package tubes.matrix.src;

import java.io.IOException;
import java.util.Scanner;

public class SPLMatrix extends Matrix {

    public static SPLMatrix augmentABMatrix(Matrix A, Matrix B){
        SPLMatrix res = new SPLMatrix();
        res.initializeMatrix(A.rowCount, A.columnCount + 1);
        for(int i = 0; i < A.rowCount; i++){
            res.contents[i][A.columnCount] = B.contents[i][0];
            for(int j = 0; j < A.columnCount; j++){
                res.contents[i][j] = A.contents[i][j];
            }
        }
        return res;
    }

    public void keyboardInputMatrix(Scanner sc) 
    // Procedure to assign matrix value from keyboard inputs
    {
        System.out.print("Matrix Row : ");
        if (sc.hasNextInt()){rowCount = sc.nextInt();}

        System.out.print("Matrix Column : ");
        if (sc.hasNextInt()) {columnCount = sc.nextInt();}

        contents = new double[rowCount][columnCount];

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if (j != columnCount - 1){
                    System.out.print(String.format("Enter a[%d][%d] : ", i + 1, j + 1));
                    contents[i][j] = sc.nextDouble();
                }
                else{
                    System.out.print(String.format("Enter b[%d] : ", i + 1));
                    contents[i][j] = sc.nextDouble();
                }
            }
        }
    }

    void multiplyERO(int rowNum, double factor){
    // Apply Elementary Row Operation of multiplying all the elements of rowNum by a factor
        for (int j = 0; j < columnCount; j++){
            contents[rowNum][j] *= factor;
        }
    }

    void switchERO(int row1, int row2){
    // Apply Elementary Row Operation of switching all the elements of row1 and row2
    // belonging to the same column
        for(int j = 0; j < columnCount; j++){
            switchEl(row1, row2, j, j);
        }
    }

    void addERO(int row1, int row2, double factor){
    // Apply Elementary Row Operation of performing addition on all elements of row1
    // with the element on the same column in row2 multiplied by a factor
        for(int j = 0; j < columnCount; j++){
            contents[row1][j] += factor*contents[row2][j];
        }
    }

    public void reduceToEchelon(){
    // Reduce Self to row echelon form matrix
        int i = 0;
        int j = 0;
        while(i < rowCount){
            while (isZeroColumn(j, i) && j < columnCount) {
                j++;
                if (j == columnCount) {break;}
            }
            if (j == columnCount) {break;}
            if (contents[i][j] == 0){ switchERO(i, nonZeroEl(j, i)); } // If top left el = 0, switch row
            multiplyERO(i, 1/(contents[i][j])); // Multiply the top row, such that the top left el = 1
            zeroUnder(j, i); // Zero out everything under it
            clearNegativeZero();
            i++;
        }
        clearNegativeZero();
    }

    public void echelonToReducedEchelon(){
    // reduce self from row echelon matrix to a reduced row echelon matrix
        for(int i = rowCount - 1; i > 0; i--){
            while (isZeroRow(i) && i > 0) {i--;}
            if (i == 0 || i == -1) {break;}
            zeroUp(leadingOneColumn(i), i);
        }
        clearNegativeZero();
    }

    public SolutionType[] AugmentedSols() {

        clearNegativeZero();
        char[] params = "pqrstuvwyzabcdefghijklmno".toCharArray();
        int charIdx = 0;
        SolutionType[] res = new SolutionType[columnCount];
        initializeSolArray(res);

        int colIdx = columnCount - 2;
        for(int i = rowCount - 1; i >= 0; i--){

            if (isZeroRow(i)){
                continue;
            }

            int leadingOneIdx = leadingOneColumn(i);
            while (colIdx != leadingOneIdx){
                res[colIdx].getParametricParts().put(params[charIdx], (double)1);
                colIdx--;
                charIdx++;
            }

            for(int j = leadingOneIdx + 1; j < columnCount; j++){
                if (j != columnCount - 1){
                    res[colIdx] = SolutionType.add(res[colIdx], res[j], -contents[i][j]);
                }
                else{
                    res[colIdx].setRealPart(res[colIdx].getRealPart() + contents[i][j]);
                }
            }
            colIdx--;
        }
        return res;
    }
    
    public boolean hasNoSols(){
        boolean res = false;
        for(int i = 0; i < rowCount; i++){
            boolean zeroCoefficients = true;
            for(int j = 0; j < columnCount - 1; j++){
                if (contents[i][j] != 0) {zeroCoefficients = false;}
            }
            if (zeroCoefficients && contents[i][columnCount - 1] != 0){
                res = true;
                return res;
            }
        }
        return res;
    }

    public String getAugmentedSols() {
        if (hasNoSols()) {return "The System of Linear Equations has no solutions";}
        SolutionType[] sols = AugmentedSols();
        String res = "";
        for(int i = 0; i < columnCount - 1; i++){
            res += String.format("x%d = ", i + 1) + sols[i].toString() + "\n";
        }
        return res;
    }

    public SolutionType[] solveFromScratch() {
        reduceToEchelon();
        echelonToReducedEchelon();
        return AugmentedSols();
    }

    public String GaussSolve() {
        reduceToEchelon();
        return getAugmentedSols();
    }

    public String GaussJordanSolve(){
        reduceToEchelon();
        echelonToReducedEchelon();
        return getAugmentedSols();
    }

    public String inverseMatrixSolve(){
        SquareMatrix A = getMatrixA();
        Matrix B = getMatrixB();
        return A.getSolsByInverse(B);
    }

    public String cramerSolve(){
        SquareMatrix A = getMatrixA();
        Matrix B = getMatrixB();
        return A.getSolsByCramer(B);
    }
    
    public SquareMatrix getMatrixA(){
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount, columnCount - 1);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount - 1; j++){
                res.contents[i][j] = contents[i][j];
            }
        }
        return res;
    }

    public Matrix getMatrixB(){
        Matrix res = new Matrix();
        res.initializeMatrix(rowCount, 1);
        for(int i = 0; i < rowCount; i++){
            res.contents[i][0] = contents[i][columnCount - 1];
        }
        return res;
    }

    protected void initializeSolArray(SolutionType[] res){
        for(int i = 0; i < columnCount - 1; i++){
            res[i] = new SolutionType(columnCount - 1);
        }
    }

    protected void switchEl(int row1, int row2, int column1, int column2){
        // Switch element at [row1][column1] and element at [row2][column2]
        double temp = contents[row1][column1];
        contents[row1][column1] = contents[row2][column2];
        contents[row2][column2] = temp;
    }

    protected int nonZeroEl(int columnNum, int topRow){
        // Find a row under topRow such that the element at columnNum column is non=zero
        for(int i = topRow; i < rowCount; i++){
            if (contents[i][columnNum] != 0) {return i;}
        }
        return topRow;
    }

    protected void zeroUnder(int columnNum, int topRow){
        // Uses Elementary Row Operation to zero out every element under topRow on a given column
        for(int i = topRow + 1; i < rowCount; i++){
                double factor = -(contents[i][columnNum]);
                addERO(i, topRow, factor);
        }
    }

    protected int leadingOneColumn(int rowNum){
    // return column number for a leading one in a given row
        double epsilon = 0.0001;
        for(int j = 0; j < columnCount; j++){
            if (contents[rowNum][j] >= (double)1 - epsilon && contents[rowNum][j] <= (double)1 + epsilon) {return j;}
        }
        return -1;
    }

    protected void zeroUp(int columnNum, int pivotRow){
    // use ERO to make every element above pivotRow in a given column zero
        for(int i = pivotRow - 1; i >= 0; i--){
            double factor = -(contents[i][columnNum]);
            addERO(i, pivotRow, factor);
        }
    }
    
    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        SPLMatrix M = new SPLMatrix();
        // M.keyboardInputMatrix(sc);
        // M.txtInputMatrix("./tubes/matrix/src/test.txt");
        // System.out.println();
        // M.printMatrix();
        M.reduceToEchelon();
        M.echelonToReducedEchelon();
        // System.out.println();
        // M.printMatrix();
        // M.echelonToReducedEchelon();
        // System.out.println();
        // M.printMatrix();
        System.out.println();
        // M.printAugmentedSols();
        // sc.close();
    }
    
}
