package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.Root;
import model.TermStructure;

public class StructureButton extends JButton {
	
	private SelectorPanel parent;
	private TermStructure struct;
	
	public StructureButton(SelectorPanel parent, TermStructure struct) {
		super(struct.getName());
		this.parent = parent;
		this.struct = struct;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClick();
			}
		});
	}
	
	public TermStructure getStruct() {
		return struct;
	}
	
	private void onClick() {
		parent.selectButton(this);
	}

}
