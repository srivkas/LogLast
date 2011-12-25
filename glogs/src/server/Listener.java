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
    static Map<SelectionKey,ArrayList<ByteBuffer>> logMessagesStorage = new HashMap<SelectionKey,ArrayList<ByteBuffer>>();
    static int port = 8340;
    
    class Con {
        ByteBuffer req;
        SocketAddress sa;

        public Con() {
            req = ByteBuffer.allocate(BUF_SZ);
        }
    }

   
    
    
    public Listener (Map<SelectionKey,ArrayList<ByteBuffer>> logMessagesStorage)
    {
    	this.logMessagesStorage = logMessagesStorage;
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
        
        
        System.out.println(new String(con.req.array(), "UTF-8"));
        //con.resp = Charset.forName( "UTF-8" ).newEncoder().encode(CharBuffer.wrap("send the same string"));
        con.req.clear();
    }

   

   static public void main(String[] args) {
    	 
         Listener svr = new Listener(logMessagesStorage);
         svr.process();
    }
}
