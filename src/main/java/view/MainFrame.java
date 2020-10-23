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
	public StructurePanel leftStructure;
	public StructurePanel rightStructure;
	public JRadioButton leftRadio = new JRadioButton("LEFT");
	public JRadioButton rightRadio = new JRadioButton("RIGHT");
	public ButtonGroup structureButtonGroup = new ButtonGroup();
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
		
		
		GridBagConstraints leftRadioCon = new GridBagConstraints();
		leftRadioCon.gridx = 2;
		leftRadioCon.gridy = 0;
		leftRadioCon.weightx = 0;
		leftRadioCon.weighty = 0;
		add(leftRadio, leftRadioCon);
		leftRadio.setActionCommand("LEFT");
		leftRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				root.setStructureSelection();
			}
		});
		leftRadio.setSelected(true);

		GridBagConstraints rightRadioCon = new GridBagConstraints();
		rightRadioCon.gridx = 3;
		rightRadioCon.gridy = 0;
		rightRadioCon.weightx = 0;
		rightRadioCon.weighty = 0;
		add(rightRadio, rightRadioCon);
		rightRadio.setActionCommand("RIGHT");
		rightRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				root.setStructureSelection();
			}
		});
		
		structureButtonGroup.add(leftRadio);
		structureButtonGroup.add(rightRadio);

		
		leftStructure = new StructurePanel(root);
		GridBagConstraints leftStructureCon = new GridBagConstraints();
		leftStructureCon.gridx = 2;
		leftStructureCon.gridy = 1;
		leftStructureCon.fill = GridBagConstraints.BOTH;
		leftStructureCon.weightx = 1;
		leftStructureCon.weighty = 1;
		add(leftStructure, leftStructureCon);
		leftStructure.setBackground(new Color(255, 255, 180));
		
		rightStructure = new StructurePanel(root);
		GridBagConstraints rightStructureCon = new GridBagConstraints();
		rightStructureCon.gridx = 3;
		rightStructureCon.gridy = 1;
		rightStructureCon.fill = GridBagConstraints.BOTH;
		rightStructureCon.weightx = 1;
		rightStructureCon.weighty = 1;
		add(rightStructure, rightStructureCon);
		rightStructure.setBackground(new Color(180, 200, 255));
		

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
		
		setStructureSelection();
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
		leftStructure.setUnselected();
		rightStructure.setUnselected();
	}
	
	public void setSelected() {
		referencePanel.setEnabled(true);
		if (doLeftSelection()) {
			leftStructure.setSelected();
			rightStructure.setUnselected();
		} else {
			leftStructure.setUnselected();
			rightStructure.setSelected();
		}
	}
	
	public void setStructureSelection() {
		if (structureButtonGroup.getSelection().getActionCommand().equals(leftRadio.getActionCommand())) {
			selectedStructurePanel = leftRadio.getActionCommand();
		} else {
			selectedStructurePanel = rightRadio.getActionCommand();
		}
		checkEnabled();
		updateReferences(selectorPanel.getSelectedStructure());
	}
	
	public void setSelection() {
		TermStructure struct = selectorPanel.getSelectedStructure();
		getStructurePanel().setSelection(struct);
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
	
	public StructurePanel getStructurePanel() {
		if (doLeftSelection()) {
			return leftStructure;
		} else {
			return rightStructure;
		}
	}
	
	public boolean doLeftSelection() {
		return selectedStructurePanel.equals(leftRadio.getActionCommand());
	}
	
}
