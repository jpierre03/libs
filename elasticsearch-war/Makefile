URL:="http://elasticsearch-jpprunaret-antalios.eu2.cloudunit.io/"

all: package
	date

package:
	mvn package

status:
	curl "${URL}/_status?pretty"
test:
	curl -XPOST "${URL}/indexname/typename/optionalUniqueId" -d "{ \"field\" : \"value\"}"

