import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBJDBC {
	public static void main(String args[]) {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("localDB");
			System.out.println("Connect to database successfully");
			//连接到集合
			MongoCollection<Document> collection = mongoDatabase.getCollection("localDB");
			System.out.println("集合 localDB 选择成功!");
			
			// insert(collection);
			find(collection);
//			update(collection);
			search(collection);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 插入数据
	 */
	public static void insert(MongoCollection<Document> collection) {
		Document document = new Document("name", "ML");
		document.append("age", 30);
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
		System.out.println("文档插入成功");
	}

	/*
	 * 查找所有数据
	 */
	public static void find(MongoCollection<Document> collection) {
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

	/*
	 * 修改数据
	 */
	public static void update(MongoCollection<Document> collection) {
		collection.updateOne(Filters.eq("name", "ML"), new Document("$set", new Document("age", 25)));
	}
	
	/*
	 * 删除数据
	 */
	public static void delete(MongoCollection<Document> collection){
		collection.deleteMany(Filters.eq("name", "ML"));
	}
	
	/*
	 * 按条件搜索
	 */
	public static void search(MongoCollection<Document> collection){
//		FindIterable<Document> findIterable = collection.find(Filters.eq("name", "ML")).limit(1).skip(1);
		Document doc = new Document("name","ML").append("age", new Document("$gte",24));
		FindIterable<Document> findIterable = collection.find(doc);
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
		System.out.println(collection.count(Filters.eq("name", "ML")));
	}
}