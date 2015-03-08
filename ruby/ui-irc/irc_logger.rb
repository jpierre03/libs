#!/usr/bin/env ruby
# encoding: utf-8

require "amqp"
require "cinch"
require "eventmachine"
load 'settings.rb'

puts "step 0"

# A simple AMQP to IRC-channel software.
#
# One way only.
# No plan to implement more functionality by now.
class IrcLogger

  def initialize(bot)
    @bot = bot
    settings = Settings.new

    EventMachine.run {
      AMQP.start(settings.amqp_url) do |connection|
        channel = AMQP::Channel.new(connection)
        exchange = channel.topic(settings.amqp_exchange_name, :auto_delete => false)

        #:durable => true
        channel.queue("", :auto_delete => true).bind(settings.amqp_exchange_name, :routing_key => "#").subscribe do |headers, payload|
          print(headers, payload)
        end

        # disconnect & exit after 1 hour
        EventMachine.add_timer(3600) do
          connection.close { EventMachine.stop }
        end
      end
    }

  end

  def print(headers="", msg="")
    puts "#{msg} => routing_key:#{headers.routing_key}"
    @bot.handlers.dispatch(:monitor_msg, nil, msg)
  end
end

# Listen AMQP channel
class RailsMonitor
  def initialize(bot)
    @bot = bot
    EventMachine.run do
      AMQP.start("amqp://localhost:5672") do |connection|
        puts "Connected to AMQP broker."
        channel = AMQP::Channel.new(connection)
        #queue    = channel.queue("amqpgem.rails.monitor", :durable => true)
        queue = channel.queue("dev.tmp", :durable => true)
        queue.subscribe do |payload|
          self.msg("#{payload}")
        end
      end
    end
  end

  def msg(msg)
    @bot.handlers.dispatch(:monitor_msg, nil, msg)
  end
end

# Implement IRC Join method
class JoinPart
  include Cinch::Plugin

  match /join (.+)/, method: :join
  match /part(?: (.+))?/, method: :part

  def initialize(*args)
    super
    @admins = ["admin_username"]
  end

  def check_user(user)
    user.refresh
    @admins.include?(user.authname)
  end

  def join(m, channel)
    return unless check_user(m.user)
    Channel(channel).join
  end

  def part(m, channel)
    return unless check_user(m.user)
    channel ||= m.channel
    Channel(channel).part if channel
  end
end

# Implements IRC gateway
class MonitorBot
  include Cinch::Plugin

  listen_to :monitor_msg

  def listen(m, msg)
    settings=Settings.new
    channel_test = settings.channel_test
    channel_warning = settings.channel_warning
    channel_info = settings.channel_info

    if msg.include? "[WARN]"
      Channel(channel_test).send Format(:red, "#{msg}")
      Channel(channel_warning).send Format(:red, "#{msg}")
    elsif msg.include? "[OK]"
      Channel(channel_test).send Format(:green, "#{msg}")
      Channel(channel_info).send Format(:green, "#{msg}")
    elsif msg.include? "[WWW]"
      Channel(channel_test).send Format(:purple, "#{msg}")
      Channel(channel_info).send Format(:purple, "#{msg}")
    elsif msg.include? "[VCS]"
      Channel(channel_test).send Format(:purple, "#{msg}")
      Channel(channel_info).send Format(:purple, "#{msg}")
    else
      Channel(channel_test).send "#{msg}"
      Channel(channel_info).send "#{msg}"
    end
  end
end

bot = Cinch::Bot.new do
  configure do |c|
    settings=Settings.new

    c.nick = settings.irc_nickname
    c.realname = settings.irc_user_comment
    c.user = settings.irc_nickname
    c.server = settings.irc_hostname
    #c.port            = 7000
    #c.ssl             = true
    c.channels = settings.irc_channels
    c.verbose = true
    c.plugins.plugins = [MonitorBot, JoinPart]
  end
end

Thread.new do
  IrcLogger.new(bot).start
end

bot.start
