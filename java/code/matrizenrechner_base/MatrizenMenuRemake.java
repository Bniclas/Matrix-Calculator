package matrizenrechner_base;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

public class MatrizenMenuRemake {
	
	private static Vector<Matrix> savedMatrices = new Vector<Matrix>();
	private static numericTextField[][] MatrixData;
	private static Matrix matrix;
	
	private static JFrame menu;
	private static JMenuBar menuBar; 
	private static JMenu mainMenu; 
	private static JTextArea Console;
	private static JTextField rowInput;
	private static JTextField colInput;
	private static JLabel calcOptionsText;
	private static JButton createMatrixButton;
	private static JPanel matrixEditor;
	private static JCheckBox checkDeterminante;
	private static JCheckBox checkInverse;
	private static JCheckBox checkTransponent;
	private static JButton runButton;
	private static JButton saveButton;
	private static JButton printSavedMatrix;
	private static JButton clearSavedMatrix;
	private static JLabel chooseALabel;
	private static JLabel chooseBLabel;
	private static JComboBox<Integer> selectionA;
	private static JComboBox<Integer> selectionB;
	private static JButton addMatricesButton;
	private static JButton subMatricesButton;
	private static JButton multMatricesButton;
	
	/**
	* Used to add any information on the menu-console
	*/
	public static void addConsoleText( String text ) {
		Console.append(text);
		Console.append("\n");
	}
	
	/**
	* Returns the console of the menu
	*/
	public static JTextArea getConsole() {
		return Console;
	}
	
	/**
	* Returns the first selected matrix of the menu 
	*/
	public static Matrix getMatrixA() {
		if ( selectionA.getSelectedItem() == null ) {
			return null;
		}
		return savedMatrices.get( (int)selectionA.getSelectedItem() - 1 );
	}
	
	/**
	* Returns the second selected matrix of the menu 
	*/
	public static Matrix getMatrixB() {
		if ( selectionB.getSelectedItem() == null ) {
			return null;
		}
		return savedMatrices.get( (int)selectionB.getSelectedItem() - 1 );
	}
	
	/**
	* Returns whether both matrices are selected to do any calculations on them
	*/
	public static boolean bothMatricesValid() {
		if ( getMatrixA() == null && getMatrixB() == null ) {
			return false;
		}
		return true;
	}
	
	/**
	* Returns the matrix, that it created in the menu by the user via input
	*/
	public static Matrix getMenuMatrix() {
		if ( MatrixData == null || MatrixData[0][0] == null) {
			return null;
		}
		int rows = Integer.parseInt(rowInput.getText());
		int cols = Integer.parseInt(colInput.getText());
		  
		matrix = new Matrix( Console, rows, cols );
		  
		for( int i=0; i<rows; i++ ) {
			for( int j=0; j<cols; j++ ) {
				matrix.setValue( i, j, Float.parseFloat(MatrixData[i][j].getText()) );
			}
		}
		  
		return matrix;
	}

	/**
	* Sets the look and feel of the menu and calls the createMenu() method
	*/
	public MatrizenMenuRemake() {
	    try {
	        for ( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
	        	//System.out.println( info.getName() );
	            if ("Nimbus".equalsIgnoreCase(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } 
	    catch ( Exception e) {
	      
	    }
	    
	    createMenu();
	}
	
	/**
	* Creating the menu of the calculator
	*/
	public void createMenu() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() * 0.7;
		double height = screenSize.getHeight() * 0.7;
		
		menu = new JFrame("Matrizenrechner");
		menu.setSize( (int)width, (int)height);
		menu.setLocationRelativeTo(null);
	    GridBagLayout mainBagLayout = new GridBagLayout();
	    GridBagConstraints mainConstraint = new GridBagConstraints();
	    mainBagLayout.setConstraints(menu,mainConstraint);
	    mainConstraint.fill = GridBagConstraints.BOTH;
	    menu.setLayout(mainBagLayout);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setVisible( true );
		
		menuBar = new JMenuBar();
		menu.setJMenuBar( menuBar );
		mainMenu = new JMenu("Hauptmenü");
		menuBar.add(mainMenu);
		
		mainConstraint.gridx = 0;
		mainConstraint.gridy = 0;
		mainConstraint.weightx = 0.5;
		mainConstraint.weighty = 1;
		mainConstraint.gridwidth = 1;
		JPanel leftMainPanel = new JPanel();
	    GridBagLayout leftLayout = new GridBagLayout();
	    GridBagConstraints leftConstraint = new GridBagConstraints();
	    leftLayout.setConstraints(menu,leftConstraint);
	    leftConstraint.fill = GridBagConstraints.BOTH;
	    leftMainPanel.setLayout(leftLayout);
		menu.add( leftMainPanel, mainConstraint );
		
		mainConstraint.gridx = 1;
		mainConstraint.gridy = 0;
		JPanel rightMainPanel = new JPanel();
	    GridBagLayout rightLayout = new GridBagLayout();
	    GridBagConstraints rightConstraint = new GridBagConstraints();
	    rightLayout.setConstraints(menu,rightConstraint);
	    rightConstraint.fill = GridBagConstraints.BOTH;
	    rightMainPanel.setLayout(rightLayout);
		menu.add( rightMainPanel, mainConstraint );
		
		leftConstraint.gridwidth = 1;
		leftConstraint.gridheight = 1;
		leftConstraint.weightx = 1;
		
		JLabel rowLabel = new JLabel("Zeilen eingeben:");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 0;
		leftMainPanel.add( rowLabel, leftConstraint );
		
		rowInput = new numericTextField();
		rowInput.setText("2");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 0;
		leftMainPanel.add( rowInput, leftConstraint );
		
		JLabel colLabel = new JLabel("Spalten eingeben:");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 1;
		leftMainPanel.add( colLabel, leftConstraint );
		
		colInput = new numericTextField();
		colInput.setText("2");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 1;
		leftMainPanel.add( colInput, leftConstraint);
		
		Console = new JTextArea();
		Console.setEditable( false );
		rightConstraint.gridx = 0;
		rightConstraint.gridy = 0;
		rightConstraint.weightx = 1;
		rightConstraint.weighty = 1;
		JScrollPane rightScrollPanel = new JScrollPane(Console); 
		rightMainPanel.add( rightScrollPanel, rightConstraint );
		
		createMatrixButton = new JButton("Matrix erstellen");
		leftConstraint.gridwidth = 1;
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 2;
		createMatrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  int rows = Integer.parseInt(rowInput.getText());
				  int cols = Integer.parseInt(colInput.getText());
				  
				  MatrixData = new numericTextField[rows][cols];
				  
				  Component[] components = matrixEditor.getComponents();
				  
				  for (Component component : components) {
					  matrixEditor.remove(component);
				  }
				  
				  matrixEditor.setLayout( new GridLayout(rows, cols) );
				  
				  for( int i=0; i<rows; i++ ) {
					  for( int j=0; j<cols; j++ ) {
						  MatrixData[i][j] = new numericTextField();;
						  MatrixData[i][j].setMaximumSize( new Dimension( 5, 5 ) );
						  MatrixData[i][j].setText("0");
						  matrixEditor.add( MatrixData[i][j] );
					  }
				  }
				  
				  
				  menu.revalidate();
			  } 
		});
		leftMainPanel.add(createMatrixButton, leftConstraint);
		leftConstraint.gridwidth = 1;
		
		matrixEditor = new JPanel();
		leftConstraint.gridwidth = 1;
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 3;
		leftMainPanel.add( matrixEditor, leftConstraint);
		
		calcOptionsText = new JLabel("Was möchtest du berechnen?");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 4;
		leftMainPanel.add( calcOptionsText, leftConstraint );
		
		checkDeterminante = new JCheckBox("Determinante");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 5;
		leftMainPanel.add(checkDeterminante, leftConstraint);
		checkInverse = new JCheckBox("Inverse");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 5;
		leftMainPanel.add(checkInverse, leftConstraint);
		checkTransponent = new JCheckBox("Transponieren");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 6;
		leftMainPanel.add(checkTransponent, leftConstraint);
		
		
		runButton = new JButton("Berechnen");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 7;
		runButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  
				  if (getMenuMatrix() == null) {
					  return;
				  }
				  
				  int rows = getMenuMatrix().getRows();
				  int cols = getMenuMatrix().getColumns();
				  
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
		leftMainPanel.add(runButton, leftConstraint);
		
		saveButton = new JButton("Speichern");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 8;
		saveButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  
				  if (getMenuMatrix() == null) {
					  return;
				  }
				  
				  savedMatrices.add( matrix );
				  
				  for (int i=0; i<savedMatrices.size(); i++ ) {
					  selectionA.addItem(i+1);
					  selectionB.addItem(i+1);
				  }
			  } 
		});
		leftMainPanel.add(saveButton, leftConstraint);
		
		printSavedMatrix = new JButton("Gespeicherte Matrizen anzeigen");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 9;
		printSavedMatrix.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  addConsoleText("\nGespeicherte Matrizen:\n");
				  int c = 0;
				  for ( Matrix m : savedMatrices ) {
					  ++c;
					  addConsoleText( c + ". gespeicherte Matrix:\n");
					  m.printMatrix( m.getRows(), m.getColumns(), m.getMatrix() );
					  m.addConsoleTextln("");
				  }
			  } 
		});
		leftMainPanel.add(printSavedMatrix, leftConstraint);
		
		
		clearSavedMatrix = new JButton("Gespeicherte Matrizen löschen");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 10;
		clearSavedMatrix.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  addConsoleText("\nGespeicherte Matrizen wurden gelöscht.\n");
				  savedMatrices.clear();
				  
				  selectionA.removeAllItems();
				  selectionB.removeAllItems();
			  } 
		});
		leftMainPanel.add(clearSavedMatrix, leftConstraint);
		
		JLabel optionLabel = new JLabel();
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 11;
		optionLabel.setText("Weitere Funktionalitäten: ");
		leftMainPanel.add(optionLabel, leftConstraint);
		
		chooseALabel = new JLabel("Matrix A wählen:");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 12;
		leftMainPanel.add(chooseALabel, leftConstraint);
		
		chooseBLabel = new JLabel("Matrix B wählen:");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 12;
		leftMainPanel.add(chooseBLabel, leftConstraint);
		
		selectionA = new JComboBox<Integer>();
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 13;
		leftMainPanel.add(selectionA, leftConstraint);
		
		selectionB = new JComboBox<Integer>();
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 13;
		leftMainPanel.add(selectionB, leftConstraint);
		
		
		addMatricesButton = new JButton("Matrizen addieren");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 14;
		addMatricesButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Matrix A = getMatrixA();
				  Matrix B = getMatrixB();
				  if ( bothMatricesValid() == false ) {
					  return;
				  }
				  Matrix C = Matrix.addMatrices( Console, A, B);
				  
				  int rowsC = C.getRows();
				  int colsC = C.getColumns();
				  
				  C.printMatrix( rowsC, colsC, C.getMatrix() );
			  } 
		});
		leftMainPanel.add(addMatricesButton, leftConstraint);
		
		subMatricesButton = new JButton("Matrizen subtrahieren");
		leftConstraint.gridx = 1;
		leftConstraint.gridy = 14;
		subMatricesButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Matrix A = getMatrixA();
				  Matrix B = getMatrixB();
				  if ( bothMatricesValid() == false ) {
					  return;
				  }
				  Matrix C = Matrix.subMatrices( Console, A, B);
				  
				  int rowsC = C.getRows();
				  int colsC = C.getColumns();
				  
				  C.printMatrix( rowsC, colsC, C.getMatrix() );
			  } 
		});
		leftMainPanel.add(subMatricesButton, leftConstraint);
		
		multMatricesButton = new JButton("Matrizen multiplizieren");
		leftConstraint.gridx = 0;
		leftConstraint.gridy = 15;
		multMatricesButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Matrix A = getMatrixA();
				  Matrix B = getMatrixB();
				  if ( bothMatricesValid() == false ) {
					  return;
				  }
				  Matrix C = Matrix.multiplyMatrices( Console, A, B);
				  
				  int rowsC = C.getRows();
				  int colsC = C.getColumns();
				  
				  C.printMatrix( rowsC, colsC, C.getMatrix() );
			  } 
		});
		leftMainPanel.add(multMatricesButton, leftConstraint);
		
		
		leftMainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		//rightMainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		menu.revalidate();
	}
	
}
