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

public class GetBookService implements RequestEventService {
    private static final Logger logger = LoggerFactory.getLogger(GetBookService.class);

    public String processRequest(String requestBody) throws Exception {
        MongoDatabase bookDB = MongoClientService.getBookDatabase();
        MongoCollection<Document> bookCollection = bookDB.getCollection(DocDBInfo.booksCollection);
        logger.info("Got book collection size: {}", bookCollection.countDocuments());

        ArrayList<Document> array = new ArrayList<>();
        FindIterable<Document> iter = bookCollection.find();
        for (Document doc : iter) {
            array.add(doc);
        }
        return new Gson().toJson(array);
    }
}
