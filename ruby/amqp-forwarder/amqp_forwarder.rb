#!/usr/bin/env ruby
# encoding: utf-8

require "amqp"
require "bunny"
require "eventmachine"
load 'settings.rb'

settings = Settings.new

# Create amqp link.
class AmqpForwarder
  def initialize(secondary_amqp_hostname = "bc.antalios.com",
                 secondary_amqp_topic = "tmp",
                 secondary_amqp_username = "jpierre03",
                 secondary_amqp_password = "toto")
    conn = Bunny.new(:automatically_recover => false,
                     :hostname => secondary_amqp_hostname,
                     :username => secondary_amqp_username,
                     :password => secondary_amqp_password)
    conn.start

    ch = conn.create_channel
    x = ch.topic(secondary_amqp_topic)
  end

  def publish(headers="",
              msg ="")
    puts "#{msg} => routing_key:#{headers.routing_key}"
    #puts "#{msg}"

    settings = Settings.new

    x.publish(msg)
  end
end

EventMachine.run {
  primary_amqp_hostname = "172.16.201.201"
  primary_amqp_username = "jpierre03"
  primary_amqp_password = "toto"
  primary_amqp_topic = "ae.ela.v1"

  AMQP.start(:host => primary_amqp_hostname,
             :username => primary_amqp_username,
             :password => primary_amqp_password) do |connection|
    channel = AMQP::Channel.new(connection)
    #exchange = channel.fanout("tmpex", :auto_delete => false)
    #exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

    exchange = channel.topic(primary_amqp_topic,
                             :auto_delete => false)

    forwarder = AmqpForwarder.new

    #:durable => true
    channel.queue("", :auto_delete => true).bind(exchange, :routing_key => "#").subscribe do |headers, payload|
      forwarder.publish(headers, payload)
    end


    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
