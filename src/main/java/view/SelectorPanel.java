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
	private StructureButton leftSelected = null;
	private StructureButton rightSelected = null;
	
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
		if (root.doLeftSelection()) {
			removeButton(leftSelected);
		} else {
			removeButton(rightSelected);
		}
		selectButton(null);
	}
	
	public void selectButton(StructureButton button) {
		if (root.doLeftSelection()) {
			selectLeftButton(button);
		} else {
			selectRightButton(button);
		}
		root.onIdSelect();
	}
	public void selectLeftButton(StructureButton button) {
		if (leftSelected != button) {
			if (leftSelected == null) {
				disableButton(button);
				leftSelected = button;
				setPosition();
			} else if (button == null) {
				enableButton(leftSelected);
				leftSelected = null;
			} else if (!button.equals(leftSelected)) {
				enableButton(leftSelected);
				disableButton(button);
				leftSelected = button;
				setPosition();
			}
		}
	}
	public void selectRightButton(StructureButton button) {
		if (rightSelected != button) {
			if (rightSelected == null) {
				disableButton(button);
				rightSelected = button;
				setPosition();
			} else if (button == null) {
				enableButton(rightSelected);
				rightSelected = null;
			} else if (!button.equals(rightSelected)) {
				enableButton(rightSelected);
				disableButton(button);
				rightSelected = button;
				setPosition();
			}
		}
	}
	private void enableButton(StructureButton button) {
		button.setEnabled(true);
		button.setBackground(defaultColor);
	}
	private void disableButton(StructureButton button) {
		button.setEnabled(false);
		if (root.doLeftSelection()) {
			button.setBackground(Color.YELLOW);
		} else {
			button.setBackground(Color.BLUE);
		}
	}
	
	public StructureButton getSelected() {
		if (root.doLeftSelection()) {
			return leftSelected;
		} else {
			return rightSelected;
		}
	}
	public StructureButton getOtherSelected() {
		if (root.doLeftSelection()) {
			return rightSelected;
		} else {
			return leftSelected;
		}
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
		if (getSelected() == null) {
			if (getOtherSelected() == null) {
				getVerticalScrollBar().setValue(0);
			} else {
				if (!getOtherSelected().getBounds().intersects(mainPanel.getVisibleRect()) && selectionButtons.size() > 3) {
					getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum() *
							selectionButtons.indexOf(leftSelected) / (selectionButtons.size() - 1));
				}
			}
		} else {
			if (!getSelected().getBounds().intersects(mainPanel.getVisibleRect()) && selectionButtons.size() > 3) {
				getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum() *
						selectionButtons.indexOf(leftSelected) / (selectionButtons.size() - 1));
			}
		}
	}

}
