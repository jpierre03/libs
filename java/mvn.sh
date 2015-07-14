for d in `ls`; do pushd $d; mvn package install -DskipTests |grep BUILD; popd; done
