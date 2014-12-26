WEB_DIR=${HOME}/public_html
CURRENT_DIR=`pwd|sed 's_/.*/__g'`
SONAR=~/bin/sonar-runner-2.0/bin/sonar-runner

all: sonar doxygen gitstats web

.PHONY: compile sonar doxygen gitstats

sonar:
	@$(SONAR) >> /dev/null

doxygen:
	@#doxygen 2> /dev/null >> /dev/null

gitstats:
	@#gitstats . gitstats/ >> /dev/null

web:
	@#rsync -art --delete doxygen $(WEB_DIR)/$(CURRENT_DIR)/
	@#rsync -art --delete gitstats $(WEB_DIR)/$(CURRENT_DIR)/

