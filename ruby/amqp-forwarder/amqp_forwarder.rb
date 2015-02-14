#!/usr/bin/env ruby
# encoding: utf-8

require "amqp"
require "bunny"
require "eventmachine"
load 'settings.rb'


settings=Settings.new

class AmqpForwarder
  def initialize(remote_amqp_hostname="bc.antalios.com", remote_amqp_topic="tmp")
      conn = Bunny.new(:automatically_recover => false,
                       :hostname => remote_amqp_hostname, :username => "jpierre03", :password => "toto")
      conn.start
      
      ch = conn.create_channel
      x = ch.topic(remote_amqp_topic)
  end

  # Send mail : http://stackoverflow.com/a/5994727
  def publish(headers="", msg ="")
    #puts "#{msg} => routing_key:#{headers.routing_key}"
    puts "#{msg}"

    settings=Settings.new

    x.publish(msg)
  end
end

EventMachine.run {
    #AMQP.start(settings.amqp_url) do |connection|
    AMQP.start(:host => "172.16.201.201", :username => "jpierre03", :password => "toto") do |connection|
    channel = AMQP::Channel.new(connection)
    #exchange = channel.fanout("tmpex", :auto_delete => false)
    #exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)
    
    exchange = channel.topic("ae.ela.v1", :auto_delete => false)

    logger = AmqpForwarder.new

    #:durable => true
    channel.queue("", :auto_delete => true).bind(exchange, :routing_key => "#").subscribe do |headers, payload|
      logger.publish(headers, payload)
    end


    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
