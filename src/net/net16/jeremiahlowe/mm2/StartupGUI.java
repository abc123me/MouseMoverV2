package net.net16.jeremiahlowe.mm2;

import java.awt.Rectangle;

import javax.swing.UIManager;

public class StartupGUI implements GAC{
	public static StartupGUI instance;
	public static void main(String[] args){instance = new StartupGUI(args);}
	
	public MouseMover mouseMover;
	public Thread shutdownHook;
	public Config cfg;
	public GUI gui;
	
	public StartupGUI(String[] args){
		parseArgs(args);
		Utility.initLogFile("latest_log.txt");
		try{cfg = new Config("config.txt");}
		catch(Exception e){Utility.logError("Couldn't init config", e);}
		Utility.logDebug("Pre-Init finished");
		gui = new GUI(this);
		Utility.gui = gui;
		gui.setVisible(true);
		onLoadConfig();
		Utility.logDebug("Init finished");
		if(cfg.isUseSystemLF()) onSetLF(cfg.isUseSystemLF());
		mouseMover = new MouseMover();
		mouseMover.start();
		mouseMover.setAlternate(cfg.isAlternateMovements());
		mouseMover.setWaitTime(cfg.getMoveMillis());
		Utility.logDebug("Post-Init finished");
		addHook(args);
	}
	public void addHook(String[] args){
		shutdownHook = new Thread(new Runnable(){
			@Override
			public void run(){
				mouseMover.stop();
				try{cfg.save();}catch(Exception e){}
				if(Utility.ansiEnabled) System.out.print(AnsiColors.ANSI_RESET);
			}
		});
	}
	public void parseArgs(String[] args){
		for(int i = 0; i < args.length; i++){
			String s = args[i];
			if(s.matches("--debug-mode") || s.matches("-d")) Utility.showDebugMessageOverride = true;
			if(s.matches("--disable-ansi")) Utility.ansiEnabled = false;
		}
	}
	//[start] GAC
	@Override
	public void onBreakEverything() {
		Utility.logFatal("Everything broke!", new RuntimeException("Everything just broke"));
	}
	@Override
	public void onRestartThreads() {
		mouseMover.restart();
		mouseMover.setAlternate(cfg.isAlternateMovements());
		mouseMover.setWaitTime(cfg.getMoveMillis());
	}
	@Override
	public void onKillNoHook() {
		Runtime.getRuntime().removeShutdownHook(shutdownHook);
		System.exit(-1);
	}
	@Override
	public void onRestartGUI() {
		onSaveConfig();
		Rectangle r = gui.getBounds();
		gui.setVisible(false);
		gui = new GUI(this);
		gui.setBounds(r);
		gui.setVisible(true);
		onLoadConfig();
	}
	@Override
	public void onSaveConfig() {
		try{
			gui.copyValuesToConfig(cfg);
			cfg.save();
			Utility.logDebug("Saved config");
		}catch(Exception e){
			Utility.logSevere("Couldn't save config", e);
		}
	}
	@Override
	public void onSetMovementTime(long ms) {
		mouseMover.setWaitTime(ms);
		onSaveConfig();
	}
	@Override
	public void onMinTray(boolean minToTray) {
		onSaveConfig();
	}
	@Override
	public void onCloseTray(boolean closeTray) {
		onSaveConfig();
	}
	@Override
	public void onSetDevMode(boolean enabled) {
		if(enabled) Utility.logWarn("Be careful in developer mode, I am not liable for ANY damages!");
		onSaveConfig();
	}
	@Override
	public void onSetLF(boolean systemLF) {
		try{
			if(systemLF) UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			else UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			onRestartGUI();
		}catch(Exception e){
			Utility.logError("Couldn't set look and feel" , e);
		}
		onSaveConfig();
	}
	@Override
	public void onLoadConfig() {
		Utility.logDebug("Loading config");
		try{
			cfg.load();
		}catch(Exception e){
			Utility.logSevere("Couldn't load config", e);
		}
		if(gui != null) gui.loadValuesFromConfig(cfg);
		Utility.logDebug("Loaded config");
	}
	@Override
	public void onSetAlternateMovements(boolean alternate) {
		mouseMover.setAlternate(alternate);
		onSaveConfig();
	}
	@Override
	public void onSetDebugMessages(boolean enabled) {
		Utility.showDebugMessages = enabled;
		onSaveConfig();
	}
	//[end]
}
