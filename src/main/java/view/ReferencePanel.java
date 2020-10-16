package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.Root;

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
	
	private void addField() {
		JTextField newField = new JTextField();
		GridBagConstraints newCon = new GridBagConstraints();
		newCon.gridx = 0;
		newCon.gridy = referenceFields.size();
		newCon.fill = GridBagConstraints.HORIZONTAL;
		newCon.weightx = 0.2;
		newCon.weighty = 0;
		newCon.insets = new Insets(5, 0, 5, 0);
		mainPanel.add(newField, newCon);
		referenceFields.add(newField);
	}
	private void removeField(int index) {
		mainPanel.remove(referenceFields.get(index));
		referenceFields.remove(index);
	}
	
	public void setEnabled(boolean enabled) {
		for (JTextField field: referenceFields) {
			field.setEnabled(enabled);
		}
	}

}
