package net.net16.jeremiahlowe.updater;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageGetter {
	public static final ImageGetter iconGetterInstance = new ImageGetter();
	public static Image getImage(String in){
		return new ImageIcon(iconGetterInstance.getClass().getResource(in)).getImage();
	}
	public static Image getImage(String in, int w, int h){
		return getImage(in).getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
}
