package mybook.service;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mybook.docdb.DocDBInfo;
import mybook.docdb.MongoClientService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PostBookService implements RequestEventService {

    private static final Logger logger = LoggerFactory.getLogger(PostBookService.class);

    public String processRequest(String requestBody) throws Exception {
        logger.info("Request body: {}", requestBody);
        MongoDatabase bookDB = MongoClientService.getBookDatabase();
        MongoCollection<Document> bookCollection = bookDB.getCollection(DocDBInfo.booksCollection);

        Document doc = Document.parse(requestBody);
        bookCollection.insertOne(doc);

        return new Gson().toJson(doc);
    }
}
