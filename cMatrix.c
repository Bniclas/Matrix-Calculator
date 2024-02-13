/*
    WRITTEN BY NICLAS BURGER DO NOT COPY OR STEAL
*/

#include <stdio.h>  

void printMatrix( int m, int n, float matrix[][n] ){  
    int i,j;  
    for( i=0; i<m; i++ ){  
        printf("[ ");  
        for( j=0; j<n; j++ ){  
            printf( "%.2f ", matrix[i][j] );  
        }  
        printf("]\n");  
    }  
}  

void multMatrixWithFactor( int m, int n, float matrix[][n], float factor ){
    int i,j;  

    for( i=0; i<m; i++ ){  
        for( j=0; j<n; j++ ){  
            matrix[i][j] = factor * matrix[i][j];  
        }  
    }  
}

void initMatrix( int m, int n, float matrix[][n], int autofill ){  

    int i,j;  

    for( i=0; i<m; i++ ){  
        for( j=0; j<n; j++ ){  
            matrix[i][j] = 0;  
        }  
    }  
    
    /*
        Nur um schneller Testmatrizen erstellen zu können und beim Testen nicht jeden Wert selbst einzutragen
    */
    
    /*
    if (autofill == 1){
        switch( n ){
            case 2: matrix[0][0] = 1; matrix[1][1] = 2; break;
            case 3: matrix[0][1] = 2; matrix[1][2] = 3 ;matrix[2][0] = 4; break;
            case 4: matrix[0][0] = 1; matrix[1][1] = 2; matrix[2][2] = 3; matrix[3][3] = 4; matrix[3][0] = 4; break;
            default: break;
        }   
    }
    */
    
}  


int isQuadratic( int m, int n ){  

    if ( m == n ) {  
        return 1;  
    }  

    return 0;  
}  


float calcDiagonals( int m, int n, int nBaseMatrix, float matrix[][n] ){
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
                printf("Ergebnis der Diagonale %i : %.2f\n", startIndexN, diagonalMult );
                */
                diagonalMult = 1;
            }
            else {
                diagonalMult *= matrix[j][startIndexN+j];
                /*
                printf("Wert: x(%i|%i) = %.2f\n", j, startIndexN+j, matrix[j][startIndexN+j]);
                */
            }
        }   
    }
    
    /*
    printf("Werte der Diagonalen von links oben nach rechts unten: %.2f\n", diagonalSum);
    printf("-----------------------------\n");
    */
    
    
    int saveSum = diagonalSum;
    
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
                printf("Ergebnis der Diagonale %i : %.2f\n", startIndexN, diagonalMult );
                */
                diagonalMult = 1;
            }
            else {
                diagonalMult *= matrix[j][startIndexN-j];
                /*
                printf("Wert: x(%i|%i) = %.2f\n", j, startIndexN-j, matrix[j][startIndexN-j]);
                */
            }
        }   
    }
    /*
    printf("Werte der Diagonalen von rechts oben nach links unten: %.2f\n", diagonalSum);
    printf("-----------------------------\n");
    */
    
    int determinante = saveSum - diagonalSum;
    
    return determinante;
}


float getDeterminant( int m, int n, float matrixData[][n] ){  
    int i, j;
    float determinante = 0;

    //  Für nicht quadratische Matrizen keine Definierte Lösung
    if ( isQuadratic( m, n ) == 0 ){  
        printf("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
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
        float newMatrix[m][n+newColumns];  

        initMatrix( m, n+newColumns, newMatrix, 0 );  

        int index = 0;  
        
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
        printf("--------------------\nDeterminantenmatrix:\n");  
        printMatrix( m, n+newColumns, newMatrix );  
        */
    
    
        determinante = calcDiagonals( m, n+newColumns, n, newMatrix);
    }  

    return determinante;  
}  


void getTransponiert( int m, int n, float matrix[][n] ){  
    float matrixT[n][m];
    initMatrix( n, m, matrixT, 0 );
    
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
    
    /*
    printf("----------------------------\n");
    printf("Transponierte Matrix: \n");
    printMatrix( n, m, matrixT );
    printf("----------------------------\n");
    */

}  

  
float getInverse( int m, int n, float matrix[][n] ){  
    if ( isQuadratic( m, n ) == 0 ){  
        printf("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
        return 0;  
    }  
    
    float detA = getDeterminant( m, n, matrix );
    
    if( detA == 0 ){
        printf("Es kann keine Inverse dieser Matrix gebildet werden, da die Determinante 0 beträgt.");
        return 0;
    }
    
    // Berechnung einer 2x2 Matrix Inversen
    if ( n == 2 ){
        float inverseMatrix[2][2];
        initMatrix( 2, 2, inverseMatrix, 0 );

        float x = 1/detA;

        inverseMatrix[0][0] = x*matrix[1][1];
        inverseMatrix[1][1] = x*matrix[0][0];
        inverseMatrix[0][1] = x*matrix[1][0];
        inverseMatrix[1][0] = x*matrix[0][1];
        
        printf("----------------------------\n");
        printf("Die Inverse ist: \n");
        printMatrix( m, n, inverseMatrix );
    }
    else {
        float adjMatrix[m][n];
        initMatrix( m, n, adjMatrix, 0 );

        // Untermatrix erhalten und Determinante berechnen
        // Wenn Spalte kleiner ist als aktuelle Spalte, dann bleibt der Spaltenindex; sonst Spaltenindex-1

        float subMatrix[m-1][n-1];
        initMatrix( m-1, n-1, subMatrix, 0 );
        int x,y;
        int row = 0;
        int col = 0;
        int counter = 0;

        // Wir loopen für jede Zeile m-mal durch die komplette Matrix
        while( counter < m * m ){
            
            //printf("Durchlauf: %i\n", counter+1);
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
                    
                    //printf("x: %i y:%i \n", x, y);
                }   
            }
            // Am Ende des Durchlaufs setzen wir die Submatrix zurück und setzten an den Spalten- sowie Zeilenindex des aktuellen Durchlaufs die Determinante der Submatrix
            adjMatrix[row][col] = getDeterminant(m-1,n-1,subMatrix);
            
            /*
            printf("Submatrix von r: %i c: %i: \n", row, col);
            printMatrix( m-1, n-1, subMatrix);
            printf("Determinante der Submatrix: %.2f \n\n", adjMatrix[row][col] );
            */
            
            initMatrix( m-1, n-1, subMatrix, 0 );
            
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
        
        printf("----------------------------\n");
        //printf("Die Determinante ist: %.2f \n", detA);
        printf("Die Inverse ist: \n");
        
        multMatrixWithFactor( m, n, adjMatrix, 1/detA);
        getTransponiert( m, n, adjMatrix);
        printMatrix( m, n, adjMatrix );

    }
    
    return 0;  
}  


int main() {  
    int m; // Zeilen   
    int n; // Spalten   
    int finish = 0;  
    int overwriteM;  
    int overwriteN; 
    float overwriteValue;  

    printf("Bitte geben Sie die Zeilenzahl Ihrer Matrix an!\n");  
    scanf("%i", &m);  

    printf("Bitte geben Sie die Spaltenzahl Ihrer Matrix an!\n");  
    scanf("%i", &n);  

    float matrix[m][n];  
    initMatrix( m, n, matrix, 1 );  

    do {  
        printf("Möchtest du noch einen Wert angeben? 0 = nein; 0<x>0 = ja \n");  
        scanf("%i", &finish);  

        if ( finish == 0 ){  
            break;  
        }  

        printf("Zeile: \n");  
        scanf("%i", &overwriteM);  

        printf("Spalte: \n");  
        scanf("%i", &overwriteN);  

        printf("Wert: \n");  
        scanf("%f", &overwriteValue);  

        // Wir müssen noch die Zeilen- und Spaltenindizes um den Wert 1 subtrahieren,   
        // damit wir die Indizes des Arrays erhalten  
        matrix[overwriteM-1][overwriteN-1] = overwriteValue;  
    } while( finish != 0 );  


    printf("--------------------\nMatrix:\n");  
    printMatrix( m, n, matrix );  

    printf("--------------------\nNächste Aktion:\n");  
    int nextAction;  


    printf("Was möchtest du tun ?\n");  
    printf("Inverse Matrix bilden: [1]\n");  
    printf("Determinante bilden: [2]\n");  
    printf("Matrix transponieren: [3]\n");  
    scanf("%i", &nextAction);  

    switch( nextAction ){  
        case 1: getInverse( m, n, matrix ); break;  
        case 2: printf("Die Determinante beträgt = %.2f", getDeterminant( m, n, matrix ) ); break;  
        case 3: getTransponiert( m, n, matrix ); printf("Transponierte Matrix: \n"); printMatrix( m, n, matrix ); break;
        default: break;
    }  

    return 0;  
}  