package server;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ConfigurationFile {

	
	public String XMLpath = "";
	public File file ;
	public DocumentBuilderFactory dbf;
	public DocumentBuilder db;
	public Document doc ;
	
	
	public ConfigurationFile(String path) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated constructor stub
		XMLpath = path;
		file = new File(XMLpath);
	    dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
        doc = db.parse(file);
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	}

	public PatternConfig findMatchingPattern (String fileName )
	{
		
		NodeList FilesConfigurationsList = doc.getElementsByTagName("FileConfig");
		int FilesConfigLength = FilesConfigurationsList.getLength();
		 for(int s=0; s<FilesConfigLength ; s++){
			 
			 Node firstConfigFileNode = FilesConfigurationsList.item(s);
			 
             if(firstConfigFileNode.getNodeType() == Node.ELEMENT_NODE){
            	 
            	 Element firstConfigFileElement = (Element)firstConfigFileNode;
            	 NodeList firstfileNameList = firstConfigFileElement.getElementsByTagName("filename");
                 Element firstfileNameElement = (Element)firstfileNameList.item(0);

                 NodeList textFNList = firstfileNameElement.getChildNodes();
                 
                 System.out.println(((Node)textFNList.item(0)).getNodeValue().trim());
                 if (((Node)textFNList.item(0)).getNodeValue().trim().equals(fileName));
                 {
                	
                	 PatternConfig FilePattern= new PatternConfig();
                	 FilePattern.setFileName(fileName);
                	 NodeList firstPatternList = firstConfigFileElement.getElementsByTagName("pattern");
                	 int patternLength = firstPatternList.getLength();
                	 for ( int i=0 ; i<patternLength; i++)
                	 {
                		 Element firstPatternElement = (Element)firstPatternList.item(i);
                		 NodeList textPList = firstPatternElement.getChildNodes();
                		 FilePattern.addRegex(((Node)textFNList.item(0)).getNodeValue().trim());
                	 }
                	 return FilePattern;
                 }
             }
		 
		 }
		
		return null;
	}
	
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
    	ConfigurationFile af = new ConfigurationFile("C:\\Users\\Rschwart\\git\\glogs\\glogs\\configuration\\config.xml");
    	af.findMatchingPattern("filename1");
    	
    }
	

}