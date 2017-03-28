package net.net16.jeremiahlowe;

import net.net16.jeremiahlowe.mm2.StartupGUI;
import net.net16.jeremiahlowe.updater.Metadata;
import net.net16.jeremiahlowe.updater.Updater;

public class Launcher {
	public static void main(String[] args) throws Exception{
		Updater.getUpdated(Metadata.UPDATE_FROM, "main.jar");
		StartupGUI.main(args);
	}
}
