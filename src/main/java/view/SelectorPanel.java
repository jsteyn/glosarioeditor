package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Root;

public class SelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 2L;
	
	private Root root;
	
	private JPanel mainPanel;
	
	public SelectorPanel(Root root) {
		super(new JPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel = (JPanel)getViewport().getView();
		this.root = root;
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.GRAY);
		
	}
	

}
