package net.net16.jeremiahlowe.updater;

import java.io.File;
import java.util.List;

public class Updater {
	public static File getUpdated(String versionFileURL, String newName, String currentVersion) throws Exception{
		File versionFile = Utility.downloadFile(versionFileURL, "recent");
		if(needsUpdate(versionFile, currentVersion)) return update(versionFile, newName);
		else return null;
	}
	public static boolean needsUpdate(File versionFile, String currentVersion) throws Exception{
		return needsUpdate(Utility.getVersionFileLines(versionFile), currentVersion);
	}
	public static boolean needsUpdate(List<String> lines, String currentVersion) throws Exception{
		boolean disabled = false, needed = false;
		for(String line : lines){
			line = Utility.removeCharacters(line, Utility.WHITESPACE);
			int ind = getIndex(line, '=');
			if(ind == -1) continue;
			String front = line.substring(0, ind);
			String back = line.substring(ind + 1);
			if(front == null || back == null) continue;
			if(front.matches("currentVersion")) needed = !back.matches(currentVersion);
			if(front.matches("disableUpdates")) disabled = Boolean.parseBoolean(back);
			System.out.println(front + " : " + back);
		}
		return needed && !disabled;
	}
	public static String getVersion(List<String> lines){
		for(String line : lines){
			line = Utility.removeCharacters(line, Utility.WHITESPACE);
			int ind = getIndex(line, '=');
			if(ind == -1) continue;
			String front = line.substring(0, ind);
			String back = line.substring(ind + 1);
			if(front.matches("currentVersion")) return back;
			System.out.println(front + " : " + back);
		}
		return null;
	}
	public static File update(File versionFile) throws Exception{return update(versionFile, null);}
	public static File update(File versionFile, String fileName) throws Exception{
		List<String> lines = Utility.getVersionFileLines(versionFile);
		String downloadURL = null;
		for(String line : lines){
			line = Utility.removeCharacters(line, Utility.WHITESPACE);
			int ind = getIndex(line, '=');
			if(ind == -1) continue;
			String front = line.substring(0, ind);
			String back = line.substring(ind + 1);
			if(front.matches("downloadLocation")) downloadURL = back;
			if(front.matches("fileName") && fileName == null) fileName = back;
			System.out.println(front + " : " + back);
		}
		if(fileName == null) fileName = "unnamed.txt";
		return Utility.downloadFile(downloadURL, fileName);
	}
	public static String getParameter(String in, char seperator){
		in = Utility.removeCharacters(in, Utility.WHITESPACE);
		return in.substring(getIndex(in, seperator), in.length());
	}
	public static int getIndex(String in, char seperator){
		for(int i = 0; i < in.length(); i++) if(in.charAt(i) == seperator) return i;
		return -1;
	}
}
