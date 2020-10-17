package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Root;
import model.TermStructure;

public class SelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 3L;
	
	private Root root;
	
	private JPanel mainPanel;
	private ArrayList<StructureButton> selectionButtons = new ArrayList<StructureButton>();
	private StructureButton selected = null;
	
	private Color defaultColor;
	
	public SelectorPanel(Root root) {
		super(new JPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel = (JPanel)getViewport().getView();
		this.root = root;
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.GRAY);
		
		defaultColor = new JButton().getBackground();
		
		TermStructure testStruct = new TermStructure();
		testStruct.setId("TEST!");
		testStruct.addDefinition("en", "definition");
		addButton(testStruct);
		TermStructure testStruct2 = new TermStructure();
		testStruct2.setId("TEST\"");
		addButton(testStruct2);
	}
	
	public void addButton(TermStructure newStructure) {
		StructureButton newButton = new StructureButton(this, newStructure);
		GridBagConstraints newCon = new GridBagConstraints();
		newCon.gridx = 0;
		newCon.gridy = selectionButtons.size();
		newCon.fill = GridBagConstraints.HORIZONTAL;
		newCon.weightx = 0.2;
		newCon.weighty = 0;
		mainPanel.add(newButton, newCon);
		newButton.setMargin(new Insets(0, -30, 0, -30));
		selectionButtons.add(newButton);
	}
	
	public void clearButtons() {
		selectButton(null);
		for (StructureButton button: selectionButtons) {
			mainPanel.remove(button);
		}
		selectionButtons = new ArrayList<StructureButton>();
	}
	
	public void removeButton(StructureButton button) {
		mainPanel.remove(button);
		selectionButtons.remove(button);
	}
	
	public void selectButton(StructureButton button) {
		if (selected != button) {
			if (selected == null) {
				disableButton(button);
				selected = button;
			} else if (button == null) {
				enableButton(selected);
				selected = null;
			} else if (!button.equals(selected)) {
				enableButton(selected);
				disableButton(button);
				selected = button;
			}
		}
		root.onIdSelect();
	}
	private void enableButton(StructureButton button) {
		button.setEnabled(true);
		button.setBackground(defaultColor);
	}
	private void disableButton(StructureButton button) {
		button.setEnabled(false);
		button.setBackground(Color.YELLOW);
	}
	
	public StructureButton getSelected() {
		return selected;
	}
	
	public int getCount() {
		return selectionButtons.size();
	}

}
