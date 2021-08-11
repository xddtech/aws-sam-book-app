package mybook.service;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mybook.cache.MemcachedService;
import mybook.docdb.DocDBInfo;
import mybook.docdb.MongoClientService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class GetBookService implements RequestEventService {
    private static final Logger logger = LoggerFactory.getLogger(GetBookService.class);

    public GetBookService() {
    }

    private boolean isCheckCache() {
        boolean checkCache = false;
        String useCache = System.getenv("useCache");
        if (useCache != null && useCache.equalsIgnoreCase("true")) {
            checkCache = true;
        }
        logger.info("Check books in memcached: " + checkCache);
        return checkCache;
    }

    public String processRequest(String requestBody) throws Exception {
        boolean checkCache = isCheckCache();
        if (checkCache) {
            String books = MemcachedService.getInstance().get("books");
            if (books != null) {
                logger.info("Found books in memcached");
                return books;
            }
        }

        MongoDatabase bookDB = MongoClientService.getBookDatabase();
        MongoCollection<Document> bookCollection = bookDB.getCollection(DocDBInfo.booksCollection);
        logger.info("Got book collection size: {}", bookCollection.countDocuments());

        ArrayList<Document> array = new ArrayList<>();
        FindIterable<Document> iter = bookCollection.find();
        for (Document doc : iter) {
            array.add(doc);
        }
        String books = new Gson().toJson(array);
        if (checkCache) {
            MemcachedService.getInstance().set("books", books);
            logger.info("Saved books in memcahed");
        }
        return books;
    }
}
