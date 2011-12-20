package server;
import java.io.*;
import java.net.*;



import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;


public class Server {
	
	private static HashMap logMessegHashMap ;

	/**
	 * @param args
	 * @throws MongoException 
	 * @throws IOException 
	 */
	
	public Server ()
	{
		logMessegHashMap = new HashMap/*<String,String>*/();
		HushMapMannager hushMap = new HushMapMannager(logMessegHashMap);
		
		
	}
	
	
	
	public static void main(String[] args) throws MongoException, IOException {
		// TODO Auto-generated method stub
		
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        
        while(true)
           {
                      	
        	  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
              serverSocket.receive(receivePacket);
              
              // get file name = the key 
              String key = "fileName";
              
              //retrive the old value to append to the new string 
              String existing = logMessegHashMap.get(key).toString();
              
              String extraContent = receivePacket.getData().toString();
              
              //add the message to the appropriate cell by file name ***attention this is whith payload
              logMessegHashMap.put("key", existing == null ? extraContent : existing + extraContent);
              
              
              //String sentence = new String( receivePacket.getData());
              //System.out.println("RECEIVED: " + sentence);
              
           }
        
        
        
		
		
		
		

	}

}
