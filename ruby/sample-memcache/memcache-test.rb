require 'dalli'
require 'securerandom'

# https://github.com/mperham/dalli

memcache_hostname = "raspberrypi.local:11211"
memcache_hostname = "172.16.201.63:11211"

options = {:namespace => "app_v1", :compress => true}
mc = Dalli::Client.new(memcache_hostname, options)

loop_counter = 1000
contentSize = 16*1000
key_prefix = 'k'

loop_counter.times do |n|
  key = key_prefix + n.to_s

  value = mc.get(key)
  puts "#{key}: #{value}"

  mc.set(key, SecureRandom.hex(contentSize))
end

