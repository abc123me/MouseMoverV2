package net.net16.jeremiahlowe.mm2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class UniversalListener implements ChangeListener, ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, WindowListener, Runnable{
	@Override public final void actionPerformed(ActionEvent ae){run();}
	@Override public final void stateChanged(ChangeEvent arg0) {run();}
	@Override public void windowActivated(WindowEvent arg0) {run();}
	@Override public void windowClosed(WindowEvent arg0) {run();}
	@Override public void windowClosing(WindowEvent arg0) {run();}
	@Override public void windowDeactivated(WindowEvent arg0) {run();}
	@Override public void windowDeiconified(WindowEvent arg0) {run();}
	@Override public void windowIconified(WindowEvent arg0) {run();}
	@Override public void windowOpened(WindowEvent arg0) {run();}
	@Override public void mouseClicked(MouseEvent arg0) {run();}
	@Override public void mouseEntered(MouseEvent arg0) {run();}
	@Override public void mouseExited(MouseEvent arg0) {run();}
	@Override public void mousePressed(MouseEvent arg0) {run();}
	@Override public void mouseReleased(MouseEvent arg0) {run();}
	@Override public void keyPressed(KeyEvent arg0) {run();}
	@Override public void keyReleased(KeyEvent arg0) {run();}
	@Override public void keyTyped(KeyEvent arg0) {run();}
	@Override public void mouseWheelMoved(MouseWheelEvent arg0) {run();}
	@Override public void mouseDragged(MouseEvent arg0) {run();}
	@Override public void mouseMoved(MouseEvent arg0) {run();}
}