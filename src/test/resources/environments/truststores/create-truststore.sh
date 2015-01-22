#!/bin/sh

if [ $# -ne 2 ]; then
  echo "Usage example: $0 myenvironment.dave.net-a-porter.com mypassword"
  exit 1
fi

HOST=$1
KEYSTORE=$1-truststore.jks
PASSWORD=$2

echo -n | openssl s_client -connect $HOST:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > tmp.cert
keytool -import -alias $HOST -keystore $KEYSTORE -file tmp.cert -storepass $2
rm tmp.cert

for i in 1 2 3
  do      
    NEXT_HOST=`echo $HOST | sed "s/\./$i\./"`
    echo $NEXT_HOST
    echo -n | openssl s_client -connect $NEXT_HOST:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > tmp.cert
    keytool -import -alias $NEXT_HOST -keystore $KEYSTORE -file tmp.cert -storepass $2
    rm tmp.cert
  done

