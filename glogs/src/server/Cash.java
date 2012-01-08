package server;

import java.awt.List;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;


import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class Cash extends Thread {
	
	Mongo m = new Mongo( "localhost" , 27017 );
	DB db = m.getDB( "mydb" );
	
	//the reason that we are using a list of buffers is because of the cost of the append to old buffer
	static Map<SocketAddress,ArrayList<ByteBuffer>> logMessagesStorage ;
	ConfigurationFile configFile ;
	
	
	/*------------------------------------------------------------------------------------
	 *  function name            : constructor
	 *  Input                   : -- 
	 * Output (return Type)     : --
	 * Operation				: initialize the hush map  with tha hush we got from the server			
	 ---------------------------------------------------------------------------------------*/
	public Cash (Map<SocketAddress,ArrayList<ByteBuffer>> LogMessagesStorage ) throws ParserConfigurationException, SAXException, IOException
	{
		this.logMessagesStorage = LogMessagesStorage ;
		//this.configFile = new ConfigurationFile("/glogs/configuration/config.xml");
	}
	
	
	/*------------------------------------------------------------------------------------
	 *  function name            : run
	 *  Input                   : -- 
	 * Output (return Type)     : --
	 * Operation				: pass over the HashMap and sending the string to find a match pattern 			
	 ------------------------------------------------------------------------------------*/
	public void run ()
	{
		while ( true )
		{
			if ( logMessagesStorage.isEmpty() == false)
			{	
				
				Iterator<SocketAddress> iterator = logMessagesStorage.keySet().iterator();
				
				while (iterator.hasNext())
				{
					//try to mutch a messege to a given pattern :
					
					SocketAddress SA = iterator.next();
					ArrayList<ByteBuffer> list = logMessagesStorage.get(SA);
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
						String strMessage = message.toString();
						if ( strMessage.matches("file name : [a-zA-Z0-9]*)"))
						{
							String temp = strMessage.split("file name : [a-zA-Z0-9]*)").toString();
							
							
							
							
						}
						
					}

				}
				// Check the configuration and send the line to the approprate function 
				
				
			}
		}
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
		// BasicDBObject doc = new BasicDBObject();
		// doc.put("date", date);
		// doc.put("Otherpropertie",
		// coll.insert(doc); //storing data
		
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
