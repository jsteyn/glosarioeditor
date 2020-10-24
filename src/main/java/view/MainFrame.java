package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import controller.Root;
import model.TermStructure;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2L;
	
	private Root root;
	
	public SelectorPanel selectorPanel;
	public JLabel referencePanelLabel = new JLabel("References:");
	public ReferencePanel referencePanel;
	public StructurePanel structurePanel;
	public StructurePanel readOnlyPanel;
	public String selectedStructurePanel;
	public JButton addButton = new JButton("Add New");
	public JButton subButton = new JButton("Delete");

	public JButton fileChooseButton = new JButton("Select File:");
	public JLabel fileLabel = new JLabel("Select a file to edit contents");
	public JButton fileSaveButton = new JButton("Save");
	
	public File currentFile = null;
	
	public MainFrame(final Root root) {
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
		selectorPanelCon.gridheight = 2;
		selectorPanelCon.fill = GridBagConstraints.BOTH;
		selectorPanelCon.weightx = 0.2;
		selectorPanelCon.weighty = 10;
		add(selectorPanel, selectorPanelCon);
		
		GridBagConstraints addButtonCon = new GridBagConstraints();
		addButtonCon.gridx = 0;
		addButtonCon.gridy = 2;
		addButtonCon.fill = GridBagConstraints.HORIZONTAL;
		addButtonCon.weightx = 0.1;
		addButtonCon.weighty = 0;
		add(addButton, addButtonCon);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				root.addNewTerm();
			}
		});
		
		GridBagConstraints subButtonCon = new GridBagConstraints();
		subButtonCon.gridx = 1;
		subButtonCon.gridy = 2;
		subButtonCon.fill = GridBagConstraints.HORIZONTAL;
		subButtonCon.weightx = 0.1;
		subButtonCon.weighty = 0;
		add(subButton, subButtonCon);
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				root.removeTerm();
			}
		});
		
		
		structurePanel = new StructurePanel(root);
		GridBagConstraints structurePanelCon = new GridBagConstraints();
		structurePanelCon.gridx = 2;
		structurePanelCon.gridy = 1;
		structurePanelCon.fill = GridBagConstraints.BOTH;
		structurePanelCon.weightx = 1;
		structurePanelCon.weighty = 1;
		add(structurePanel, structurePanelCon);
		structurePanel.setBackground(new Color(210, 220, 230));
		
		readOnlyPanel = new StructurePanel(root);
		GridBagConstraints readOnlyPanelCon = new GridBagConstraints();
		readOnlyPanelCon.gridx = 3;
		readOnlyPanelCon.gridy = 1;
		readOnlyPanelCon.fill = GridBagConstraints.BOTH;
		readOnlyPanelCon.weightx = 1;
		readOnlyPanelCon.weighty = 1;
		add(readOnlyPanel, readOnlyPanelCon);
		readOnlyPanel.setReadOnly();
		readOnlyPanel.setBackground(new Color(255, 255, 200));
		
		GridBagConstraints referencePanelLabelCon = new GridBagConstraints();
		referencePanelLabelCon.gridx = 4;
		referencePanelLabelCon.gridy = 0;
		referencePanelLabelCon.weightx = 0;
		referencePanelLabelCon.weighty = 0;
		referencePanelLabelCon.insets = new Insets(10, 20, 0, 0);
		add(referencePanelLabel, referencePanelLabelCon);
		
		referencePanel = new ReferencePanel(root);
		GridBagConstraints referencePanelCon = new GridBagConstraints();
		referencePanelCon.gridx = 4;
		referencePanelCon.gridy = 1;
		referencePanelCon.gridheight = 2;
		referencePanelCon.fill = GridBagConstraints.BOTH;
		referencePanelCon.weightx = 0.2;
		referencePanelCon.weighty = 10;
		add(referencePanel, referencePanelCon);

		GridBagConstraints fileChooseButtonCon = new GridBagConstraints();
		fileChooseButtonCon.gridx = 0;
		fileChooseButtonCon.gridy = 3;
		fileChooseButtonCon.fill = GridBagConstraints.HORIZONTAL;
		fileChooseButtonCon.weightx = 0;
		fileChooseButtonCon.weighty = 0;
		add(fileChooseButton, fileChooseButtonCon);
		fileChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				root.chooseFile();
			}
		});
		
		GridBagConstraints fileLabelCon = new GridBagConstraints();
		fileLabelCon.gridx = 1;
		fileLabelCon.gridy = 3;
		fileLabelCon.gridwidth = 3;
		fileLabelCon.fill = GridBagConstraints.HORIZONTAL;
		fileLabelCon.weightx = 4;
		fileLabelCon.weighty = 0;
		add(fileLabel, fileLabelCon);
		
		GridBagConstraints fileSaveButtonCon = new GridBagConstraints();
		fileSaveButtonCon.gridx = 4;
		fileSaveButtonCon.gridy = 3;
		fileSaveButtonCon.fill = GridBagConstraints.HORIZONTAL;
		fileSaveButtonCon.weightx = 1;
		fileSaveButtonCon.weighty = 0;
		add(fileSaveButton, fileSaveButtonCon);
		fileSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				root.saveFile();
			}
		});
		
		checkEnabled();
		
		setPreferredSize(new Dimension(1200, 600));
		pack();
		setVisible(true);
	}
	
	public void checkEnabled() {
		if (selectorPanel.getSelected() == null) {
			setUnselected();
		} else {
			setSelected();
		}
		if (currentFile == null) {
			fileSaveButton.setEnabled(false);
			addButton.setEnabled(false);
			subButton.setEnabled(false);
		} else {
			fileSaveButton.setEnabled(true);
			addButton.setEnabled(true);
			if (selectorPanel.getCount() == 0) {
				subButton.setEnabled(false);
			} else {
				subButton.setEnabled(true);
			}
		}
	}
	
	public void setUnselected() {
		referencePanel.setEnabled(false);
		structurePanel.setUnselected();
		readOnlyPanel.setUnselected();
	}
	
	public void setSelected() {
		referencePanel.setEnabled(true);
		structurePanel.setSelected();
		readOnlyPanel.setSelected();
	}
	
	public void setSelection() {
		TermStructure struct = selectorPanel.getSelectedStructure();
		structurePanel.setSelection(struct);
		readOnlyPanel.setSelection(struct);
		updateReferences(struct);
	}
	
	public void updateReferences(TermStructure struct) {
		root.lockDocumentListeners = true;

		referencePanel.clearFields();
		if (struct != null) {
			referencePanel.clearFields();
			for (String ref: struct.getRefs()) {
				referencePanel.addField(ref);
			}
		}
		root.lockDocumentListeners = false;
		repaint();
	}
	
}
