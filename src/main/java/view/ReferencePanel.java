package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Root;
import model.TermStructure;

public class ReferencePanel extends JScrollPane {

	private static final long serialVersionUID = 4L;
	
	private Root root;
	
	private JPanel mainPanel;
	private ArrayList<JTextField> referenceFields = new ArrayList<JTextField>();
	
	public ReferencePanel(Root root) {
		super(new JPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel = (JPanel)getViewport().getView();
		this.root = root;
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.DARK_GRAY);
		
		addField();
	}
	
	public void addField(String text) {
		addField();
		referenceFields.get(referenceFields.size() - 2).setText(text);
	}
	private void addField() {
		JTextField newField = new JTextField();
		GridBagConstraints newCon = new GridBagConstraints();
		newCon.gridx = 0;
		newCon.gridy = referenceFields.size();
		newCon.fill = GridBagConstraints.HORIZONTAL;
		newCon.weightx = 0.2;
		newCon.weighty = 0;
		newCon.insets = new Insets(5, 0, 5, 0);
		newField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			public void changedUpdate(DocumentEvent e) {

			}
			
			public void update(DocumentEvent e) {
				checkFields();
				root.onReferenceChange();
			}
		});
		mainPanel.add(newField, newCon);
		referenceFields.add(newField);
	}
	private void removeField(int index) {
		mainPanel.remove(referenceFields.get(index));
		referenceFields.remove(index);
	}
	public void clearFields() {
		for (JTextField f: referenceFields) {
			mainPanel.remove(f);
		}
		referenceFields = new ArrayList<JTextField>();
		addField();
	}
	
	private void checkFields() {
		ArrayList<Integer> removals = new ArrayList<Integer>();
		for (int i = 0; i < referenceFields.size(); i++) {
			if (i == referenceFields.size() - 1) {
				System.out.println("TEXT " + referenceFields.get(i).getText());
				if (!referenceFields.get(i).getText().equals("")) {
					addField();
				}
			} else {
				if (referenceFields.get(i).getText().equals("") && referenceFields.size() != 1) {
					mainPanel.remove(referenceFields.get(i));
					removals.add(i);
				}
			}
		}
		for (int i = removals.size() - 1; i >= 0; i--) {
			referenceFields.remove(removals.get(i));
		}
		mainPanel.updateUI();
	}
	
	public void setEnabled(boolean enabled) {
		for (JTextField field: referenceFields) {
			field.setEnabled(enabled);
		}
	}
	
	public void applyReferences(TermStructure struct) {
		struct.clearRefs();
		for (JTextField field: referenceFields) {
			if (!field.getText().equals("")) {
				struct.addRef(field.getText());
			}
		}
	}

}
