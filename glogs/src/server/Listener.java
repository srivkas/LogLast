package server;


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
//import java.nio.channels.SocketChannel;
//import java.nio.channels.spi.SelectorProvider;
//import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class Listener {
	
    static int BUF_SZ = 1024;
    ArrayList<ByteBuffer> BuffersList = new ArrayList<ByteBuffer>();
    static Map<SocketAddress,ArrayList<ByteBuffer>> logMessagesStorage ;
    static int port = 8340;
    
    class Con {
        ByteBuffer req;
        SocketAddress sa;

        public Con() {
            req = ByteBuffer.allocate(BUF_SZ);
        }
    }

   
    
    
    public Listener (Map<SocketAddress, ArrayList<ByteBuffer>> cashStorage)
    {
    	logMessagesStorage = cashStorage;
    }
    
    
    private void process() {
        try {
            Selector selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress isa = new InetSocketAddress(port);
            channel.socket().bind(isa);
            channel.configureBlocking(false);
            SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
            clientKey.attach(new Con());
            System.out.print("Server up!");
            while (true) {
                try {
                    selector.select();
                    java.util.Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext()) {
                        try {
                            SelectionKey key = (SelectionKey) selectedKeys.next();
                           // selectedKeys.remove();

                            if (!key.isValid()) {
                              continue;
                            }

                            if (key.isReadable()) {
                                read(key);
                                //key.interestOps(SelectionKey.OP_READ);
                            } 
                        } catch (IOException e) {
                            System.err.println("glitch, continuing... " +(e.getMessage()!=null?e.getMessage():""));
                        }
                    }
                } catch (IOException e) {
                    System.err.println("glitch, continuing... " +(e.getMessage()!=null?e.getMessage():""));
                }
            }
        } catch (IOException e) {
            System.err.println("network error: " + (e.getMessage()!=null?e.getMessage():""));
        }
    }

    
    private void read(SelectionKey key) throws IOException {
        DatagramChannel chan = (DatagramChannel)key.channel();
        Con con = (Con)key.attachment();
        
        DatagramPacket dp = new DatagramPacket(con.req.array(),BUF_SZ);
        con.sa = chan.receive(con.req);
        ArrayList<ByteBuffer> Buffers =logMessagesStorage.get(con.sa);
        
        if (Buffers ==null)
        {
        	 Buffers = new  ArrayList<ByteBuffer>();
        	 Buffers.add(con.req);
        	 logMessagesStorage.put(con.sa,Buffers);
        }
        else
        {
        	logMessagesStorage.get(con.sa).add(con.req);
        }
       
        System.out.println("IP and socket :"+con.sa+"\n"+new String(con.req.array(), "UTF-8"));
        con.req.clear();
    }

   

   static public void main(String[] args) {
    	 
	     Map<SocketAddress,ArrayList<ByteBuffer>> CashStorage = new HashMap<SocketAddress,ArrayList<ByteBuffer>>();
         Listener svr = new Listener(CashStorage);
         
         svr.process();
    }
}
