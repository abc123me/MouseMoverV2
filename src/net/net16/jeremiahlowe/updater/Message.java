package net.net16.jeremiahlowe.updater;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JProgressBar;
import java.awt.Font;
import javax.swing.JButton;

public class Message extends JFrame{
	public Message() {
		setTitle("Update");
		getContentPane().setLayout(null);
		
		JLabel lblUpdateAvailable = new JLabel("Update Available");
		lblUpdateAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUpdateAvailable.setBounds(10, 11, 277, 24);
		getContentPane().add(lblUpdateAvailable);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(10, 46, 89, 23);
		getContentPane().add(btnUpdate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 80, 89, 23);
		getContentPane().add(btnCancel);
	}
}
