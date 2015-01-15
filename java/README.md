libs
====

Les différents éléments de ce dépôt correspondent à des actions courantes réalisées par des applications distribuées sur plusieurs machines.

Les applications développées avec ces librairies sont contruites autour d'un broker AMQP tel que RabbitMQ ou ZeroMQ.

# Liens

* [SO: Tutoriel pour utiliser github comme dépôt maven](http://stackoverflow.com/questions/14013644/hosting-a-maven-repository-on-github)
* [la page officielle github](https://github.com/github/maven-plugins#readme)

# Bug connu sur le plugin de publication

* [thread](https://github.com/github/maven-plugins/issues/69)
* contournement : cloner le dépôt officiel du plugin, modifier le code pour mettre en dur l'adresse mail et le username, mvn install et c'est bon.
