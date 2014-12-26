require "amqp"
require "piface"
require "bunny"

load 'settings.rb'

class RaspberryPiInputDevice
  def initialize()
    conn = Bunny.new(:automatically_recover => false, :hostname => "bc.antalios.com", :user => "jpierre03", :password => "toto")
    conn.start

    ch = conn.create_channel
    x = ch.fanout("anta")

    # Listen for button press
    loop do
      for i in 0..4 do
        # Check if the button has been pressed
        if (Piface.read(i) == Piface::HIGH)
          msg = "Button " + i.to_s + " pressed"
          puts msg
          x.publish(msg)
          puts " [x] Sent #{msg}"
        end
      end
      sleep 0.05 # sleep to be kind to the CPU
    end

  end
end

class Relay
  def initialize(relay_number)
    @relay_number = relay_number
    @state = 0
  end

  def turn_on
    Piface.write @relay_number, 1
    @state = 1
  end

  def turn_off
    Piface.write @relay_number, 0
    @state = 0
  end

  def toggle
    @state == 1 ? turn_off : turn_on
  end

  def blink(delay=1)
    turn_on
    sleep delay
    turn_off
  end
end

EventMachine.run {
  settings=Settings.new
  AMQP.start(settings.amqp_url) do |connection|
    channel = AMQP::Channel.new(connection)
    exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

    Thread.new { device = RaspberryPiInputDevice.new() }

    #out0=Relay.new(0)
    #out1=Relay.new(1)
    out2=Relay.new(2)
    out3=Relay.new(3)
    out4=Relay.new(4)
    out5=Relay.new(5)
    out6=Relay.new(6)
    out7=Relay.new(7)
    out8=Relay.new(8)

    channel.queue("", :auto_delete => true).bind(exchange, :routing_key => "#").subscribe do |headers, payload|
      #out0.turn_on()
      #out1.turn_on()
      out2.turn_on()
      out3.turn_on()
      out4.turn_on()
      out5.turn_on()
      out6.turn_on()
      out7.turn_on()
      out8.turn_on()
      sleep 1
      #out0.turn_off()
      #out1.turn_off()
      out2.turn_off()
      out3.turn_off()
      out4.turn_off()
      out5.turn_off()
      out6.turn_off()
      out7.turn_off()
      out8.turn_off()
      sleep 1
    end

    # disconnect & exit after 1 hour
    #EventMachine.add_timer(3600) do
    #  connection.close { EventMachine.stop }
    #end
  end
}
