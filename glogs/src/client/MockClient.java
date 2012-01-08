package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MockClient {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/* BufferedReader inFromUser =
		         new BufferedReader(new InputStreamReader(System.in));*/
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		System.out.println("ip address:"+IPAddress.toString()+"---"+clientSocket.toString());
		byte[] sendData = new byte[1024];
		
		for (int i=0; i< 30; i++)
		{

		      //byte[] receiveData = new byte[1024];
		      String sentence = "File Name : foo#"+i +"-----Message-----"+"\n"; //inFromUser.readLine();
		      sendData = sentence.getBytes();
		      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8340);
		      clientSocket.send(sendPacket);
		      //DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		      //clientSocket.receive(receivePacket);
		      //String modifiedSentence = new String(receivePacket.getData());
		      // System.out.println("FROM SERVER:" + modifiedSentence);
		      //clientSocket.close();
		     
		}

	}

}
