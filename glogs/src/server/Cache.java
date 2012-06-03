package server;

import java.awt.List;
import java.nio.channels.SocketChannel;

import java.io.Console;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.util.Hash;


import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class Cache implements Runnable {

	//MongoDB attributes ----------------------------------------
	//private Mongo m;
	//private DB db ;
	//private DBCollection coll;

	//HusHmap attributes ----------------------------------------
	//the reason we are using a list of buffers is because of the cost of the append to old buffer
	static Map<SocketChannel,ArrayList<ByteBuffer>> logMessagesStorage ;

	//XML attributes --------------------------------------------
	ConfigurationFile configFile ;


	//private List queue = new LinkedList();

	public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		synchronized(this.logMessagesStorage) {

			ArrayList<ByteBuffer> queue = this.logMessagesStorage.get(socket);
			if (queue == null) {
				queue = new ArrayList();
				this.logMessagesStorage.put(socket, queue);
			}
			queue.add(ByteBuffer.wrap(data));
			this.logMessagesStorage.notify();	
		}



	}

	/*------------------------------------------------------------------------------------
	 *  function name            : run
	 *  Input                   : -- 
	 * Output (return Type)     : --
	 * Operation				: pass over the HashMap and sending the string to find a match pattern 			
	 ------------------------------------------------------------------------------------*/
	
	public void run() {
		
		 ServerDataEvent dataEvent;

		while(true) 
		{
			// Wait for data to become available
			synchronized(this.logMessagesStorage) 
			{
				while(this.logMessagesStorage.isEmpty()) 
				{
					try 
					{
						this.logMessagesStorage.wait();
					} catch (InterruptedException e) 
					{
					}
				}
				
					Iterator<SocketChannel> iterator = logMessagesStorage.keySet().iterator();

					if(iterator.hasNext())
					{

						SocketChannel SC = iterator.next();
						ArrayList<ByteBuffer> list = logMessagesStorage.get(SC);
						

						if ( list != null)
						{
							int bytesLength =0;

							for ( int i=0 ; i< list.size(); i++)
							{
								bytesLength +=list.get(i).capacity();
							}

							ByteBuffer BF;
							byte[] message= new byte[bytesLength];  
							int currentLength = 0;

							for ( int i=0 ; i <list.size(); i++)
							{
								BF = list.get(i);
								System.arraycopy(BF.array(),0 , message, currentLength,BF.capacity());
								currentLength+=BF.capacity();
							}

							//now we have the whole message , - We can check if there is a match between pattern and the message 

							// get the file name of the message :
						
							String strMessage = new String(message);
							System.out.println(strMessage);

							if ( strMessage.matches("file name : [a-zA-Z0-9]*"))
							{
								//String temp = strMessage.split("file name : [a-zA-Z0-9]*)").toString();

								//BasicDBObject doc = new BasicDBObject();
								//doc.put("date", "11/04/1990");
								//doc.put("time", "09:07:09.385");
								//doc.put("function", "CCPServlet");
								//doc.put("unknownAttribute", "qtp9521674-29");
								//doc.put("type", "INFO");
								//doc.put("Message", "Transaction get configuration");
								//coll.insert(doc);
								
							}
							this.logMessagesStorage.remove(SC);

						}
					}
			}
		}
}

						



	/*------------------------------------------------------------------------------------
	 *  function name            : constructor
	 *  Input                   : -- 
	 * Output (return Type)     : --
	 * Operation				: initialize the hush map  with tha hush we got from the server			
	 ---------------------------------------------------------------------------------------*/
	public Cache () throws ParserConfigurationException, SAXException, IOException
	{
		this.logMessagesStorage = new HashMap<SocketChannel, ArrayList<ByteBuffer>>();
		//this.m  = new Mongo( "localhost" , 27017 );
		//this.db = m.getDB( "mydb" );
		//this.coll= db.getCollection("testCollection");
	}


	

	/*------------------------------------------------------------------------------------
	 *  function name            : patternMatching 
	 * Input                    : filename that has a configure patterns , and the log message to be parsed 
	 * Output (return Type)     : If the function matched the message successfully - save the object in the DB and return true - else- false.
	 * Operation				: Try to find a match to the string message 			
	 ------------------------------------------------------------------------------------*/

	public boolean patternMatching (String fileName , String logMessege)
	{
		int i=0 ;
		boolean patternNotmatching = true;
		while ( patternNotmatching )
		{
			/*String pattern = configFile.findMatchingPattern(fileName) ;
			if ( pattern == null)
				break;
			else
			{
				//try to match message to pattern 
			}*/




			i++;
		}
		return true;
	}

	/*------------------------------------------------------------------------------------
	 *  Input                    : string message that may be the hole message or part of the hole message 
	 * Output (return Type)     : If the function matched the message successfully - save the object in the DB and return true - else- false.
	 * Operation				: Try to find a match to the string message 			
	 ------------------------------------------------------------------------------------*/
	public Boolean parsingPattern (String Pattern , String message)
	{
		 //BasicDBObject doc = new BasicDBObject();
		// doc.put("date", date);
		 //doc.put("Otherpropertie", coll.insert(doc)); //storing data

		/* do :
		 * 1. parsing data  
		 * 2. while create a MongoDB object 
		 * 3. store  the mongoDB object  
		 */


		return true ; //false

	}

	//public storeObject()
	{

	}
	
	
	 







}
