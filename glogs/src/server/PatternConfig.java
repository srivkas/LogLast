package server;

import java.awt.List;
import java.util.ArrayList;

public class PatternConfig {
	public String fileName ="";
	public ArrayList<String> regexList ;
	
	public PatternConfig() {
		// TODO Auto-generated constructor stub
		regexList = new ArrayList<String>();
	}
	
	public void addRegex(String regex)
	{
		regexList.add(regex);
	}
	
	public void removeRegex(String regex)
	{
		regexList.remove(regex);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getRegexList() {
		return regexList;
	}

	public void setRegexList(ArrayList<String> regexList) {
		this.regexList = regexList;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String regexString = null ;
		for ( int i=0 ; i<regexList.size(); i++)
		{
			regexString+="Regex number :"+i+regexList.get(i)+"\n";
		}
		return fileName+regexString;
	}
	
	

}
