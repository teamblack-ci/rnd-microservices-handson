#!/usr/bin/env bash

echo
echo "#############################################"
echo "# building 'shared'                         #"
echo "#############################################"
./shared/gradlew -p shared clean install

echo
echo "#############################################"
echo "# building 'catalog'                        #"
echo "#############################################"
./catalog/gradlew -p catalog clean check bootRepackage

echo
echo "#############################################"
echo "# building 'order'                          #"
echo "#############################################"
./order/gradlew -p order clean check bootRepackage

echo
echo "#############################################"
echo "# building 'bakery'                         #"
echo "#############################################"
./bakery/gradlew -p bakery clean check bootRepackage

echo
echo "#############################################"
echo "# building 'delivery'                       #"
echo "#############################################"
./delivery/gradlew -p delivery clean check bootRepackage
