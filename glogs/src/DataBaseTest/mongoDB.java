package DataBaseTest;



import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;


public class mongoDB {

	
	
	/**
	 * @param args
	 */
	
	private static Mongo m;
	private static DB db ;
	private static DBCollection coll;
	
	public static void main(String[] args) throws UnknownHostException, MongoException {
		// TODO Auto-generated method stub
		m  = new Mongo( "localhost" , 27017 );
		db = m.getDB( "mydb" );
		coll= db.getCollection("testCollection");
		
		
	    BasicDBObject doc = new BasicDBObject();
		/*doc.put("date", "11/04/1990");
		doc.put("time", "09:07:09.385");
		doc.put("function", "CCPServlet");*/
		doc.put("unknownAttribute", "qtp9521674-29");
        doc.put("type", "INFO");
        doc.put("Message", "Transaction get configuration");
        
        if ( coll!= null)
        {	
        	coll.insert(doc);
        }
        
        /*DBCursor cur = coll.find();

        while(cur.hasNext()) {
            System.out.println(cur.next());
        }*/
        db.dropDatabase();
        DBCursor cur2 = coll.find();

        while(cur2.hasNext()) {
            System.out.println(cur2.next());
        
            
            
        }
        
        
	    
	}

}
