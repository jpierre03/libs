require "eventmachine"
require "amqp"

#@amqp_url="amqp://jpierre03:toto@bc.antalios.com:5672"
@amqp_string="amqp://localhost:5672"
@amqp_exchange_name="dev.tmp"

class InputDeviceSimulator
  @value=Random.new.rand(1..9999)
  @amqp_exchange
  @id

  def initialize(id=Random.new.rand(10000..99999999))
    @id=id
    EventMachine.run {
      EventMachine.add_periodic_timer(2) {
        self.updateValue()
      }
    }
  end

  def updateValue()
    @value=Random.new.rand(1..99999999)
  end

  def id
    @id
  end

  def value
    @value
  end

end

EventMachine.run {
  AMQP.start(@amqp_url) do |connection|
    channel = AMQP::Channel.new(connection)
    exchange = channel.topic(@amqp_exchange_name, :auto_delete => false)

    device=InputDeviceSimulator.new

    EventMachine.add_periodic_timer(1) do
      exchange.publish("id=#{device.id}, value=#{device.value}")
    end

    # disconnect & exit after 2 seconds
    EventMachine.add_timer(3600) do
      exchange.delete

      connection.close { EventMachine.stop }
    end
  end
}
