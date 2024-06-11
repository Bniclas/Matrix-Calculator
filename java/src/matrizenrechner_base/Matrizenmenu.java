package matrizenrechner_base;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Matrizenmenu {
	
	private JFrame menu;
	private JTextArea consoleArea;
	private JTextField[][] MatrixData;
	private Matrizenrechner matrix;
	private JButton runButton;
	private JPanel editorPanel;
	private JCheckBox checkDeterminante;
	private JCheckBox checkInverse;
	private JCheckBox checkTransponent;
	
	public void addConsoleText( String text ) {
		this.consoleArea.append(text);
		this.consoleArea.append("\n");
	}
	
	public Matrizenmenu() {
		/*
		 * Den Style vom LookAndFeel suchen und wenn Nimbus gefunden setzen
		*/
	    try {
	        for ( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } 
	    catch ( Exception e) {
	      
	    }
	    
	    /*
	     * Abfragen der Bildschirmauflösung
	    */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() * 0.5;
		double height = screenSize.getHeight() * 0.5;
		
		/*
		 * Erstellen des Hauptmenüs
		*/
		menu = new JFrame("Matrizenrechner");
		menu.setSize( (int)width, (int)height);
		menu.setLocationRelativeTo(null);
		menu.setLayout( new BorderLayout() );
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/*
		 * Erstellen des linken Panels
		*/
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS ) );
		leftPanel.setBackground(Color.gray);
		menu.add(leftPanel, BorderLayout.WEST);
		
		/*
		 * Eingabe der Zeilenzahl
		*/
		JLabel rowInputLabel = new JLabel();
		rowInputLabel.setText("Zeilenzahl:");
		leftPanel.add(rowInputLabel);
		
		JTextField rowInput = new JTextField();
		rowInput.setMaximumSize( new Dimension( 25, 25 ) );
		rowInput.setText("2");
		leftPanel.add(rowInput);
		
		/*
		 * Eingabe der Spaltenzahl
		*/
		JLabel colInputLabel = new JLabel();
		colInputLabel.setText("Spaltenzahl:");
		leftPanel.add(colInputLabel);
		
		JTextField colInput = new JTextField();
		colInput.setMaximumSize( new Dimension( 25, 25 ) );
		colInput.setText("2");
		leftPanel.add(colInput);
		
		/*
		 * Ausführen Button 
		*/
		JButton createButton = new JButton("Matrix erstellen");
		createButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  int rows = Integer.parseInt(rowInput.getText());
				  int cols = Integer.parseInt(colInput.getText());
				  matrix = new Matrizenrechner( consoleArea, rows, cols );
				  
				  MatrixData = new JTextField[rows][cols];
				  
				  consoleArea.append("\n----------------------------\n");
				  matrix.printMatrix( matrix.getRows(), matrix.getColumns(), matrix.getMatrix());
				  consoleArea.append("Du kannst die Matrix nun weiter anpassen.\n");
				  
				  Component[] components = editorPanel.getComponents();
				  
				  for (Component component : components) {
					  if (component.getClass().equals(JTextField.class)) {
						  editorPanel.remove(component);
					  }
				  }
				  
				  editorPanel.setLayout( new GridLayout(rows, cols) );
				  
				  for( int i=0; i<rows; i++ ) {
					  for( int j=0; j<cols; j++ ) {
						  MatrixData[i][j] = new JTextField();
						  MatrixData[i][j].setMaximumSize( new Dimension( 25, 25 ) );
						  MatrixData[i][j].setText("0");
						  editorPanel.add( MatrixData[i][j] );
					  }
				  }
				  
			      checkDeterminante.setVisible( true );
			      checkInverse.setVisible( true );
				  checkTransponent.setVisible( true );
				  runButton.setVisible( true );
				  menu.revalidate();
			  } 
		});
		leftPanel.add(createButton);
		
		editorPanel = new JPanel();
		editorPanel.setSize( leftPanel.getWidth(), leftPanel.getWidth() );
		leftPanel.add(editorPanel);
		
		checkDeterminante = new JCheckBox("Determinante");
		checkInverse = new JCheckBox("Inverse");
		checkTransponent = new JCheckBox("Transponieren");
		
		checkDeterminante.hide();
		checkInverse.hide();
		checkTransponent.hide();
		
		leftPanel.add(checkDeterminante);
		leftPanel.add(checkInverse);
		leftPanel.add(checkTransponent);
		
		runButton = new JButton("Berechnen");
		runButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  int rows = matrix.getRows();
				  int cols = matrix.getColumns();
				  
				  for( int i=0; i<rows; i++ ) {
					  for( int j=0; j<cols; j++ ) {
						  matrix.setValue( i, j, Float.parseFloat(MatrixData[i][j].getText()) );
					  }
				  }
				  
				  matrix.addConsoleTextln("<<<<<< BERECHNUNGEN >>>>>>");
				  
				  if ( checkDeterminante.isSelected() ) {
					  matrix.addConsoleText("Die Determinante beträgt: ");
					  matrix.addConsoleTextln( String.valueOf( matrix.getDeterminant( rows, cols, matrix.getMatrix() ) ) );
					  matrix.addConsoleTextln("");
				  }
				  if ( checkInverse.isSelected() ) {
					  matrix.addConsoleTextln("Die inverse Matrix lautet: ");
					  matrix.getInverse();
					  matrix.addConsoleTextln("");
				  }
				  if ( checkTransponent.isSelected() ) {
					  matrix.addConsoleTextln("Die transponierte Matrix lautet: ");
					  matrix.printMatrix( rows, cols, matrix.getTransponiert( rows, cols, matrix.getMatrix() ) );
				  }
			  } 
		});
		runButton.hide();
		leftPanel.add(runButton);
		
		// Rechter Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout( new FlowLayout() );
		rightPanel.setBackground(Color.DARK_GRAY);

		
		consoleArea = new JTextArea(25, 25);
		consoleArea.setLineWrap(true);
		consoleArea.setEditable(false);
		JScrollPane rightScrollPanel = new JScrollPane(consoleArea); 
		
		rightPanel.add( rightScrollPanel );
		menu.add(rightPanel, BorderLayout.CENTER);
		
		/*
		 * Menü sichtbar machen
		*/

		menu.setVisible(true);
		
		/*
		 * 
		 * 
		 */
		this.addConsoleText( "Lass uns loslegen!" );
		
	}
}
