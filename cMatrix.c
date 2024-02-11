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


void initMatrix( int m, int n, float matrix[][n] ){  

    int i,j;  

    for( i=0; i<m; i++ ){  
        for( j=0; j<n; j++ ){  
            matrix[i][j] = 0;  
        }  
    }  
    
    /*
        Nur um schneller Testmatrizen erstellen zu können und beim Testen nicht jeden Wert selbst einzutragen
    */
    
    
    switch( n ){
        case 2: matrix[0][0] = 1; matrix[1][1] = 2; break;
        case 3: matrix[0][0] = 1; matrix[1][1] = 2; matrix[2][2] = 3; break;
        case 4: matrix[0][0] = 1; matrix[1][1] = 2; matrix[2][2] = 3; matrix[3][3] = 4; break;
        default: break;
    }
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
                printf("Ergebnis der Diagonale %.2f : %.2f\n", startIndexN, diagonalMult );
                diagonalMult = 1;
            }
            else {
                diagonalMult *= matrix[j][startIndexN+j];
                printf("Wert: x(%i|%i) = %.2f\n", j, startIndexN+j, matrix[j][startIndexN+j]);
            }
        }   
    }
    
    printf("Werte der Diagonalen von links oben nach rechts unten: %i\n", diagonalSum);
    printf("-----------------------------\n");
    
    
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
                printf("Ergebnis der Diagonale %i : %i\n", startIndexN, diagonalMult );
                diagonalMult = 1;
            }
            else {
                diagonalMult *= matrix[j][startIndexN-j];
                printf("Wert: x(%i|%i) = %i\n", j, startIndexN-j, matrix[j][startIndexN-j]);
            }
        }   
    }
    
    printf("Werte der Diagonalen von rechts oben nach links unten: %i\n", diagonalSum);
    printf("-----------------------------\n");
    
    int determinante = saveSum - diagonalSum;
    
    return determinante;
}


float getDeterminant( int m, int n, float matrix[][n] ){  
    int i, j;
    float determinante = 0;

    //  Für nicht quadratische Matrizen keine Definierte Lösung
    if ( isQuadratic( m, n ) == 0 ){  
        printf("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
        return 0;  
    }  

    // Berechnung von Determinante von 2x2 Matrix
    if ( m == 2 ){  
        determinante = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }  
    // Berechnung von größeren Matrizen
    else {  
        int newColumns = m-1;  
        float newMatrix[m][n+newColumns];  

        initMatrix( m, n+newColumns, newMatrix );  

        int index = 0;  
        
        // Wir haben in einem Array den Startindex 0 und daher können wir somit effizienter arbeiten, als jedes Mal -1 zu rechnen
        int mForArray = m-1;
        int nForArray = n-1;

        // Die alte Matrix übernehmen
        for (i=0;i<=mForArray;i++){
            for(j=0;j<=nForArray;j++){
                newMatrix[i][j] = matrix[i][j];
            }
        }

        // Die zusätzlichen Spalten erstellen
        for (j=0;j<=newColumns-1;j++){  
            for( i=0;i<=newColumns-1;i++){  
                newMatrix[i][j+n] = matrix[i][j];  
            }  
        }  

        // Die Matrix ausgeben  
        printf("--------------------\nDeterminantenmatrix:\n");  
        printMatrix( m, n+newColumns, newMatrix );  
    
    
        determinante = calcDiagonals( m, n+newColumns, n, newMatrix);
    }  

    return determinante;  
}  


float getTranspon( int m, int n, float matrix[][n] ){  
    float matrixT[n][m];
    initMatrix( n, m, matrixT );
    
    for( int j=0; j<=m-1; j++ ){
        for( int i=0; i<=n-1; i++ ){
            matrixT[i][j] = matrix[j][i];
        }
    }
    
    printf("----------------------------\n");
    printf("Transponierte Matrix: \n");
    printMatrix( n, m, matrixT );

    return 0;  
}  

  
float getInverse( int m, int n, float matrix[][n] ){  
    if ( isQuadratic( m, n ) == 0 ){  
        printf("Leider ist das keine Quadratiche Matrix : Aufgabe beendet!\n");  
        return 0;  
    }  
    
    float determinante = getDeterminant( m, n, matrix );
    
    if( determinante == 0 ){
        printf("Es kann keine Inverse dieser Matrix gebildet werden, da die Determinante 0 beträgt.");
        return 0;
    }
    
    // Berechnung einer 2x2 Matrix Inversen
    if ( n == 2 ){
        float inverseMatrix[2][2];
        initMatrix( 2, 2, inverseMatrix );

        float x = 1/determinante;

        inverseMatrix[0][0] = x*matrix[1][1];
        inverseMatrix[1][1] = x*matrix[0][0];
        inverseMatrix[0][1] = x*matrix[1][0];
        inverseMatrix[1][0] = x*matrix[0][1];
        
        printf("----------------------------\n");
        printf("Die Inverse ist: \n");
        printMatrix( m, n, inverseMatrix );
    }
    else {
        float inverseMatrix[m][n*2];
        initMatrix( m, n*2, inverseMatrix );
        
        float *ptr = &inverseMatrix[0][n-2];
        
        for( int i=0; i<=m-1; i++ ){
            for( int j=0; j<=n-1; j++ ){
                inverseMatrix[i][j] = matrix[i][j];
            }
        }
        
        for (int i=0; i<=m; i++){
            ptr = &inverseMatrix[i][n+i];
            *ptr = 1;
        }

        printf("----------------------------\n");
        printf("Die Inverse ist: \n");
        printMatrix( m, n, inverseMatrix );
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
    initMatrix( m, n, matrix );  

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
    printf("Inverse Matrix bilden (WIP): [1]\n");  
    printf("Determinante bilden: [2]\n");  
    printf("Matrix transponieren: [3]\n");  
    scanf("%i", &nextAction);  

    switch( nextAction ){  
        case 1: getInverse( m, n, matrix ); break;  
        case 2: printf("Die Determinante beträgt = %.2f", getDeterminant( m, n, matrix ) ); break;  
        case 3: getTranspon( m, n, matrix ); break;
        default: break;
    }  

    return 0;  
}  