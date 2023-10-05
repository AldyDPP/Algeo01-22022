package tubes.matrix;

import java.io.IOException;
// import java.util.Scanner;

public class SquareMatrix extends SPLMatrix {

    SPLMatrix matrixI = getIdentityMatrix();
    double determinant = 1.00;
    public boolean isInvertible = true;

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
    // give Inverse Matrix obtained using elementary row operations manipulation

        double[][] oldContents = contentsClone();
        matrixI = getIdentityMatrix();

        reduceToEchelon();
        echelonToReducedEchelon();
        if (hasZeroRow()) {isInvertible = false;}
        contents = oldContents;

        SquareMatrix res = new SquareMatrix();
        res.contents = matrixI.contents;
        res.rowCount = rowCount;
        res.columnCount = columnCount;

        return res;
    }

    public Matrix solsByInverse(Matrix B){
    // get Matrix X solution of the equation
    // (self)X = B
        Matrix x = multiplyMatrix(inverseByERO(), B);
        return x;
    }

    public String getSolsByInverse(Matrix B){
    // get solution of the equation (self)X = B
    // in String form
        if (hasZeroRow()) {return "The Matrix Is Not Invertible";}
        Matrix x = solsByInverse(B);
        String res = "";
        for(int i = 0; i < rowCount; i++){
            res += String.format("x%d = %.2f", i + 1, x.contents[i][0]) + "\n";
        }
        return res;
    }

    public double determinantByERO(){
    // get determinant of self by using elementary row
    // operation manipulation
        determinant = 1.00;
        double[][] oldContents = contentsClone();

        reduceToEchelon();
        echelonToReducedEchelon();
        if (hasZeroRow()) {return 0.00;}
        contents = oldContents;

        return determinant;
    }

    public double determinantByCofactor(){
    // get determinant of self by using cofactor expansion
    // along the first row
        if (rowCount == 1) {return contents[0][0];}
        double res = 0.00;
        for(int i = 0; i < rowCount; i++){
            res += (contents[0][i])*(subMatrix(0, i).determinantByCofactor())*(-2*(i % 2) + 1);
        }
        return res;
    }

    public SquareMatrix inverseByCofactor(){
    // get inverse of self by using adjoint cofactor
    // matrix
        SquareMatrix res = new SquareMatrix();
        res = adjointCofactor();
        res.scalarMultiply(1/determinantByCofactor());
        return res;
    }

    public Matrix solsByCramer(Matrix B){
    // get X matrix, solution of the equation (self)X = B
    // using cramer's rule
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

    public String getSolsByCramer(Matrix B){
    // get solution of the equation (self)X = B
    // using cramer's rule in String form
        Matrix x = solsByCramer(B);
        String res = "";
        for(int i = 0; i < rowCount; i++){
            res += String.format("x%d = %.2f", i + 1, x.contents[i][0]) + "\n";
        }
        return res;
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

    public void checkInvertibility(){
    // Check invertibility of self
        double[][] oldContents = contentsClone();

        reduceToEchelon();
        echelonToReducedEchelon();
        if (hasZeroRow()) {isInvertible = false;}
        contents = oldContents;
    }

    protected SPLMatrix getIdentityMatrix(){
    // get Identity matrix with the same
    // size as self
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
    // Get subMatrix M(rowNum)(colNum) containing all elements of self,
    // aij with a != rowNum and b != colNum
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
    // get adjoint Cofactor Matrix of self
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount, columnCount);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                res.contents[i][j] = (-2*((i + j) % 2) + 1)*(subMatrix(i, j).determinantByCofactor());
            }
        }
        res.transpose();
        return res;
    }

    protected SquareMatrix clone(){
    // get deep clone of self
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(rowCount, columnCount);
        res.contents = contentsClone();
        return res;
    }

    protected boolean hasZeroRow(){
    // give predicate on whether self
    // has a zero row
        boolean res = false;
        for (int i = 0; i < rowCount; i++){
            if (isZeroRow(i)) {res = true;}
        }
        return res;
    }
}
