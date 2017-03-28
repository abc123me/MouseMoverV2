package net.net16.jeremiahlowe.updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

public class Launcher {
	public static final String downloadFrom = "https://raw.githubusercontent.com/abc123me/MouseMoverV2/master/updater/version";
	public static void main(String[] args) throws Exception{
		try{
			Properties meta = new Properties();
			meta.load(new FileInputStream("current.meta"));
			File versionFile = Utility.downloadFile(downloadFrom, "recent");
			List<String> lines = Utility.getVersionFileLines(versionFile);
			if(Updater.needsUpdate(lines, meta.getProperty("version"))){
				Updater.update(versionFile, "main.jar");
				meta.setProperty("version", Updater.getVersion(lines));
				meta.store(new FileOutputStream("current.meta"), "Delete if you don't want auto-updating");
			}
		}catch(Exception e){}
	}
}