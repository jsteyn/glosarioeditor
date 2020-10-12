package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Root;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 3L;
	
	private Root root;
	
	public SelectorPanel selectorPanel;
	public JButton addButton = new JButton("Add New");
	public JButton subButton = new JButton("Delete");

	public JLabel nameFieldLabel = new JLabel("Term:");
	public JTextField nameField = new JTextField();
	public AutoCompleteComboBox languageSelect = new AutoCompleteComboBox(new String[] {"None"});
	public JLabel definitionAreaLabel = new JLabel("Definition:");
	public JTextArea definitionArea = new JTextArea();
	public JLabel fileSelectLabel = new JLabel("Select File:");
	public JTextField fileField = new JTextField();
	public JButton fileChooseButton = new JButton("Select File");
	
	public MainFrame(Root root) {
		super("Glosario Editor");
		this.root = root;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		selectorPanel = new SelectorPanel(root);
		GridBagConstraints selectorPanelCon = new GridBagConstraints();
		selectorPanelCon.gridx = 0;
		selectorPanelCon.gridy = 0;
		selectorPanelCon.gridwidth = 2;
		selectorPanelCon.gridheight = 3;
		selectorPanelCon.fill = GridBagConstraints.BOTH;
		selectorPanelCon.weightx = 0.2;
		selectorPanelCon.weighty = 10;
		add(selectorPanel, selectorPanelCon);
		
		GridBagConstraints addButtonCon = new GridBagConstraints();
		addButtonCon.gridx = 0;
		addButtonCon.gridy = 3;
		addButtonCon.fill = GridBagConstraints.HORIZONTAL;
		addButtonCon.weightx = 0.1;
		addButtonCon.weighty = 0;
		add(addButton, addButtonCon);
		
		GridBagConstraints subButtonCon = new GridBagConstraints();
		subButtonCon.gridx = 1;
		subButtonCon.gridy = 3;
		subButtonCon.fill = GridBagConstraints.HORIZONTAL;
		subButtonCon.weightx = 0.1;
		subButtonCon.weighty = 0;
		add(subButton, subButtonCon);
		subButton.setEnabled(false);
		
		
		GridBagConstraints nameFieldLabelCon = new GridBagConstraints();
		nameFieldLabelCon.gridx = 2;
		nameFieldLabelCon.gridy = 0;
		nameFieldLabelCon.weightx = 0;
		nameFieldLabelCon.weighty = 0;
		add(nameFieldLabel, nameFieldLabelCon);
		
		GridBagConstraints nameFieldCon = new GridBagConstraints();
		nameFieldCon.gridx = 3;
		nameFieldCon.gridy = 0;
		nameFieldCon.fill = GridBagConstraints.HORIZONTAL;
		nameFieldCon.weightx = 1;
		nameFieldCon.weighty = 0;
		add(nameField, nameFieldCon);
		
		GridBagConstraints languageSelectCon = new GridBagConstraints();
		languageSelectCon.gridx = 4;
		languageSelectCon.gridy = 0;
		languageSelectCon.gridwidth = 2;
		languageSelectCon.fill = GridBagConstraints.HORIZONTAL;
		languageSelectCon.weightx = 0.5;
		languageSelectCon.weighty = 0;
		add(languageSelect, languageSelectCon);
		
		GridBagConstraints definitionAreaLabelCon = new GridBagConstraints();
		definitionAreaLabelCon.gridx = 2;
		definitionAreaLabelCon.gridy = 1;
		definitionAreaLabelCon.weightx = 0;
		definitionAreaLabelCon.weighty = 0;
		definitionAreaLabelCon.insets = new Insets(10, 20, 0, 0);
		add(definitionAreaLabel, definitionAreaLabelCon);
		
		GridBagConstraints definitionAreaCon = new GridBagConstraints();
		definitionAreaCon.gridx = 2;
		definitionAreaCon.gridy = 2;
		definitionAreaCon.gridwidth = 5;
		definitionAreaCon.fill = GridBagConstraints.BOTH;
		definitionAreaCon.weightx = 1;
		definitionAreaCon.weighty = 9;
		definitionAreaCon.insets = new Insets(0, 20, 20, 20);
		add(definitionArea, definitionAreaCon);
		definitionArea.setEnabled(false);
		
		GridBagConstraints fileSelectLabelCon = new GridBagConstraints();
		fileSelectLabelCon.gridx = 0;
		fileSelectLabelCon.gridy = 4;
		fileSelectLabelCon.weightx = 0;
		fileSelectLabelCon.weighty = 0;
		add(fileSelectLabel, fileSelectLabelCon);
		
		GridBagConstraints fileFieldCon = new GridBagConstraints();
		fileFieldCon.gridx = 1;
		fileFieldCon.gridy = 4;
		fileFieldCon.gridwidth = 4;
		fileFieldCon.fill = GridBagConstraints.HORIZONTAL;
		fileFieldCon.weightx = 4;
		fileFieldCon.weighty = 0;
		add(fileField, fileFieldCon);
		
		GridBagConstraints fileChooseButtonCon = new GridBagConstraints();
		fileChooseButtonCon.gridx = 5;
		fileChooseButtonCon.gridy = 4;
		fileChooseButtonCon.fill = GridBagConstraints.HORIZONTAL;
		fileChooseButtonCon.weightx = 0;
		fileChooseButtonCon.weighty = 0;
		add(fileChooseButton, fileChooseButtonCon);
		
		setPreferredSize(new Dimension(800, 600));
		pack();
		setVisible(true);
	}

}
