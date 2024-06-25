/**
 * @autor Niclas Burger
 * Do not copy or steal!
 */

package matrizenrechner_base;

import javax.swing.*;

public class Matrix {
	
	private int m; // Zeilenzahl
	private int n; // Spaltenzahl
	private float[][] matrix; // zweidimensionales Array
	private JTextArea Console;
	
	/**
	* Returns matrix rows
	*/
	public int getRows( ) {
		return this.m;
	}
	
	/**
	* Returns matrix columns
	*/
	public int getColumns( ) {
		return this.n;
	}
	
	/**
	* Returns the matrix
	*/
	public float[][] getMatrix(){
		return this.matrix;
	}
	
	/**
	* Sets matrix rows
	*/
	public void setRows( int rows ) {
		this.m = rows;
	}
	
	/**
	* Sets matrix columns
	*/
	public void setColumns( int cols ) {
		this.n = cols;
	}
	
	/**
	* Sets a value on a specific field of the matrix
	*/
	public void setValue( int row, int col, float value ) {
		this.getMatrix()[row][col] = value;
	}
	
	/**
	* Gets the value of the required field inside the matrix
	*/
	public float getValue( int row, int col ) {
		return this.getMatrix()[row][col];
	}
	
	/**
	* Adds any information to the menu-console of the frame with linebreak
	*/
	public void addConsoleTextln( String text ) {
		this.Console.append(text);
		this.Console.append("\n");
	}
	
	/**
	* Adds any information to the menu-console of the frame without linebreak
	*/
	public void addConsoleText( String text ) {
		this.Console.append(text);
	}
	
	/**
	* Constructor to create a matrix
	*/
	public Matrix( JTextArea console, int m, int n ) {
		this.Console = console;
		this.setRows( m );
		this.setColumns( n );
	    this.matrix = new float[m][n];
	    this.initMatrix( this.m, this.n, matrix );  
	}
	
	/**
	* Prints the matrix
	*/
	public void printMatrix( int m, int n, float matrix[][] ){  
	    int i,j;  
	    for( i=0; i<m; i++ ){  
	        addConsoleText("[ ");  
	        for( j=0; j<n; j++ ){  
	            addConsoleText( String.format("%.02f", matrix[i][j]) );  
	            if ( j<n-1 ) {
	            	addConsoleText(" | ");
	            }
	        }  
	        addConsoleText(" ]\n");  
	    }  
	}  
	
	/**
	* Method to subtract one matrix from the other
	*/
	public static Matrix subMatrices( JTextArea Console, Matrix A, Matrix B ){
		int rowsA = A.getRows();
		int rowsB = A.getRows();
		int colsA = A.getColumns();
		int colsB = B.getColumns();
		
		int rowsC = rowsA;
		int colsC = colsB;
		
		if ( colsA != colsB && rowsA != rowsB ) {
			A.addConsoleTextln("Subtraktion kann nicht durchgeführt werden.");  
			return new Matrix( Console, 0, 0 );
		}
		
		Matrix C = new Matrix( Console, rowsC, colsC );
		
		for( int i=0; i<rowsC; i++ ) {
			for( int j=0; j<colsC; j++ ) {
				C.setValue(i, j, ( A.getMatrix()[i][j] - B.getMatrix()[i][j] ) ); 
			}
		}
		
		return C;
	}
	
	/**
	* Method to add one matrix to the other
	*/
	public static Matrix addMatrices( JTextArea Console, Matrix A, Matrix B ){
		int rowsA = A.getRows();
		int rowsB = A.getRows();
		int colsA = A.getColumns();
		int colsB = B.getColumns();
		
		int rowsC = rowsA;
		int colsC = colsB;
		
		if ( colsA != colsB && rowsA != rowsB ) {
			A.addConsoleTextln("Addition kann nicht durchgeführt werden.");  
			return new Matrix( Console, 0, 0 );
		}
		
		Matrix C = new Matrix( Console, rowsC, colsC );
		
		for( int i=0; i<rowsC; i++ ) {
			for( int j=0; j<colsC; j++ ) {
				C.setValue(i, j, ( A.getMatrix()[i][j] + B.getMatrix()[i][j] ) ); 
			}
		}
		
		return C;
	}
	
	/**
	* Method to multiply two matrices
	*/
	public static Matrix multiplyMatrices( JTextArea Console, Matrix A, Matrix B ){
		int rowsA = A.getRows();
		int rowsB = A.getRows();
		int colsA = A.getColumns();
		int colsB = B.getColumns();
		
		int rowsC = rowsA;
		int colsC = colsB;
		
		if ( colsA != rowsB ) {
			A.addConsoleTextln("Multiplikation kann nicht durchgeführt werden.");  
			return new Matrix( Console, 0, 0 );
		}
		
		
		Matrix C = new Matrix( Console, rowsC, colsC );

				
		// Matrix A loop through
		for ( int i=0; i<rowsA; i++ ) {
			for( int j=0; j<colsA; j++ ) {
				
				// Matrix B loop 
				for( int l=0; l<colsB; l++ ) {
					float valueC = A.getValue(i, j) * B.getValue(j, l);
					C.setValue( i, l, C.getValue(i,l) + valueC );
				}
				
			}
		}
				
		return C;
	}

	/**
	* Method to multiply a matrix with a single factorial number
	*/
	public void multMatrixWithFactor( int m, int n, float matrix[][], float factor ){
	    int i,j;  

	    for( i=0; i<m; i++ ){  
	        for( j=0; j<n; j++ ){  
	            matrix[i][j] = factor * matrix[i][j];  
	        }  
	    }  
	}

	/**
	* Initializing the fields of a matrix
	*/
	public void initMatrix( int m, int n, float matrix[][] ){  

	    int i,j;  

	    for( i=0; i<m; i++ ){  
	        for( j=0; j<n; j++ ){  
	            matrix[i][j] = 0;  
	        }  
	    }  
	    
	}  

	/**
	* Returns whether the given matrix is quadratic or not
	*/
	public int isQuadratic( int m, int n ){  

	    if ( m == n ) {  
	        return 1;  
	    }  

	    return 0;  
	}  

	/**
	* Calculates the diagonals inside a matrix which are needed to calculate the determinant
	*/
	public float calcDiagonals( int m, int n, int nBaseMatrix, float matrix[][] ){
	    int i,j;
	    int startIndexN = 0; // Der Start der neuen Spalte
	    
	    float diagonalMult = 1;
	    float diagonalSum = 0;
	    
	    /*
	        BERECHNUNG DER DIAGONALEN VON LINKS OBEN NACH RECHTS UNTEN
	    */
	    // Wie oft wir die Operation ausführen müssen, da wir bspw. eine 3x3 Matrix haben --> Dann 3 Mal
	    for( i=0; i<=nBaseMatrix-1; i++ ){
	        
	        // Pro Durchlauf immer alle Zeilen nutzen
	        for( j=0; j<=m; j++ ){
	            if( j == m ){
	                startIndexN += 1;
	                diagonalSum += diagonalMult;
	                /*
	                	addConsoleTextln("Ergebnis der Diagonale %i : %.2f\n", startIndexN, diagonalMult );
	                */
	                diagonalMult = 1;
	            }
	            else {
	                diagonalMult *= matrix[j][startIndexN+j];
	                /*
	                	addConsoleTextln("Wert: x(%i|%i) = %.2f\n", j, startIndexN+j, matrix[j][startIndexN+j]);
	                */
	            }
	        }   
	    }
	    
	    /*
	    	addConsoleTextln("Werte der Diagonalen von links oben nach rechts unten: %.2f\n", diagonalSum);
	    	addConsoleTextln("-----------------------------\n");
	    */
	    
	    
	    float saveSum = diagonalSum;
	    
	    /*
	        BERECHNUNG DER DIAGONALEN VON RECHTS OBEN NACH LINKS UNTEN
	    */
	    // Wie oft wir die Operation ausführen müssen, da wir bspw. eine 3x3 Matrix haben --> Dann 3 Mal
	    startIndexN = n-1;
	    
	    diagonalMult = 1;
	    diagonalSum = 0;
	    
	    for( i=0; i<=nBaseMatrix-1; i++ ){
	        
	        // Pro Durchlauf immer alle Zeilen nutzen
	        for( j=0; j<=m; j++ ){
	            if( j == m ){
	                startIndexN -= 1;
	                diagonalSum += diagonalMult;
	                /*
	                	addConsoleTextln("Ergebnis der Diagonale %i : %.2f\n", startIndexN, diagonalMult );
	                */
	                diagonalMult = 1;
	            }
	            else {
	                diagonalMult *= matrix[j][startIndexN-j];
	                /*
	                	addConsoleTextln("Wert: x(%i|%i) = %.2f\n", j, startIndexN-j, matrix[j][startIndexN-j]);
	                */
	            }
	        }   
	    }
	    /*
	    	addConsoleTextln("Werte der Diagonalen von rechts oben nach links unten: %.2f\n", diagonalSum);
	    	addConsoleTextln("-----------------------------\n");
	    */
	    
	    float determinante = saveSum - diagonalSum;
	    
	    return determinante;
	}

	/**
	* Calculates the determinant of a matrix
	*/
	public float getDeterminant( int m, int n, float matrixData[][] ){  
	    int i, j;
	    float determinante = 0;

	    //  Für nicht quadratische Matrizen keine Definierte Lösung
	    if ( this.isQuadratic( m, n ) == 0 ){  
	        addConsoleTextln("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
	        return 0;  
	    }  

	    // Berechnung von Determinante von 2x2 Matrix
	    if ( m == 2 ){  
	        determinante = matrixData[0][0] * matrixData[1][1] - matrixData[0][1] * matrixData[1][0];
	        return determinante;
	    }  
	    // Berechnung von größeren Matrizen
	    else {  
	        int newColumns = m-1;  
	        float newMatrix[][] = new float[m][n+newColumns];  

	        this.initMatrix( m, n, newMatrix );  
	        
	        // Wir haben in einem Array den Startindex 0 und daher können wir somit effizienter arbeiten, als jedes Mal -1 zu rechnen
	        int mForArray = m-1;
	        int nForArray = n-1;

	        // Die alte Matrix übernehmen
	        for (i=0;i<=mForArray;i++){
	            for(j=0;j<=nForArray;j++){
	                newMatrix[i][j] = matrixData[i][j];
	            }
	        }

	        // Die zusätzlichen Spalten erstellen
	        for (j=0;j<newColumns;j++){  
	            for( i=0;i<m;i++){  
	                newMatrix[i][j+n] = matrixData[i][j];  
	            }  
	        }  

	        /*
	        // Die Matrix ausgeben  
	        addConsoleTextln("--------------------\nDeterminantenmatrix:\n");  
	        printMatrix( m, n+newColumns, newMatrix );  
	        */
	    
	    
	        determinante = this.calcDiagonals( m, n+newColumns, n, newMatrix );
	    }  

	    return determinante;  
	}  

	/**
	* Calculates the transposed matrix
	*/
	public float[][] getTransponiert( int m, int n, float matrix[][] ){  
	    float matrixT[][] = new float[n][m];
	    this.initMatrix( n, m, matrixT );
	    
	    for( int j=0; j<m; j++ ){
	        for( int i=0; i<n; i++ ){
	            matrixT[i][j] = matrix[j][i];
	        }
	    }
	    
	    for( int j=0; j<m; j++ ){
	        for( int i=0; i<n; i++ ){
	            matrix[i][j] = matrixT[i][j];
	        }
	    }
	    
	    return matrixT;
	}  

	/**
	* Calculates the inverse of a matrix
	*/
	public float getInverse(){  
		int m = this.getRows();
		int n = this.getColumns();
		float[][] matrix = this.getMatrix();
		
		
	    if ( this.isQuadratic( m, n ) == 0 ){  
	        addConsoleTextln("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
	        return 0;  
	    }  
	    
	    float detA = this.getDeterminant( m, n, matrix );
	    
	    if( detA == 0 ){
	        addConsoleTextln("Es kann keine Inverse dieser Matrix gebildet werden, da die Determinante 0 beträgt.");
	        return 0;
	    }
	    
	    // Berechnung einer 2x2 Matrix Inversen
	    if ( n == 2 ){
	        float inverseMatrix[][] = new float[2][2];
	        this.initMatrix( m, n, inverseMatrix );

	        float x = 1/detA;

	        inverseMatrix[0][0] = x*matrix[1][1];
	        inverseMatrix[1][1] = x*matrix[0][0];
	        inverseMatrix[0][1] = x*matrix[1][0];
	        inverseMatrix[1][0] = x*matrix[0][1];
	        
	        this.printMatrix( m, n, inverseMatrix );
	    }
	    else {
	        float adjMatrix[][] = new float[m][n];
	        this.initMatrix( m, n, adjMatrix );

	        // Untermatrix erhalten und Determinante berechnen
	        // Wenn Spalte kleiner ist als aktuelle Spalte, dann bleibt der Spaltenindex; sonst Spaltenindex-1

	        float subMatrix[][] = new float[m-1][n-1];
	        this.initMatrix( m-1, n-1, subMatrix );
	        int x,y;
	        int row = 0;
	        int col = 0;
	        int counter = 0;

	        // Wir loopen für jede Zeile m-mal durch die komplette Matrix
	        while( counter < m * m ){
	            
	            //addConsoleTextln("Durchlauf: %i\n", counter+1);
	            for(x=0;x<m;x++){
	                for(y=0;y<m;y++){
	                    if(x==row || y==col){
	                        continue;
	                    }
	                    
	                    if ( y<col ) {
	                        if (x<row){
	                            subMatrix[x][y] = matrix[x][y];
	                        }
	                        else if (x>row){
	                            subMatrix[x-1][y] = matrix[x][y];
	                        }
	                    }
	                    else if ( y>col ) {
	                        if (x<row){
	                            subMatrix[x][y-1] = matrix[x][y];
	                        }
	                        else if (x>row){
	                            subMatrix[x-1][y-1] = matrix[x][y];
	                        }
	                    }
	                    
	                    //addConsoleTextln("x: %i y:%i \n", x, y);
	                }   
	            }
	            // Am Ende des Durchlaufs setzen wir die Submatrix zurück und setzten an den Spalten- sowie Zeilenindex des aktuellen Durchlaufs die Determinante der Submatrix
	            adjMatrix[row][col] = getDeterminant(m-1,n-1,subMatrix);
	            
	            /*
	            addConsoleTextln("Submatrix von r: %i c: %i: \n", row, col);
	            printMatrix( m-1, n-1, subMatrix);
	            addConsoleTextln("Determinante der Submatrix: %.2f \n\n", adjMatrix[row][col] );
	            */
	            
	            this.initMatrix( m-1, n-1, subMatrix );
	            
	            counter += 1;
	            if ( counter % m == 0 && counter != 0 ){
	                col = 0;
	                row += 1;  
	            } else {
	                col += 1;
	            }
	        }
	        
	        //printMatrix( m, n, adjMatrix);
	        


	        // Schachbrett auf Matrix anwenden: Wenn _index_actual % 2 == 0 dann *1 sonst *-1
	        for (x=0; x<n; x++){
	            for (y=0; y<n; y++){
	                /*
	                if( matrix[x][y] == 0 ){
	                    continue;
	                }
	                */
	                if ( x % 2 == 0 ){
	                    if ( y % 2 == 0 ){
	                        adjMatrix[x][y] = 1 * adjMatrix[x][y];
	                    }
	                    else {
	                        adjMatrix[x][y] = -1 * adjMatrix[x][y];
	                    }
	                }
	                else {
	                    if ( y % 2 == 0 ){
	                        adjMatrix[x][y] = -1 * adjMatrix[x][y];
	                    }
	                    else {
	                        adjMatrix[x][y] = 1 * adjMatrix[x][y];
	                    }
	                }
	            }
	        }


	        // Letzte Berechnung A^-1 = 1/det(A) * Adj(A) [transponiert]
	        
	        
	        // Ausgabe der Matrix
	        
	        this.multMatrixWithFactor( m, n, adjMatrix, 1/detA);
	        this.getTransponiert( m, n, adjMatrix );
	        this.printMatrix( m, n, adjMatrix );

	    }
	    
	    return 0;  
	}  
	
}
