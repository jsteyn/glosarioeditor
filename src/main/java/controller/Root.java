package controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import view.MainFrame;

public class Root {
	
	private static final long serialVersionUID = 1L;
	
	private MainFrame mainFrame;
	
	public static void main(String[] args) {
		new Root();
	}
	
	public Root() {
		mainFrame = new MainFrame(this);
	}

}
