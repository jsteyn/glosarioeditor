package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Root;
import model.DefinitionStructure;
import model.TermStructure;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2L;
	
	private Root root;
	
	public SelectorPanel selectorPanel;
	public ReferencePanel referencePanel;
	public JButton addButton = new JButton("Add New");
	public JButton subButton = new JButton("Delete");

	public JLabel idFieldLabel = new JLabel("ID:");
	public JTextField idField = new JTextField();
	public AutoCompleteComboBox languageSelect = new AutoCompleteComboBox(new String[] {"None"});
	public JLabel termFieldLabel = new JLabel("Term:");
	public JTextField termField = new JTextField();
	public JLabel acronymFieldLabel = new JLabel("Acronym");
	public JTextField acronymField = new JTextField();
	public JLabel definitionAreaLabel = new JLabel("Definition:");
	public JTextPane definitionArea = new JTextPane();
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
		selectorPanelCon.gridheight = 4;
		selectorPanelCon.fill = GridBagConstraints.BOTH;
		selectorPanelCon.weightx = 0.2;
		selectorPanelCon.weighty = 10;
		add(selectorPanel, selectorPanelCon);
		
		GridBagConstraints addButtonCon = new GridBagConstraints();
		addButtonCon.gridx = 0;
		addButtonCon.gridy = 4;
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
		subButtonCon.gridy = 4;
		subButtonCon.fill = GridBagConstraints.HORIZONTAL;
		subButtonCon.weightx = 0.1;
		subButtonCon.weighty = 0;
		add(subButton, subButtonCon);
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				root.removeTerm();
			}
		});
		
		
		GridBagConstraints nameFieldLabelCon = new GridBagConstraints();
		nameFieldLabelCon.gridx = 2;
		nameFieldLabelCon.gridy = 0;
		nameFieldLabelCon.weightx = 0;
		nameFieldLabelCon.weighty = 0;
		add(idFieldLabel, nameFieldLabelCon);
		
		GridBagConstraints nameFieldCon = new GridBagConstraints();
		nameFieldCon.gridx = 3;
		nameFieldCon.gridy = 0;
		nameFieldCon.fill = GridBagConstraints.HORIZONTAL;
		nameFieldCon.weightx = 1;
		nameFieldCon.weighty = 0;
		add(idField, nameFieldCon);
		idField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			public void changedUpdate(DocumentEvent e) {

			}
			
			public void update(DocumentEvent e) {
				root.onIdChange();
			}
		});
		
		GridBagConstraints languageSelectCon = new GridBagConstraints();
		languageSelectCon.gridx = 4;
		languageSelectCon.gridy = 0;
		languageSelectCon.gridwidth = 2;
		languageSelectCon.fill = GridBagConstraints.HORIZONTAL;
		languageSelectCon.weightx = 0.5;
		languageSelectCon.weighty = 0;
		add(languageSelect, languageSelectCon);
		languageSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println(e.getStateChange());
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("SELECTED");
					root.onLanguageSelect();
				}
			}
		});
		languageSelect.inputField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					root.addLanguage();
				}
			}
		});
		
		GridBagConstraints termFieldLabelCon = new GridBagConstraints();
		termFieldLabelCon.gridx = 2;
		termFieldLabelCon.gridy = 1;
		termFieldLabelCon.weightx = 0.1;
		termFieldLabelCon.weighty = 0;
		add(termFieldLabel, termFieldLabelCon);
		
		GridBagConstraints termFieldCon = new GridBagConstraints();
		termFieldCon.gridx = 3;
		termFieldCon.gridy = 1;
		termFieldCon.fill = GridBagConstraints.HORIZONTAL;
		termFieldCon.weightx = 0.1;
		termFieldCon.weighty = 0;
		add(termField, termFieldCon);
		termField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			public void changedUpdate(DocumentEvent e) {

			}
			
			public void update(DocumentEvent e) {
				root.onTermChange();
			}
		});
		
		GridBagConstraints acronymFieldLabelCon = new GridBagConstraints();
		acronymFieldLabelCon.gridx = 4;
		acronymFieldLabelCon.gridy = 1;
		acronymFieldLabelCon.weightx = 0.1;
		acronymFieldLabelCon.weighty = 0;
		add(acronymFieldLabel, acronymFieldLabelCon);
		
		GridBagConstraints acronymFieldCon = new GridBagConstraints();
		acronymFieldCon.gridx = 5;
		acronymFieldCon.gridy = 1;
		acronymFieldCon.fill = GridBagConstraints.HORIZONTAL;
		acronymFieldCon.weightx = 0.1;
		acronymFieldCon.weighty = 0;
		add(acronymField, acronymFieldCon);
		acronymField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			public void changedUpdate(DocumentEvent e) {

			}
			
			public void update(DocumentEvent e) {
				root.onAcronymChange();
			}
		});
		
		GridBagConstraints definitionAreaLabelCon = new GridBagConstraints();
		definitionAreaLabelCon.gridx = 2;
		definitionAreaLabelCon.gridy = 2;
		definitionAreaLabelCon.weightx = 0;
		definitionAreaLabelCon.weighty = 0;
		definitionAreaLabelCon.insets = new Insets(10, 20, 0, 0);
		add(definitionAreaLabel, definitionAreaLabelCon);
		
		GridBagConstraints definitionAreaCon = new GridBagConstraints();
		definitionAreaCon.gridx = 2;
		definitionAreaCon.gridy = 3;
		definitionAreaCon.gridwidth = 3;
		definitionAreaCon.fill = GridBagConstraints.BOTH;
		definitionAreaCon.weightx = 1;
		definitionAreaCon.weighty = 9;
		definitionAreaCon.insets = new Insets(0, 20, 20, 20);
		add(definitionArea, definitionAreaCon);
		definitionArea.setEnabled(false);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
		definitionArea.setParagraphAttributes(attribs, true);
		definitionArea.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			public void changedUpdate(DocumentEvent e) {

			}
			
			public void update(DocumentEvent e) {
				root.onDefinitionChange();
			}
		});
		

		referencePanel = new ReferencePanel(root);
		GridBagConstraints referencePanelCon = new GridBagConstraints();
		referencePanelCon.gridx = 5;
		referencePanelCon.gridy = 3;
		referencePanelCon.fill = GridBagConstraints.BOTH;
		referencePanelCon.weightx = 0.2;
		referencePanelCon.weighty = 10;
		add(referencePanel, referencePanelCon);

		GridBagConstraints fileChooseButtonCon = new GridBagConstraints();
		fileChooseButtonCon.gridx = 0;
		fileChooseButtonCon.gridy = 5;
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
		fileLabelCon.gridy = 5;
		fileLabelCon.gridwidth = 4;
		fileLabelCon.fill = GridBagConstraints.HORIZONTAL;
		fileLabelCon.weightx = 4;
		fileLabelCon.weighty = 0;
		add(fileLabel, fileLabelCon);
		
		GridBagConstraints fileSaveButtonCon = new GridBagConstraints();
		fileSaveButtonCon.gridx = 5;
		fileSaveButtonCon.gridy = 5;
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
		
		setPreferredSize(new Dimension(800, 600));
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
		idField.setEnabled(false);
		languageSelect.setEnabled(false);
		termField.setEnabled(false);
		acronymField.setEnabled(false);
		definitionArea.setEnabled(false);
	}
	
	public void setSelected() {
		referencePanel.setEnabled(true);
		idField.setEnabled(true);
		languageSelect.setEnabled(true);
		if (languageSelect.getSelectedItem().equals("None")) {
			termField.setEnabled(false);
			acronymField.setEnabled(false);
			definitionArea.setEnabled(false);
		} else {
			termField.setEnabled(true);
			acronymField.setEnabled(true);
			definitionArea.setEnabled(true);
		}
	}
	
	public void setSelection() {
		if (selectorPanel.getSelected() == null) {
			idField.setText("");
			languageSelect.removeAllItems();
			languageSelect.addItem("None");
			referencePanel.clearFields();
		} else {
			TermStructure struct = selectorPanel.getSelected().getStruct();
			idField.setText(struct.getId());
			languageSelect.removeAllItems();
			languageSelect.addItem("None");
			System.out.println(languageSelect.getItemCount());
			for (String lan: struct.getLanguages()) {
				languageSelect.addItem(lan);
			}
			int index = 0;
			referencePanel.clearFields();
			for (String ref: struct.getRefs()) {
				referencePanel.addField(ref);
			}
		}
		setLanguage();
		repaint();
	}
	
	public void setLanguage() {
		String language = (String)languageSelect.getSelectedItem();
		if (language.equals("None")) {
			termField.setText("");
			acronymField.setText("");
			definitionArea.setText("");
		} else {
			TermStructure struct = selectorPanel.getSelected().getStruct();
			termField.setText(struct.getTerm(language));
			acronymField.setText(struct.getAcronym(language));
			definitionArea.setText(struct.getDefinition(language));
		}
		
	}

}
