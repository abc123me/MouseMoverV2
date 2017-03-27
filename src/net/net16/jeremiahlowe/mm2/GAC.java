package net.net16.jeremiahlowe.mm2;

public interface GAC {
	public void onBreakEverything();
	public void onRestartThreads();
	public void onKillNoHook();
	public void onRestartGUI();
	public void onSaveConfig();
	public void onLoadConfig();
	public void onSetMovementTime(long ms);
	public void onMinTray(boolean minToTray);
	public void onCloseTray(boolean closeTray);
	public void onSetDevMode(boolean enabled);
	public void onSetLF(boolean systemLF);
	public void onSetAlternateMovements(boolean alternate);
	public void onSetDebugMessages(boolean enabled);
}
