package tubes.matrix.src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Matrix {
    
    // Attribute
    public int rowCount;
    public int columnCount;
    public double[][] contents;

    // Class Method
    public static Matrix multiplyMatrix(Matrix M1, Matrix M2){
        // Return result of M1 x M2
        Matrix res = new Matrix();
        res.rowCount = M1.rowCount;
        res.columnCount = M2.columnCount;
        res.contents = new double[M1.rowCount][M2.columnCount];

        for(int i = 0; i < M1.rowCount; i++){
            for(int j = 0; j < M2.columnCount; j++){
                res.contents[i][j] = 0;
                for(int k = 0; k < M1.columnCount; k++){
                    res.contents[i][j] += M1.contents[i][k] * M2.contents[k][j];
                }
            }
        }
        return res;
    }

    // Instance Method
    public Matrix(){
        // Do Nothing
    }

    public void initializeMatrix(int i, int j){
        rowCount = i;
        columnCount = j;
        contents = new double[i][j];
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
                System.out.print(String.format("Enter a[%d][%d] : ", i + 1, j + 1));
                contents[i][j] = sc.nextDouble();
            }
        }
    }

    public void txtInputMatrix(String path) throws IOException{
        // Input matrix from txt file
        String data = "";
        data = new String(
            Files.readAllBytes(Paths.get(path)));
        String[] arr = null;

        arr = data.split("\n");

        rowCount = arr.length;
        columnCount = arr[0].split(" ").length;

        contents = new double[rowCount][columnCount];
        for(int i = 0; i < rowCount; i++){
            String[] subArr = null;
            subArr = arr[i].split(" ");
            for(int j = 0; j < subArr.length; j++){
                contents[i][j] = Double.parseDouble(subArr[j]);
            }
        }
    }
    

    public void printMatrix(){
    // Procedure to print matrix values
        clearNegativeZero();
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                System.out.print(String.format("%.2f ", contents[i][j]));
            }
            System.out.println("");
        }
    }

    Matrix transposeOf(){
        // return transposed matrix
        Matrix res = new Matrix();
        res.initializeMatrix(columnCount, rowCount);
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                res.contents[j][i] = contents[i][j];
            }
        }
        return res;
    }

    void transpose(){
        // transpose self
        contents = transposeOf().contents;
    }

    void scalarMultiply(double x){
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                contents[i][j] *= x;
            }
        }
    }

    protected void clearNegativeZero(){
        // Get rid of negative 0.00
        double epsilon = 0.000001;
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < columnCount; j++){
                if (contents[i][j] < epsilon && contents[i][j] > -epsilon) {contents[i][j] = +0.00;}
            }
        }
    }

    protected boolean isZeroRow(int rowNum){
    // return true if a given row only has elements zero, false otherwise
        for(int j = 0; j < columnCount; j++){
            if (contents[rowNum][j] != 0) {return false;}
        }
        return true;
    }

    protected boolean isZeroColumn(int columnNum, int topRow){
        // return true if every element at a given column from a given topRow is zero, false otherwise
        for(int i = topRow; i < rowCount; i++){
            if (contents[i][columnNum] != 0) {return false;}
        }
        return true;
    }

    protected boolean isZeroMatrix(){
        // return true if every element at a given column from a given topRow is zero, false otherwise
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if (contents[i][j] != 0) {return false;}
            }
        }
        return true;
    }

    protected double[][] contentsClone(){
        double[][] res = contents.clone();
        for(int i = 0; i < rowCount; i++){
            res[i] = contents[i].clone();
        }
        return res;
    }

    protected Matrix clone(){
        Matrix res = new Matrix();
        res.initializeMatrix(rowCount, columnCount);
        res.contents = contentsClone();
        return res;
    }

    public static void main(String[] args) throws IOException{
        Matrix M;
        M = new Matrix();
        // Scanner sc = new Scanner(System.in);
        // A = new Matrix();
        // B = new Matrix();
        // A.keyboardInputMatrix(sc);
        // System.out.println();
        // B.keyboardInputMatrix(sc);
        // Matrix C = Matrix.multiplyMatrix(A, B);
        // System.out.println();
        // C.printMatrix();
        // sc.close();
        M.txtInputMatrix("./tubes/matrix/src/test.txt");

    }
}
