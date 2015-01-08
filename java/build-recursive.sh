#!/bin/sh

for d in `find . -type d -maxdepth 1`; do pushd $d; mvn install; popd; done

