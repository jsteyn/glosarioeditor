package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	private StructureButton selectedStructure = null;
	
	private Color defaultColor;
	
	public SelectorPanel(Root root) {
		super(new JPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel = (JPanel)getViewport().getView();
		this.root = root;
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.GRAY);
		
		defaultColor = new JButton().getBackground();
	}
	
	public StructureButton addButton(TermStructure newStructure) {
		StructureButton newButton = new StructureButton(this, newStructure);
		GridBagConstraints newCon = new GridBagConstraints();
		newCon.gridx = 0;
		newCon.gridy = selectionButtons.size();
		newCon.fill = GridBagConstraints.HORIZONTAL;
		newCon.weightx = 0.2;
		newCon.weighty = 0.01;
		mainPanel.add(newButton, newCon);
		newButton.setMargin(new Insets(0, -30, 0, -30));
		newButton.setPreferredSize(new Dimension(0, 30));
		selectionButtons.add(newButton);
		return newButton;
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
	
	public void removeSelected() {
		removeButton(selectedStructure);
		selectButton(null);
	}
	
	public void selectButton(StructureButton button) {
		if (selectedStructure != button) {
			if (selectedStructure == null) {
				disableButton(button);
				selectedStructure = button;
				setPosition();
			} else if (button == null) {
				enableButton(selectedStructure);
				selectedStructure = null;
			} else if (!button.equals(selectedStructure)) {
				enableButton(selectedStructure);
				disableButton(button);
				selectedStructure = button;
				setPosition();
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
		return selectedStructure;
	}
	
	public TermStructure getSelectedStructure() {
		if (getSelected() == null) {
			return null;
		}
		return getSelected().getStruct();
	}
	
	public int getCount() {
		return selectionButtons.size();
	}
	
	public ArrayList<TermStructure> getStructures() {
		ArrayList<TermStructure> structs = new ArrayList<TermStructure>();
		for (StructureButton b: selectionButtons) {
			structs.add(b.getStruct());
		}
		return structs;
	}
	
	public void setPosition() {
		if (selectedStructure == null) {
			getVerticalScrollBar().setValue(0);
		} else if (!selectedStructure.getBounds().intersects(mainPanel.getVisibleRect()) && selectionButtons.size() > 3) {
			getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum() *
					selectionButtons.indexOf(selectedStructure) / (selectionButtons.size() - 1));
		}
	}

}
