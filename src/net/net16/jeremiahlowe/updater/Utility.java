package net.net16.jeremiahlowe.updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	public static final char[] WHITESPACE = {'\n', '\r', '\t', ' '};
	public static File downloadFile(String url, String fileName) throws Exception{
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		return new File(fileName);
	}
	public static String removeCharacters(String s, char... toRemove){
		String out = "";
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			boolean ok = true;
			for(char e : toRemove) if(c == e){
				ok = false; break;
			}
			if(ok) out += c;
		}
		return out;
	}
	public static List<String> getVersionFileLines(File versionFile) throws Exception{
		FileInputStream fis = new FileInputStream(versionFile);
		List<String> lines = new ArrayList<String>();
		String tmp = ""; int rr = 0; 
		boolean commented = false;
		while((rr = fis.read()) != -1){
			char c = (char) rr;
			if(c == '#') commented = true;
			if(c == '\n' || c == '\r' || c == ';'){
				commented = false;
				lines.add(tmp);
				tmp = "";
			}
			else if(!commented) tmp += c;
		}
		fis.close();
		return lines;
	}
	public static String removeNonAlphanumeric(String in){
		String out = "";
		for(int i = 0; i < in.length(); i++){
			char c = in.charAt(i);
			if(c >= 'A' && c <= 'Z') out += c;
			else if(c >= 'a' && c <= 'z') out += c;
			else if(c >= '0' && c <= '9') out += c;
		}
		return out;
	}
	public static String removeNonAlphanumericButInclude(String in, char... include){
		String out = "";
		for(int i = 0; i < in.length(); i++){
			char c = in.charAt(i);
			if(c >= 'A' && c <= 'Z') out += c;
			else if(c >= 'a' && c <= 'z') out += c;
			else if(c >= '0' && c <= '9') out += c;
			boolean ok = true;
			for(char e : include) if(e == c){
				ok = false; break;
			}
			if(!ok) out += c;
		}
		return out;
	}
	public static String shrinken7Bit(String in){return shrinkenToBits(in, 7);}
	public static String shrinken8Bit(String in){return shrinkenToBits(in, 8);}
	public static String shrinkenToBits(String in, int bits){
		int max = (int) Math.pow(2, bits);
		String out = "";
		for(int i = 0; i < in.length(); i++){
			char c = in.charAt(i);
			if(c < max) out += c;
		}
		return out;
	}
}
