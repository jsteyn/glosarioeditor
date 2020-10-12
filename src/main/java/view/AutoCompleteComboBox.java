package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Code for this class was obtained from http://esus.com/creating-auto-complete-jcombobox/
 */
public class AutoCompleteComboBox extends JComboBox
{
    public int caretPos=0;
    public JTextField inputField=null;

    public AutoCompleteComboBox(final Object elements[]) {
        super(elements);
        setEditor(new BasicComboBoxEditor());
        setEditable(true);
    }

    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);

        inputField.setText(getItemAt(index).toString());
        inputField.setSelectionEnd(caretPos + inputField.getText().length());
        inputField.moveCaretPosition(caretPos);
    }

    public void setEditor(ComboBoxEditor editor) {
        super.setEditor(editor);
        if (editor.getEditorComponent() instanceof JTextField) {
            inputField = (JTextField) editor.getEditorComponent();
            inputField.setBorder(BorderFactory.createEtchedBorder());           // Implemented this line myself to clarify the borders of the widget

            inputField.addKeyListener(new KeyAdapter() {
                public void keyReleased( KeyEvent ev ) {
                    char key=ev.getKeyChar();
                    if (! (Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;

                    caretPos=inputField.getCaretPosition();
                    String text="";
                    try {
                        text=inputField.getText(0, caretPos);
                    }
                    catch (javax.swing.text.BadLocationException e) {
                        e.printStackTrace();
                    }

                    for (int i=0; i<getItemCount(); i++) {
                        String element = (String) getItemAt(i);
                        if (element.startsWith(text)) {
                            setSelectedIndex(i);
                            return;
                        }
                    }
                }
            });
        }
    }
}