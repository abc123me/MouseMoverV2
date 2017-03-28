package net.net16.jeremiahlowe.updater;

import java.io.File;

public class UpdaterTester {
	public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/abc123me/MouseMoverV2/master/Updater/version";
	public static final String VERSION_FILE_NAME = "version";
	public static void main(String[] args) throws Throwable{
		File v = new File("updater/version"); 
		System.out.println(Updater.needsUpdate(v, "1.3"));
		Updater.update(v, "hello.txt");
	}
	
}
