
# Client Config
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

#
https://intellipaat.com/community/42405/is-it-possible-to-stop-nodes-in-aws-elasticache-cluster
You cannot stop an Elasticache cluster. Currently, there is a provision only to Delete it or Recreate one more. But you can do something which will give you a similar result as stopping an Elasticache cluster.

Take a Snapshot of your cluster
Delete your cluster
Use the created snapshot to create a new cluster with the same data as the deleted one
When you create with the snapshot of a cluster, It will have the cluster id, region and the aws account id. So, when you create a cluster using the snapshot, your cluster will have the same endpoint.

#
https://stackoverflow.com/questions/42024419/java-api-for-aws-elasticache
aws-java-sdk-elasticache is only for managing your Elasticache resources through the AWS API. Not for connecting and manipulating data inside the Elasticache server.

I think elasticache-java-cluster-client is only for Memcached clusters. Are you using Memcached or Redis?

If you are using Redis you should use a Java Redis client like Jedis.
