package net.net16.jeremiahlowe.mm2;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class UpdaterStandalone {
	public static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/abc123me/MouseMoverV2/master/Updater/version";
	public static final String VERSION_FILE_NAME = "version";
	public static void main(String[] args) {
		try {
			File f = downloadFile(VERSION_FILE_URL, VERSION_FILE_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File downloadFile(String url, String fileName) throws Exception{
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		return new File(fileName);
	}
}
