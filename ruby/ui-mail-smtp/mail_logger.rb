#!/usr/bin/env ruby
# encoding: utf-8

require "amqp"
require "eventmachine"
load 'settings.rb'


settings=Settings.new

class MailLogger
  def initialize()
  end

  # Send mail : http://stackoverflow.com/a/5994727
  def publish(headers="", msg ="", subject=Settings.new.mail_default_subject, recipients=Settings.new.mail_default_recipients)
    #puts "#{msg} => routing_key:#{headers.routing_key}"
    puts "#{msg}"

    settings=Settings.new

    email = EM::Protocols::SmtpClient.send(
        :domain => settings.mail_domain,
        :host => settings.mail_host,
        :port => settings.mail_port, # optional, defaults 25
        :starttls => settings.mail_startrls, # use ssl
        :from => settings.mail_from,
        :to => recipients,
        :header => {"Subject" => subject},
        :body => "#{msg}"

=begin
        :host => 'smtp.gmail.com',
        :port => 587,
        :starttls => true,
        :auth => {
          :type => :plain,
          :username => "xxxx@gmail.com",
          :password => "yyyyyyy"
        },
        :verbose => true
=end
    )
    email.callback {
      puts 'Email sent!'
    }
    email.errback { |e|
      puts 'Email failed!'
      puts "#{e.to_s}"
    }
  end
end

EventMachine.run {
  AMQP.start(settings.amqp_url) do |connection|
    channel = AMQP::Channel.new(connection)
    #exchange = channel.fanout("tmpex", :auto_delete => false)
    exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

    logger = MailLogger.new

    #:durable => true
    channel.queue("", :auto_delete => true).bind(exchange, :routing_key => "#").subscribe do |headers, payload|
      logger.publish(headers, payload, :subject => settings.mail_default_subject, :recipients => settings.mail_default_recipients)
    end


    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
