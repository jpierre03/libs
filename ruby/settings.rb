class Settings
  attr_reader :irc_nickname, :irc_user_comment, :irc_hostname, :irc_channels,
              :channel_warning, :channel_alert, :channel_info, :channel_test,
              :amqp_exchange_name, :amqp_url,
              :mail_host, :mail_startrls, :mail_domain, :mail_port, :mail_default_recipients, :mail_default_subject, :mail_from

  def initialize()
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

    @mail_host="192.168.1.50"
    @mail_domain="example.com"
    @mail_port=25
    @mail_startrls=false
    @mail_from="sender@example.com"
    @mail_default_recipients=["test1@spam.prunetwork.fr", "test2@spam.prunetwork.fr"]
    @mail_default_subject="This is a subject line"
  end
end

