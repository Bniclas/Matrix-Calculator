package matrizenrechner_base;

import java.awt.event.*;
import javax.swing.*;

public class numericTextField extends JTextField {
	
    public numericTextField(int length) {
        super(length);
    }
    
    public numericTextField() {
        super();
    }

    @Override
    public void processKeyEvent(KeyEvent e) {
        if ( Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '-' ) {
            super.processKeyEvent(e);
        }
        e.consume();
        return;
    }

}
