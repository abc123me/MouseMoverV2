package net.net16.jeremiahlowe.updater;

import java.io.File;

public class UpdaterStandalone {
	public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/abc123me/MouseMoverV2/master/Updater/version";
	public static final String VERSION_FILE_NAME = "version";
	public static void main(String[] args) throws Throwable{
		System.out.println(Updater.needsUpdate(new File("updater/version"), "1.3"));
	}
	
}
