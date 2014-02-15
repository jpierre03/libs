require "amqp"
require "eventmachine"
require "settings"

class ConsoleLogger
  def initialize(name = "World")
    @name = name
  end

  def print(headers="", msg ="")
    #puts "#{msg} => routing_key:#{headers.routing_key}"
    puts "#{msg}"
  end
end

EventMachine.run {
  settings=Settings.new
  AMQP.start(settings.amqp_url) do |connection|
    channel = AMQP::Channel.new(connection)
    #exchange = channel.fanout("tmpex", :auto_delete => false)
    exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

    logger = ConsoleLogger.new

    #:durable => true
    channel.queue("", :auto_delete => true).bind(exchange, :routing_key => "#").subscribe do |headers, payload|
      logger.print(headers, payload)
    end

    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
