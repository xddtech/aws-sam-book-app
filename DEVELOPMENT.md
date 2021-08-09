
#
https://aws.amazon.com/blogs/database/building-resilient-applications-with-amazon-documentdb-with-mongodb-compatibility-part-1-client-configuration/
Building resilient applications with Amazon DocumentDB (with MongoDB compatibility), Part 1: Client configuration

#
https://www.baeldung.com/java-mongodb
A Guide to MongoDB with Java

#
https://scalegrid.io/blog/how-to-use-mongodb-connection-pooling-on-aws-lambda/
How to Use MongoDB Connection Pooling on AWS Lambda

#
https://stackoverflow.com/questions/59739006/aws-documentdb-tls-connection-with-java
AWS DocumentDB TLS connection with Java

The issue seems to be related to the process of importing the certificates within the bundle, under the same alias.
So I have had to stop using the bundled option (rds-combined-ca-bundle.pem) and start using this one rds-ca-2019-root.pem
After importing the keystore using the following command:

keytool -importcert -trustcacerts -file rds-ca-2019-root.pem -alias rds19 -keystore rds-ca-certs -storepass keyStorePassword

Connectivity with the database under TLS was achieved.
