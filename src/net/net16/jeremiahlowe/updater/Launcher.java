package net.net16.jeremiahlowe.updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Launcher {
	public static String downloadFrom = "https://raw.githubusercontent.com/abc123me/MouseMoverV2/master/updater/version";
	public static String updaterDirectory = "updater/";
	public static void main(String[] args) throws Exception{
		for(int i = 0; i < args.length; i++){
			String arg = args[i].trim();
			if(arg.matches("--disable")) System.exit(0);
			if(arg.matches("--update-dir")) updaterDirectory = args[i + 1].trim();
			if(arg.matches("--get-from")) downloadFrom = args[i + 1].trim();
		}
		try{
			new File(updaterDirectory).mkdir();
			Properties meta = new Properties();
			File metaFile = new File(updaterDirectory + "current.meta");
			metaFile.createNewFile();
			meta.load(new FileInputStream(metaFile));
			if(!meta.containsKey("disableUpdates")) meta.put("disableUpdates", "false");
			File versionFile = Utility.downloadFile(downloadFrom, updaterDirectory + "version");
			List<String> lines = Utility.getVersionFileLines(versionFile);
			if(!Boolean.parseBoolean(meta.getProperty("disableUpdates", "false")) && Updater.needsUpdate(lines, meta.getProperty("version", "NONE"))){
				int option = JOptionPane.showOptionDialog(null, "Update available:" + System.lineSeparator() + "Do you want to update?", 
						"Mouse mover V2", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
						new ImageIcon(ImageGetter.getImage("update.png", 32, 32)), new String[]{"Yes (Recommended)", "No"}, null);
				if(option == 1) System.exit(0);
				else{
					System.out.println("Going on!");
					Updater.update(versionFile, "main.jar");
					meta.setProperty("version", Updater.getVersion(lines));
					meta.store(new FileOutputStream(metaFile), "Set disableUpdates to true to disable updates");
				}
			}
		}
		catch(Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
