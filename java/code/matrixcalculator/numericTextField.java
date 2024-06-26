/**
 * Do not copy or steal!
 */

package matrixcalculator;

import java.awt.event.*;
import javax.swing.*;

/**
* Class to make textfields that only accept numerical input
*/
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
