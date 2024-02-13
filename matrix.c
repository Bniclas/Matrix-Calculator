/*
    WRITTEN BY NICLAS BURGER DO NOT COPY OR STEAL
*/

void printMatrix( int m, int n, float matrix[][n] );

void multMatrixWithFactor( int m, int n, float matrix[][n], float factor );

void initMatrix( int m, int n, float matrix[][n], int autofill );

int isQuadratic( int m, int n );

float calcDiagonals( int m, int n, int nBaseMatrix, float matrix[][n] );

float getDeterminant( int m, int n, float matrixData[][n] );

void getTransponiert( int m, int n, float matrix[][n] );

float getInverse( int m, int n, float matrix[][n] );

void startMatrixCalc();