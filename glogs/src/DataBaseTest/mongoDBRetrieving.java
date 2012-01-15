package DataBaseTest;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class mongoDBRetrieving {
	
	//data base argument section :
	private static Mongo m;
	private static DB db ;
	private static DBCollection coll;
	
	public static void main(String[] args) throws UnknownHostException, MongoException {
		
		// TODO Auto-generated method stub
		m  = new Mongo( "localhost" , 27017 );
		db = m.getDB( "mydb" );
		coll= db.getCollection("testCollection");
		
        DBCursor cur2 = coll.find();

        while(cur2.hasNext()) {
            System.out.println(cur2.next());

	        }
	}

}
