/**
 * @autor Niclas Burger
 * Do not copy or steal!
 */

package matrizenrechner_base;

import java.util.Scanner;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.*;

public class Matrizenrechner {
	
	private int m; // Zeilenzahl
	private int n; // Spaltenzahl
	private float[][] matrix; // zweidimensionales Array
	private JTextArea Console;
	
	public int getRows( ) {
		return this.m;
	}
	
	public int getColumns( ) {
		return this.n;
	}
	
	public float[][] getMatrix(){
		return this.matrix;
	}
	
	public void setRows( int rows ) {
		this.m = rows;
	}
	
	public void setColumns( int cols ) {
		this.n = cols;
	}
	
	public void setValue( int row, int col, float value ) {
		this.getMatrix()[row][col] = value;
	}
	
	public void addConsoleTextln( String text ) {
		this.Console.append(text);
		this.Console.append("\n");
	}
	
	public void addConsoleText( String text ) {
		this.Console.append(text);
	}
	
	public Matrizenrechner( JTextArea console, int m, int n ) {
		this.Console = console;
		this.setRows( m );
		this.setColumns( n );
	    this.matrix = new float[m][n];
	    this.initMatrix( this.m, this.n, matrix );  
	}
	
	public void startMatrixCalc() {
	    int finish = 0;  
	    int overwriteM;  
	    int overwriteN; 
	    float overwriteValue;  
	    
	    Scanner scan = new Scanner( System.in );
	    
	    addConsoleTextln("Bitte geben Sie die Zeilenzahl Ihrer Matrix an!");  
	    this.m = scan.nextInt();

	    addConsoleTextln("Bitte geben Sie die Spaltenzahl Ihrer Matrix an!");  
	    this.n = scan.nextInt(); 
	    
	    matrix = new float[m][n];
	    this.initMatrix( this.m, this.n, matrix );  

	    do {  
	    	addConsoleTextln("Möchtest du noch einen Wert angeben? 0 = nein; 0<x>0 = ja");  
	    	finish = scan.nextInt();

	        if ( finish == 0 ){  
	            break;  
	        }  

	        addConsoleTextln("Zeile:");  
	        overwriteM = scan.nextInt();

	        addConsoleTextln("Spalte:");  
	        overwriteN = scan.nextInt();

	        addConsoleTextln("Wert:");  
	        overwriteValue = scan.nextInt(); 

	        // Wir müssen noch die Zeilen- und Spaltenindizes um den Wert 1 subtrahieren,   
	        // damit wir die Indizes des Arrays erhalten  
	        matrix[overwriteM-1][overwriteN-1] = overwriteValue;  
	    } while( finish != 0 );  


	    addConsoleTextln("--------------------\nMatrix:");  
	    this.printMatrix( this.m, this.n, this.matrix );  

	    addConsoleTextln("--------------------\nNächste Aktion:");  
	    int nextAction;  


	    addConsoleTextln("Was möchtest du tun ?");  
	    addConsoleTextln("Inverse Matrix bilden: [1]");  
	    addConsoleTextln("Determinante bilden: [2]");  
	    addConsoleTextln("Matrix transponieren: [3]");  
	    nextAction = scan.nextInt(); 

	    switch( nextAction ){  
	        case 1: this.getInverse( ); break;  
	        case 2: addConsoleTextln("Die Determinante beträgt = %.2f" + this.getDeterminant( this.m, this.n, this.matrix ) ); break;  
	        case 3: {
	        	addConsoleTextln("Transponierte Matrix: \n"); this.printMatrix( this.m, this.n, this.getTransponiert( this.m, this.n, this.matrix )); 
	        	break;
	        }
	        default: break;
	    }  
	    
	    scan.close();
	}
	
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

	public void multMatrixWithFactor( int m, int n, float matrix[][], float factor ){
	    int i,j;  

	    for( i=0; i<m; i++ ){  
	        for( j=0; j<n; j++ ){  
	            matrix[i][j] = factor * matrix[i][j];  
	        }  
	    }  
	}

	public void initMatrix( int m, int n, float matrix[][] ){  

	    int i,j;  

	    for( i=0; i<m; i++ ){  
	        for( j=0; j<n; j++ ){  
	            matrix[i][j] = 0;  
	        }  
	    }  
	    
	}  


	public int isQuadratic( int m, int n ){  

	    if ( m == n ) {  
	        return 1;  
	    }  

	    return 0;  
	}  


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
