package net.net16.jeremiahlowe.mm2;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utility {
	public static GUI gui;
	public static boolean showDebugMessages = false;
	public static boolean showDebugMessageOverride = false;
	public static boolean ansiEnabled = true;
	public static String dateFormat = "mm/dd/yy hour:min:second am_pm";
	public static final String LS = System.lineSeparator();
	public static boolean logToFile = false;
	
	private static File logFile;
	private static boolean fileStreamOpen = false; 
	private static FileOutputStream logStream;
	
	public static String getDatePrefix(){return getDatePrefix(System.currentTimeMillis());}
	public static String getDatePrefix(long epoch){return getDatePrefix(epoch, dateFormat);}
	public static String getDatePrefix(long epoch, String dpf){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(epoch);
		String msg = dpf;
		msg = msg.replaceAll("mm", c.get(Calendar.MONTH) + "");
		msg = msg.replaceAll("dd", c.get(Calendar.DAY_OF_MONTH) + "");
		String year = c.get(Calendar.YEAR) + "";
		msg = msg.replaceAll("yyyy", year);
		msg = msg.replaceAll("yy", year.substring(year.length() - 2, year.length()));
		msg = msg.replaceAll("hour", c.get(Calendar.HOUR) + "");
		msg = msg.replaceAll("min", c.get(Calendar.MINUTE) + "");
		msg = msg.replaceAll("second", c.get(Calendar.SECOND) + "");
		msg = msg.replaceAll("ms", c.get(Calendar.MILLISECOND) + "");
		int ap = c.get(Calendar.AM_PM);
		msg = msg.replaceAll("am_pm", ap == Calendar.AM ? "AM" : "PM");
		return msg;
	}
	public static void initLogFile(String name){
		try{
			logFile = new File(name);
			logFile.createNewFile();
			logStream = new FileOutputStream(logFile);
			fileStreamOpen = true;
			logToFile = true;
		}catch(Exception e){Utility.logSevere("Couldn't initialize log file", e);}
	}
	public static void closeLogFile() throws IOException{
		try{if(fileStreamOpen) logStream.close();}
		catch(Exception e){Utility.logSevere("Couldn't close log file", e);}
	}
	public static void logToFile(String msg){
		if(logToFile && fileStreamOpen){
			try {logStream.write(msg.getBytes());} 
			catch (IOException e) {logError("Couldn't write to log file", e);}
		}
	}
	public static void logDebug(String msg){
		if(showDebugMessages || showDebugMessageOverride){
			logToGUI(gui, "[Debug]", msg, null, new Color(255, 0, 255), false, true);
			logAnything(System.out, "[Debug]", msg, AnsiColors.ANSI_PURPLE, null);
		}
	}
	public static void logInfo(String msg){
		logToGUI(gui, "[Info]", msg, null, Color.BLACK, false, false);
		logAnything(System.out, "[Info]", msg, AnsiColors.ANSI_RESET, null);
	}
	public static void logWarn(String msg){
		logToGUI(gui, "[Warning]", msg, null, Color.YELLOW, false, false);
		logAnything(System.out, "[Warning]", msg, AnsiColors.ANSI_YELLOW, null);
	}
	public static void logError(String msg){
		logToGUI(gui, "[Error]", msg, null, Color.RED, false, false);
		logAnything(System.err, "[Error]", msg, AnsiColors.ANSI_RED, null);
	}
	public static void logError(String msg, Exception e){
		logToGUI(gui, "[Error]", msg, e, Color.RED, false, false);
		logAnything(System.err, "[Error]", msg, AnsiColors.ANSI_RED, e);
	}
	public static void logSevere(String msg, Exception e){
		logToGUI(gui, "[Severe]", msg, e, Color.RED, false, true);
		logAnything(System.err, "[Severe]", msg, AnsiColors.ANSI_RED, e);
		e.printStackTrace();
	}
	public static void logFatal(String msg, Exception e){
		logToGUI(gui, "[Fatal]", msg, e, Color.RED, true, true);
		logAnything(System.err, "[Fatal]", msg, AnsiColors.ANSI_RED, e);
		e.printStackTrace();
		System.exit(-1);
	}
	private static String buildLogMessage(String prefix, String msg, String cause){
		String fullMsg = prefix.trim() + " " + getDatePrefix().trim() + ": " + msg;
		if(cause != null && !cause.matches("")) fullMsg += (" caused by: " + LS + cause);
		return fullMsg;
	}
	private static void logAnything(PrintStream to, String prefix, String msg, String ansi, Exception e){
		if(ansiEnabled) to.print(ansi);
		String fullMsg = buildLogMessage(prefix, msg, e == null ? null : e.toString());
		to.println(fullMsg);
		logToFile(fullMsg + LS);
	}
	private static void logToGUI(GUI to, String prefix, String msg, Exception e, Color color, boolean bold, boolean italic){
		if(to != null){
			String fullMsg = buildLogMessage(prefix, msg, e == null ? null : e.toString());
			to.writeToLog(fullMsg, color, bold, italic, false, false);
		}
	}
	public static String formatTextHTML(String text, Color c, boolean bold, boolean italic, boolean underlined, boolean striked){
		List<String> tags = new ArrayList<String>();
		List<String> ctags = new ArrayList<String>();
		if(bold){tags.add("<b>"); ctags.add("</b>");}
		if(italic){tags.add("<i>"); ctags.add("</i>");}
		if(underlined){tags.add("<u>"); ctags.add("</u>");}
		if(striked){tags.add("<s>"); ctags.add("</s>");}
		String cs = "<font color=rgb(" + c.getRed() + ',' + c.getGreen() + ',' + c.getBlue() + ")>";
		tags.add(cs); ctags.add("</font>");
		String out = "";
		for(String tag : tags) out += tag;
		out += text;
		for(String tag : ctags) out += tag;
		return out;
	}
}
