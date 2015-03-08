#!/usr/bin/env ruby
# encoding: utf-8

# usage : $0 "<SEVERITY><message>"

require "bunny"
load 'settings.rb'

settings=Settings.new

conn = Bunny.new(:automatically_recover => false,
                 :hostname => "localhost")
conn.start

ch   = conn.create_channel
x    = ch.topic(settings.amqp_exchange_name)
#ch   = conn.create_channel
#x    = ch.queue("q1", :durable => "yes")

msg  = ARGV.empty? ? "Hello World!" : ARGV.join(" ")

x.publish(msg)
for i in 1..0 do
	x.publish(msg)
end

puts " [x] Sent #{msg}"

conn.close

