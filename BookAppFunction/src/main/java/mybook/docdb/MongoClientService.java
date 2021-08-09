package mybook.docdb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoClientService {
    private static final Logger logger = LoggerFactory.getLogger(MongoClientService.class);

    private static MongoClient mongoClient = null;

    public static MongoClient getMongoClient() throws Exception {
        if (mongoClient == null) {
            createMongoClient();
        }
        return mongoClient;
    }

    private static void createMongoClient() throws Exception {
        //docdbUri = "mongodb://dbadmin:Con#doc16@docdb-2021-08-07-17-18-03.cluster-ckddmwjkjoew.us-east-2.docdb.amazonaws.com:27017/?ssl=true&ssl_ca_certs=rds-combined-ca-bundle.pem&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false"
        //truststore = rds-ca-certs-root.jks
        try {
            String docdbUri = System.getenv("docdbUri");
            String truststore = System.getenv("truststore");
            if (truststore != null) {
                logger.info("Set SSL truststore: {}", truststore);
                System.setProperty("javax.net.ssl.trustStore", truststore);
                System.setProperty("javax.net.ssl.trustStorePassword", DocDBInfo.trustStorePassword);
            }

            logger.info("Create mongo client on: {}", docdbUri);
            mongoClient = MongoClients.create(docdbUri);
            logger.info("Created mongo client instance");
        } catch (Exception e) {
            logger.error("Failed to create mongoClient", e);
            throw e;
        }
    }

    public static MongoDatabase getBookDatabase() throws Exception {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase(DocDBInfo.booksDatabase);
        logger.info("Got the book collection");
        return database;
    }
}
