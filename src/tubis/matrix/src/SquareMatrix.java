package tubes.matrix.src;

import java.io.IOException;
// import java.util.Scanner;

public class SquareMatrix extends SPLMatrix {

    public SPLMatrix matrixI = getIdentityMatrix();
    double determinant = 1.00;
    public boolean isInvertible = true;
    
    // added this
	public static boolean isInvertible (SquareMatrix M) {
		return (M.determinantByCofactor() == 0);
	}

    void multiplyERO(int rowNum, double factor){
    	
        // Apply Elementary Row Operation of multiplying all the elements of rowNum by a factor
        for (int j = 0; j < columnCount; j++){
        	
            contents[rowNum][j] *= factor;
            matrixI.contents[rowNum][j] *= factor;
        }
        determinant /= factor;
    }
    
    void switchERO(int row1, int row2){
    // Apply Elementary Row Operation of switching all the elements of row1 and row2
    // belonging to the same column
        for(int j = 0; j < columnCount; j++){
            switchEl(row1, row2, j, j);
            matrixI.switchEl(row1, row2, j, j);
        }
        determinant*=(-1);
    }

    void addERO(int row1, int row2, double factor){
    // Apply Elementary Row Operation of performing addition on all elements of row1
    // with the element on the same column in row2 multiplied by a factor
        for(int j = 0; j < columnCount; j++){
            contents[row1][j] += factor*contents[row2][j];
            matrixI.contents[row1][j] += factor*matrixI.contents[row2][j];
        }
    }

    public SquareMatrix inverseByERO(){

        double[][] oldContents = contentsClone();
        matrixI = getIdentityMatrix();

        reduceToEchelon();
        echelonToReducedEchelon();
        contents = oldContents;

        SquareMatrix res = new SquareMatrix();
        res.contents = matrixI.contents;
        res.rowCount = rowCount;
        res.columnCount = columnCount;

        return res;
    }

    public Matrix solsByInverse(Matrix B){
        Matrix x = multiplyMatrix(inverseByERO(), B);
        return x;
    }

    public void printSolsByInverse(Matrix B){
        Matrix x = solsByInverse(B);
        for(int i = 0; i < rowCount; i++){
            System.out.println(String.format("x%d = %.2f", i + 1, x.contents[i][0]));
        }
    }

    public double determinantByERO(){
    	matrixI = getIdentityMatrix();
        determinant = 1.00;
        double[][] oldContents = contentsClone();

        reduceToEchelon();
        echelonToReducedEchelon();
        if (hasZeroRow()) {return 0.00;}
        printMatrix();
        contents = oldContents;

        return determinant;
    }

    public double determinantByCofactor(){
        if (rowCount == 1) {return contents[0][0];}
        double res = 0.00;
        for(int i = 0; i < rowCount; i++){
            res += (contents[0][i])*(subMatrix(0, i).determinantByCofactor())*(-2*(i % 2) + 1);
        }
        return res;
    }

    public SquareMatrix inverseByCofactor(){
        SquareMatrix res = new SquareMatrix();
        res = adjointCofactor();
        res.scalarMultiply(1/determinantByCofactor());
        return res;
    }

    public Matrix solsByCramer(Matrix B){
        Matrix res = new Matrix();
        determinant = determinantByCofactor();
        res.initializeMatrix(rowCount, 1);
        for(int j = 0; j < columnCount; j++){
            SquareMatrix temp = clone();
            for(int i = 0; i < rowCount; i++){
                temp.contents[i][j] = B.contents[i][0];
            }
            res.contents[j][0] = (temp.determinantByCofactor())/determinant;
        }
        return res;
    }

    public void printSolsByCramer(Matrix B){
        Matrix x = solsByCramer(B);
        for(int i = 0; i < rowCount; i++){
            System.out.println(String.format("x%d = %.2f", i + 1, x.contents[i][0]));
        }
    }

    public SquareMatrix transposeOf(){
        // return transposed matrix
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(columnCount, rowCount);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                res.contents[j][i] = contents[i][j];
            }
        }
        return res;
    }

    protected SPLMatrix getIdentityMatrix(){
        SPLMatrix res = new SPLMatrix();
        res.initializeMatrix(rowCount, columnCount);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < rowCount; j++){
                res.contents[i][j] = (i == j) ? 1 : 0;
            }
        }
        return res;
    }

    protected SquareMatrix subMatrix(int rowNum, int colNum){
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount - 1, columnCount - 1);
        for(int i = 0; i < rowCount; i++){
            if (i == rowNum){continue;}
            for(int j = 0; j < columnCount; j++){
                if (j == colNum) {continue;}
                res.contents[i - ((i > rowNum) ? 1 : 0)][j - ((j > colNum) ? 1 : 0)] = contents[i][j];
            }
        }
        return res;
    }

    protected SquareMatrix adjointCofactor(){
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount, columnCount);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++) {
            	if (rowCount == 1 && columnCount == 1) {
            		res.contents[i][j] = 1;
            	}
            	else {
            		res.contents[i][j] = (-2*((i + j) % 2) + 1)*(subMatrix(i, j).determinantByCofactor());	
            	}
            }
        }
        res.transpose();
        return res;
    }

    protected SquareMatrix clone(){
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount, columnCount);
        res.contents = contentsClone();
        return res;
    }
    
    public String getSolsByInverse(Matrix B){
        if (hasZeroRow()) {return "The Matrix Is Not Invertible";}
        Matrix x = solsByInverse(B);
        String res = "";
        for(int i = 0; i < rowCount; i++){
            res += String.format("x%d = %.2f", i + 1, x.contents[i][0]) + "\n";
        }
        return res;
    }
    
    protected boolean hasZeroRow(){
        boolean res = false;
        for (int i = 0; i < rowCount; i++){
            if (isZeroRow(i)) {res = true;}
        }
        return res;
    }
    
    public void checkInvertibility(){
        double[][] oldContents = contentsClone();

        reduceToEchelon();
        echelonToReducedEchelon();
        if (hasZeroRow()) {isInvertible = false;}
        contents = oldContents;
    }
    
    public String getSolsByCramer(Matrix B){
        Matrix x = solsByCramer(B);
        String res = "";
        for(int i = 0; i < rowCount; i++){
            res += String.format("x%d = %.2f", i + 1, x.contents[i][0]) + "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        SquareMatrix M = new SquareMatrix();
        //M.txtInputMatrix("./tubes/matrix/src/test.txt");

        SquareMatrix B = new SquareMatrix();
        //B.txtInputMatrix("./tubes/matrix/src/testb.txt");

        M.printSolsByCramer(B);
        // System.out.println();
        // M.printMatrix();
        // M.reduceToEchelon();
        // System.out.println();
        // M.printMatrix();
        // M.echelonToReducedEchelon();
        // System.out.println();
        // SquareMatrix MInverse = M.inverseByERO();
        // SquareMatrix MInverse2 = M.inverseByCofactor();
        // MInverse.printMatrix();
        // System.out.println();
        // M.printMatrix();
        // System.out.println();
        // MInverse2.printMatrix();
        // System.out.println();
        // System.out.println(String.format("Determinan Matriks : %.2f", M.determinantByCofactor()));
        // System.out.println();
        // sc.close();
    }

}
