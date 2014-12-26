require "eventmachine"
require "amqp"
load 'settings.rb'

class InputDeviceSimulator

  def initialize(id=Random.new.rand(10000..99999999))
    @id=id
    @value=Random.new.rand(1..9999)
    @key=Random.new.rand(1..9)

    EventMachine.run {
      EventMachine.add_periodic_timer(2) {
        self.updateValue()
      }
    }
  end

  def updateValue()
    @value=Random.new.rand(1..99999999)
    @key=Random.new.rand(1..9)
  end

  def id
    @id
  end

  def value
    @value
  end

  def key
    @key
  end

end

EventMachine.run {
  settings=Settings.new
  AMQP.start(settings.amqp_url) do |connection|
    channel = AMQP::Channel.new(connection)
    exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

    device=InputDeviceSimulator.new

    EventMachine.add_periodic_timer(1) do
      exchange.publish("id=#{device.id}, value=#{device.value}", :routing_key => "marseille.#{device.id}.#{device.key}")
    end

    # disconnect & exit after 1 hour
    EventMachine.add_timer(3600) do
      connection.close { EventMachine.stop }
    end
  end
}
