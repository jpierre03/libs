class Settings
  attr_reader :irc_nickname, :irc_user_comment, :irc_hostname, :irc_channels,
              :channel_warning, :channel_alert, :channel_info, :channel_test,
              :amqp_exchange_name, :amqp_url

  def initialize
    #@amqp_url="amqp://jpierre03:toto@bc.antalios.com:5672"
    @amqp_url="amqp://localhost:5672"
    @amqp_exchange_name= "dev.tmp"

    @channel_info= "#info"
    @channel_warning= "#warning"
    @channel_alert= "#alert"
    @channel_test= "#test"

    @irc_hostname= "irc.teleragno.fr"
    @irc_channels= [@channel_alert, @channel_info, @channel_test, @channel_warning]
    @irc_nickname="[jpierre03_bot2]"
    @irc_user_comment="Je suis un bot AMQP -> IRC. Je n'aime pas répondre aux gens que je ne connais pas. Je ne connais personne."
  end
end
