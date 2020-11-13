package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Root;
import model.TermStructure;

public class StructurePanel extends JPanel {
	
	private static final long serialVersionUID = 5L;
	
	private Root root;

	public JLabel idFieldLabel = new JLabel("ID:");
	public JTextField idField = new JTextField();
	public AutoCompleteComboBox languageSelect = new AutoCompleteComboBox(new String[] {"None"});
	public JLabel termFieldLabel = new JLabel("Term:");
	public JTextField termField = new JTextField();
	public JLabel acronymFieldLabel = new JLabel("Acronym");
	public JTextField acronymField = new JTextField();
	public JLabel definitionAreaLabel = new JLabel("Definition:");
	public JTextPane definitionArea = new JTextPane();
	
	public boolean readOnly = false;
	
	public StructurePanel(final Root root) {
		super();
		this.root = root;
		setLayout(new GridBagLayout());

		GridBagConstraints idFieldLabelCon = new GridBagConstraints();
		idFieldLabelCon.gridx = 2;
		idFieldLabelCon.gridy = 0;
		idFieldLabelCon.weightx = 0;
		idFieldLabelCon.weighty = 0;
		add(idFieldLabel, idFieldLabelCon);
		
		GridBagConstraints idFieldCon = new GridBagConstraints();
		idFieldCon.gridx = 3;
		idFieldCon.gridy = 0;
		idFieldCon.fill = GridBagConstraints.HORIZONTAL;
		idFieldCon.weightx = 1;
		idFieldCon.weighty = 0;
		add(idField, idFieldCon);
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
				if (!root.lockDocumentListeners) {
					root.onIdChange();
				}
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
				if (e.getStateChange() == ItemEvent.SELECTED) {
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
				if (!root.lockDocumentListeners) {
					root.onTermChange();
				}
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
				if (!root.lockDocumentListeners) {
					root.onAcronymChange();
				}
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
		definitionAreaCon.gridwidth = 4;
		definitionAreaCon.fill = GridBagConstraints.BOTH;
		definitionAreaCon.weightx = 1;
		definitionAreaCon.weighty = 9;
		definitionAreaCon.insets = new Insets(0, 20, 20, 20);
		add(definitionArea, definitionAreaCon);
		definitionArea.setEnabled(false);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		// StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
		// definitionArea.setParagraphAttributes(attribs, true);
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
				if (!root.lockDocumentListeners) {
					root.onDefinitionChange();
				}
			}
		});
		
	}
	
	public void setReadOnly() {
		readOnly = true;
	}
	
	public void setUnselected() {
		idField.setEnabled(false);
		languageSelect.setEnabled(false);
		termField.setEnabled(false);
		acronymField.setEnabled(false);
		definitionArea.setEnabled(false);
	}
	
	public void setSelected() {
		idField.setEnabled(true);
		languageSelect.setEnabled(true);
		if (languageSelect.getSelectedItem().equals("None")) {
			termField.setEnabled(false);
			acronymField.setEnabled(false);
			definitionArea.setEnabled(false);
		} else {
			termField.setEnabled(!readOnly);
			acronymField.setEnabled(!readOnly);
			definitionArea.setEnabled(!readOnly);
		}
	}
	
	public void setSelection(TermStructure struct) {
		if (struct == null) {
			idField.setText("");
			languageSelect.removeAllItems();
			languageSelect.addItem("None");
		} else {
			idField.setText(struct.getId());
			languageSelect.removeAllItems();
			languageSelect.addItem("None");
			for (String lan: struct.getLanguages()) {
				languageSelect.addItem(lan);
			}
			if (struct.getLanguages().contains("en")) {
				root.lockDocumentListeners = true;
				languageSelect.setSelectedItem("en");
				root.lockDocumentListeners = false;
			}
		}
		setLanguage(struct);
	}
	
	public void setLanguage(TermStructure struct) {
		String language = (String)languageSelect.getSelectedItem();
		root.lockDocumentListeners = true;
		if (language.equals("None")) {
			termField.setText("");
			acronymField.setText("");
			definitionArea.setText("");
		} else {
			termField.setText(struct.getTerm(language));
			acronymField.setText(struct.getAcronym(language));
			definitionArea.setText(struct.getDefinition(language));
		}
		root.lockDocumentListeners = false;
	}

}
