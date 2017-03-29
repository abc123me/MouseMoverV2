package net.net16.jeremiahlowe.mm2;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean cancelMinuteSpinner = false;
	private boolean cancelSecondSpinner = false;
	private JTabbedPane contentPane;
	private JSpinner minuteSpinner;
	private JSpinner secondSpinner;
	private JCheckBox chckbxAlternateMovements;
	private JCheckBox chckbxMinimizeToTray;
	private JCheckBox chckbxUseSystemLf;
	private JCheckBox chckbxCloseToTray;
	private JCheckBox chckbxDevMode;
	private JButton btnBreakEverything;
	private JButton btnRestartThread;
	private JButton btnKillNohook;
	private JButton btnRestartGui;
	private JButton btnSaveConfig;
	private JButton btnLoadConfig;
	private JTextPane textPane;
	private GAC gac;
	private JTextPane txtLogging;
	private JCheckBox chckbxLogDebug;
	public GUI(GAC gac) {
		this.gac = gac;
		setTitle("Mouse mover V2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JTabbedPane();
		setContentPane(contentPane);
		JPanel settingsPane = new JPanel();
		contentPane.addTab("Settings", null, settingsPane, null);
		Box verticalBox = Box.createVerticalBox();
		settingsPane.add(verticalBox);
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		JLabel lblMoveMouseEvery = new JLabel("Move mouse every:");
		horizontalBox.add(lblMoveMouseEvery);
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_1);
		minuteSpinner = new JSpinner();
		minuteSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		horizontalBox.add(minuteSpinner);
		JLabel lblMinutes = new JLabel("m");
		horizontalBox.add(lblMinutes);
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_2);
		secondSpinner = new JSpinner();
		secondSpinner.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		horizontalBox.add(secondSpinner);
		JLabel lblS = new JLabel("s");
		horizontalBox.add(lblS);
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		Box verticalBox_1 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_1);
		chckbxAlternateMovements = new JCheckBox("Alternate movements");
		verticalBox_1.add(chckbxAlternateMovements);
		chckbxUseSystemLf = new JCheckBox("Use system L+F");
		verticalBox_1.add(chckbxUseSystemLf);
		chckbxMinimizeToTray = new JCheckBox("Minimize to tray WIP");
		verticalBox_1.add(chckbxMinimizeToTray);
		chckbxCloseToTray = new JCheckBox("Close to tray WIP");
		verticalBox_1.add(chckbxCloseToTray);
		chckbxDevMode = new JCheckBox("Developer mode");
		verticalBox_1.add(chckbxDevMode);
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox_1.add(horizontalGlue);
		JScrollPane loggingPane = new JScrollPane();
		contentPane.addTab("Logging", null, loggingPane, null);
		txtLogging = new JTextPane();
		txtLogging.setEditable(false);
		txtLogging.setVisible(true);
		txtLogging.setContentType("text/html");
		loggingPane.setViewportView(txtLogging);
		JPanel devOptionsPanel = new JPanel();
		contentPane.addTab("Developer", null, devOptionsPanel, null);
		contentPane.setEnabledAt(2, false);
		devOptionsPanel.setLayout(null);
		btnBreakEverything = new JButton("Exit gracefully");
		btnBreakEverything.setBounds(10, 11, 140, 23);
		devOptionsPanel.add(btnBreakEverything);
		btnRestartThread = new JButton("Restart thread");
		btnRestartThread.setBounds(10, 45, 140, 23);
		devOptionsPanel.add(btnRestartThread);
		btnKillNohook = new JButton("Kill w/o hook");
		btnKillNohook.setBounds(10, 79, 140, 23);
		devOptionsPanel.add(btnKillNohook);
		btnRestartGui = new JButton("Restart GUI");
		btnRestartGui.setBounds(160, 79, 140, 23);
		devOptionsPanel.add(btnRestartGui);
		btnSaveConfig = new JButton("Save config");
		btnSaveConfig.setBounds(160, 11, 140, 23);
		devOptionsPanel.add(btnSaveConfig);
		btnLoadConfig = new JButton("Load config");
		btnLoadConfig.setBounds(160, 45, 140, 23);
		devOptionsPanel.add(btnLoadConfig);
		JButton btnClearLogs = new JButton("Clear logs");
		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtLogging.setText("<html><body></body></html>");
			}
		});
		btnClearLogs.setBounds(10, 114, 140, 26);
		devOptionsPanel.add(btnClearLogs);
		chckbxLogDebug = new JCheckBox("Log debugging info");
		chckbxLogDebug.setBounds(160, 115, 140, 24);
		chckbxLogDebug.setSelected(Utility.showDebugMessages);
		devOptionsPanel.add(chckbxLogDebug);
		JScrollPane licensePane = new JScrollPane();
		contentPane.addTab("License", null, licensePane, null);
		textPane = new JTextPane();
		doLicense();
		textPane.setEditable(false);
		licensePane.setViewportView(textPane);
		addActionListeners();
	}
	private void doLicense(){
		textPane.setText("Copyright 2017 Jeremiah Lowe\r\n\r\n"
				+ "Permission is hereby granted, free of charge, "
				+ "to any person obtaining a copy of this software and "
				+ "associated documentation files (the \"Software\"), "
				+ "to deal in the Software without restriction, including without "
				+ "limitation the rights to use, copy, modify, merge, publish, distribute, "
				+ "sublicense, and/or sell copies of the Software, and to permit persons "
				+ "to whom the Software is furnished to do so, subject to the following conditions:\r\n\r\n"
				+ "The above copyright notice and this permission notice shall be included "
				+ "in all copies or substantial portions of the Software.\r\n\r\n"
				+ "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, "
				+ "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF "
				+ "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. "
				+ "IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR "
				+ "ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, "
				+ "TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE "
				+ "SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
		textPane.setCaretPosition(0);
	}
	private void addActionListeners(){
		//Window
		addWindowListener(new WindowAdapter(){
			@Override public void windowClosing(WindowEvent we){gac.onCloseTray(getCloseToTray());}});
		addWindowListener(new WindowAdapter(){
			@Override public void windowIconified(WindowEvent we){gac.onMinTray(getMinimizeToTray());}});
		//Buttons
		btnBreakEverything.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onBreakEverything();}});
		btnKillNohook.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onKillNoHook();}});
		btnRestartGui.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onRestartGUI();}});
		btnRestartThread.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onRestartThreads();}});
		btnLoadConfig.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onLoadConfig();}});
		//Check boxes
		chckbxCloseToTray.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onCloseTray(chckbxCloseToTray.isSelected());}});
		chckbxDevMode.addActionListener(new UniversalListener(){
			@Override public void run(){
				gac.onSetDevMode(chckbxDevMode.isSelected());
				setDevMode(chckbxDevMode.isSelected());
			}
		});
		chckbxMinimizeToTray.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onMinTray(chckbxMinimizeToTray.isSelected());}});
		chckbxUseSystemLf.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onSetLF(chckbxUseSystemLf.isSelected());}});
		chckbxAlternateMovements.addActionListener(new UniversalListener(){
			@Override public void run(){gac.onSetAlternateMovements(chckbxAlternateMovements.isSelected());}});
		chckbxLogDebug.addActionListener(new UniversalListener() {
			@Override public void run() {gac.onSetDebugMessages(chckbxLogDebug.isSelected());}});
		//Spinners
		minuteSpinner.addChangeListener(new UniversalListener(){
			@Override public void run(){
				if(cancelMinuteSpinner){
					cancelMinuteSpinner = false;
					return;
				}
				gac.onSetMovementTime(getMouseMoveMillis());
			}});
		secondSpinner.addChangeListener(new UniversalListener(){
			@Override public void run(){
				if(cancelSecondSpinner){
					cancelSecondSpinner = false;
					return;
				}
				gac.onSetMovementTime(getMouseMoveMillis());
			}});
	}
	public void copyValuesToConfig(Config cfg){
		cfg.setAlternateMovements(getAlternateMovements());
		cfg.setMoveMillis(getMouseMoveMillis());
		cfg.setCloseToTray(getCloseToTray());
		cfg.setMinimizeToTray(getMinimizeToTray());
		cfg.setDeveloperMode(getDevMode());
		cfg.setUseSystemLF(getSystemLF());
		cfg.setShowDebugMessages(getDebugLogging());
	}
	public void loadValuesFromConfig(Config cfg) {
		setMouseMoveMillis(cfg.getMoveMillis());
		setAlternateMovements(cfg.isAlternateMovements());
		setCloseToTray(cfg.isCloseToTray());
		setMinimizeToTray(cfg.isMinimizeToTray());
		setDevMode(cfg.isDeveloperMode());
		setSystemLF(cfg.isUseSystemLF());
		setDebugLogging(cfg.isShowDebugMessages());
	}
	public synchronized void writeToLog(String text, Color c, boolean bold, boolean italic, boolean underlined, boolean striked){
		/*SwingUtilities.invokeLater(new Runnable() {@Override public void run(){
		String html = Utility.formatTextHTML(text, c, bold, italic, underlined, striked);
		String old = txtLogging.getText();
		old = old.replaceAll("<html>", "");
		old = old.replaceAll("</html>", "");
		old = old.replaceAll("<head>", "");
		old = old.replaceAll("</head>", "");
		old = old.replaceAll("<body>", "");
		old = old.replaceAll("</body>", "");
		String header = "<html><body style=\"font-family:arial;\" >";
		String footer = "<br></body></html>";
		try {
			txtLogging.getDocument().insertString(0, "hello", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtLogging.repaint();
		System.out.println(Thread.currentThread().getName());
		}});*/
	}
	public long getMouseMoveMillis(){
		long seconds = (int) secondSpinner.getValue();
		seconds += ((int) minuteSpinner.getValue()) * 60l;
		return seconds * 1000L;
	}
	public void setMouseMoveMillis(long ms){
		long totalSeconds = ms / 1000;
		int minutes = (int) (totalSeconds / 60);
		int seconds = (int) (totalSeconds % 60);
		if(minutes >= 60){
			minutes = 59;
			seconds = 59;
		}
		cancelMinuteSpinner = true;
		minuteSpinner.setValue(minutes);
		cancelSecondSpinner = true;
		secondSpinner.setValue(seconds);
	}
	public void setDebugLogging(boolean en){chckbxLogDebug.setSelected(en);}
	public void setAlternateMovements(boolean en){chckbxAlternateMovements.setSelected(en);}
	public void setMinimizeToTray(boolean en){chckbxMinimizeToTray.setSelected(en);}
	public void setSystemLF(boolean en){chckbxUseSystemLf.setSelected(en);}
	public void setCloseToTray(boolean en){chckbxCloseToTray.setSelected(en);}
	public void setDevMode(boolean en){
		chckbxDevMode.setSelected(en);
		contentPane.setEnabledAt(2, en);
	}
	public boolean getDebugLogging(){return chckbxLogDebug.isSelected();}
	public boolean getAlternateMovements(){return chckbxAlternateMovements.isSelected();}
	public boolean getMinimizeToTray(){return chckbxMinimizeToTray.isSelected();}
	public boolean getSystemLF(){return chckbxUseSystemLf.isSelected();}
	public boolean getCloseToTray(){return chckbxCloseToTray.isSelected();}
	public boolean getDevMode(){return chckbxDevMode.isSelected();}
}
