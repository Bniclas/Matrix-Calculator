package matrizenrechner_base;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Matrizenmenu {
	
	private Vector<Matrizenrechner> savedMatrix = new Vector<Matrizenrechner>();
	
	private JFrame menu;
	private JMenuBar menuBar; 
	private JMenu mainMenu; 
	private JMenu advancedMenu; 
	private JTextArea consoleArea;
	private numericTextField[][] MatrixData;
	private Matrizenrechner matrix;
	private JButton runButton;
	private JButton saveButton;
	private JButton printSavedMatrix;
	private JButton clearSavedMatrix;
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
		double width = screenSize.getWidth() * 0.7;
		double height = screenSize.getHeight() * 0.7;
		
		/*
		 * Erstellen des Hauptmenüs
		*/
		menu = new JFrame("Matrizenrechner");
		menu.setSize( (int)width, (int)height);
		menu.setLocationRelativeTo(null);
		menu.setLayout( new BorderLayout() );
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menu.setJMenuBar( menuBar );
		
		JMenu mainMenu = new JMenu("Hauptmenü");
		menuBar.add(mainMenu);
		
		
		/*
		 * Erstellen des linken Panels
		*/
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS ) );
		menu.add(leftPanel, BorderLayout.WEST);
		
		/*
		 * Eingabe der Zeilenzahl
		*/
		JLabel rowInputLabel = new JLabel();
		rowInputLabel.setText("Zeilenzahl:");
		leftPanel.add(rowInputLabel);
		
		JTextField rowInput = new numericTextField();
		rowInput.setMaximumSize( new Dimension( 300, 25 ) );
		rowInput.setText("2");
		leftPanel.add(rowInput);
		
		/*
		 * Eingabe der Spaltenzahl
		*/
		JLabel colInputLabel = new JLabel();
		colInputLabel.setText("Spaltenzahl:");
		leftPanel.add(colInputLabel);
		
		JTextField colInput = new numericTextField();
		colInput.setMaximumSize( new Dimension( 300, 25 ) );
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
				  
				  MatrixData = new numericTextField[rows][cols];
				  
				  consoleArea.append("\n----------------------------\n");
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
						  MatrixData[i][j] = new numericTextField();;
						  MatrixData[i][j].setMaximumSize( new Dimension( 5, 5 ) );
						  MatrixData[i][j].setText("0");
						  editorPanel.add( MatrixData[i][j] );
					  }
				  }
				  
			      checkDeterminante.setVisible( true );
			      checkInverse.setVisible( true );
				  checkTransponent.setVisible( true );
				  runButton.setVisible( true );
				  saveButton.setVisible( true );
				  printSavedMatrix.setVisible( true );
				  clearSavedMatrix.setVisible( true );
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
		
		checkDeterminante.setVisible( false );
		checkInverse.setVisible( false );
		checkTransponent.setVisible( false );
		
		leftPanel.add(checkDeterminante);
		leftPanel.add(checkInverse);
		leftPanel.add(checkTransponent);
		
		runButton = new JButton("Berechnen");
		runButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  int rows = Integer.parseInt(rowInput.getText());
				  int cols = Integer.parseInt(colInput.getText());
				  
				  matrix = new Matrizenrechner( consoleArea, rows, cols );
				  
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
		runButton.setVisible( false );
		leftPanel.add(runButton);
		
		JLabel optionLabel = new JLabel();
		optionLabel.setText("Weitere Funktionalitäten: ");
	
		leftPanel.add(optionLabel);
		
		saveButton = new JButton("Speichern");
		saveButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  savedMatrix.add( matrix );
			  } 
		});
		saveButton.setVisible( false );
		leftPanel.add(saveButton);
		
		printSavedMatrix = new JButton("Gespeicherte Matrizen anzeigen");
		printSavedMatrix.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  matrix.addConsoleTextln("\nGespeicherte Matrizen:\n");
				  int c = 0;
				  for ( Matrizenrechner m : savedMatrix ) {
					  ++c;
					  m.addConsoleTextln( c + ". gespeicherte Matrix:");
					  m.printMatrix( m.getRows(), m.getColumns(), m.getMatrix() );
					  m.addConsoleTextln("");
				  }
			  } 
		});
		printSavedMatrix.setVisible( false );
		leftPanel.add(printSavedMatrix);
		
		
		clearSavedMatrix = new JButton("Gespeicherte Matrizen anzeigen");
		clearSavedMatrix.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  matrix.addConsoleTextln("\nGespeicherte Matrizen wurden gelöscht.\n");
				  savedMatrix.clear();
			  } 
		});
		clearSavedMatrix.setVisible( false );
		leftPanel.add(clearSavedMatrix);


		// Rechter Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout( new BorderLayout() );
		
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
