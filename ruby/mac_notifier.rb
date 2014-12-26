require "amqp"
require "eventmachine"
load 'settings.rb'

class ConsoleLogger
  def initialize(name = "World")
    @name = name
  end

  def print(headers="", msg ="")
    #puts "#{msg} => routing_key:#{headers.routing_key}"
    system "terminal-notifier -title 'BioConnect' -message \"#{msg}\""
  end
end

EventMachine.run {
  settings=Settings.new
  AMQP.start("amqp://jpierre03:toto@bc.antalios.com:5672") do |connection|
    channel = AMQP::Channel.new(connection)
    exchange = channel.fanout("anta.bc", :auto_delete => false)

    logger = ConsoleLogger.new

    #:durable => true
    channel.queue("", :auto_delete => true).bind(exchange).subscribe do |headers, payload|
      logger.print(headers, payload)
    end

    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
