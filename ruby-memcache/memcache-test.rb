require 'dalli'
require 'securerandom'

# https://github.com/mperham/dalli

options = { :namespace => "app_v1", :compress => true }
#dc = Dalli::Client.new('raspberrypi.local:11211', options)
dc = Dalli::Client.new('172.16.201.63:11211', options)


loop_counter=1000
contentSize=16*1000
key_prefix='k'

loop_counter.times do |n|
  key = key_prefix + n.to_s
  
  #dc.set(key, SecureRandom.hex(contentSize))
  
  value = dc.get(key)
  puts "#{key}: #{value}"
  
  dc.set(key, SecureRandom.hex(contentSize))
end

