package net.net16.jeremiahlowe.mm2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {
	private Properties props;
	private File cfgFile;
	private long moveMillis = 60000;
	private boolean alternateMovements = false;
	private boolean closeToTray = false;
	private boolean minimizeToTray = false;
	private boolean useSystemLF = false;
	private boolean developerMode = false;
	public Config(String cfgFileName) throws Exception{
		cfgFile = new File(cfgFileName);
		if(!cfgFile.exists()) cfgFile.createNewFile();
		props = new Properties();
	}
	private String get(String key, String def){
		String tmp = (String) props.get(key);
		if(tmp == null || tmp.matches("")) tmp = def;
		return tmp;
	}
	private void put(String key, String val){props.put(key, val);}
	private void put(String key, long val){put(key, String.valueOf(val));}
	private void put(String key, boolean val){put(key, String.valueOf(val));}
	public void load() throws Exception{
		props.load(new FileInputStream(cfgFile));
		moveMillis = Long.parseLong(get("moveMillis", "1000"));
		alternateMovements = Boolean.parseBoolean(get("altMove", "true"));
		closeToTray = Boolean.parseBoolean(get("c2tray", "false"));
		minimizeToTray = Boolean.parseBoolean(get("m2tray", "false"));
		useSystemLF = Boolean.parseBoolean(get("sysLF", "true"));
		developerMode = Boolean.parseBoolean(get("devMode", "false"));
		Utility.showDebugMessages = Boolean.parseBoolean(get("logDebug", "false"));
	}
	public void save() throws Exception{
		put("moveMillis", moveMillis);
		put("altMove", alternateMovements);
		put("c2tray", closeToTray);
		put("m2tray", minimizeToTray);
		put("sysLF", useSystemLF);
		put("devMode", developerMode);
		put("logDebug", Utility.showDebugMessages);
		props.store(new FileOutputStream(cfgFile), "Config file for MouseMoverV2");
	}
	public long getMoveMillis() {return moveMillis;}
	public void setMoveMillis(long moveMillis) {this.moveMillis = moveMillis;}
	public boolean isAlternateMovements() {return alternateMovements;}
	public void setAlternateMovements(boolean alternateMovements) {this.alternateMovements = alternateMovements;}
	public boolean isCloseToTray() {return closeToTray;}
	public void setCloseToTray(boolean closeToTray) {this.closeToTray = closeToTray;}
	public boolean isMinimizeToTray() {return minimizeToTray;}
	public void setMinimizeToTray(boolean minimizeToTray) {this.minimizeToTray = minimizeToTray;}
	public boolean isUseSystemLF() {return useSystemLF;}
	public void setUseSystemLF(boolean useSystemLF) {this.useSystemLF = useSystemLF;}
	public boolean isDeveloperMode() {return developerMode;}
	public void setDeveloperMode(boolean developerMode) {this.developerMode = developerMode;}
	public boolean isShowDebugMessages() {return Utility.showDebugMessages;}
	public void setShowDebugMessages(boolean showDebugMessages) {Utility.showDebugMessages = showDebugMessages;}
}
