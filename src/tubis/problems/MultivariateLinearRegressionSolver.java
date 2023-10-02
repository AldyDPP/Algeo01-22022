package tubes.problems;

import java.io.IOException;
import java.util.Scanner;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SolutionType;
import tubes.matrix.src.SquareMatrix;

public class MultivariateLinearRegressionSolver {

    public int n;
    public int m;
    public SPLMatrix xyMatrix = new SPLMatrix();
    public SPLMatrix OLSEquationMatrix = new SPLMatrix();
    
    public MultivariateLinearRegressionSolver(){
        // Do Nothing
    }

    public void keyboardInput(Scanner sc){

        System.out.print("Input number of regressor: ");
        n = sc.nextInt() + 1;

        System.out.print("Input number of sample: ");
        m = sc.nextInt();

        xyMatrix.initializeMatrix(m, n);

        for(int i = 0; i < m; i++){
            System.out.print("Input each sample:\n");
            for(int j = 0; j < n; j++){
                if(j != n - 1) {System.out.print(String.format("x%d%d : ", i, j));}
                else {System.out.print(String.format("y%d : ", i));}
                xyMatrix.contents[i][j] = sc.nextDouble();
            }
        }
    }

    public void textInput(String path) throws IOException{
        xyMatrix.txtInputMatrix(path);
        n = xyMatrix.columnCount - 1; // n + 1
        m = xyMatrix.rowCount;
        Matrix m2 = new Matrix();
        
    }

    SquareMatrix getXMatrix(){
        SquareMatrix res = new SquareMatrix();
        res.initializeMatrix(m, n + 1);
        for(int i = 0; i < m; i++){
            res.contents[i][0] = (double)1;
        }
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n ; j++){
                res.contents[i][j + 1] = xyMatrix.contents[i][j];
            }
        }
        return res;
    }

    Matrix getYMatrix(){
        return xyMatrix.getMatrixB();
    }

    public void constructOLSMatrix(){
        SquareMatrix A = new SquareMatrix();
        Matrix B = new Matrix();
        A = getXMatrix();
        B = getYMatrix();
        OLSEquationMatrix = SPLMatrix.augmentABMatrix(
            Matrix.multiplyMatrix(A.transposeOf(), A),
            Matrix.multiplyMatrix(A.transposeOf(), B));
    }

    public SolutionType[] getBetaMatrix(){
        OLSEquationMatrix.reduceToEchelon();
        return OLSEquationMatrix.AugmentedSols();
    }

    String getYasString(){
        String res = "";
        SolutionType[] Beta = getBetaMatrix();
        res += "y = ";
        for(int i = 0; i < n + 1; i++){
            if (i == 0 && Beta[0].getRealPart() != 0.00) {
                res += (String.format("%.2f", Beta[i].getRealPart()));
            }
            else{
                if (Beta[i].getRealPart() == 0) {continue;}
                if (Beta[i].getRealPart() > 0 && res.length() > 4){res += " + ";}
                if (Beta[i].getRealPart() < 0 && res.length() > 4){res += " - ";}
                if (res.length() <= 4){
                    res += String.format("%.2f", Beta[i].getRealPart());
                }
                else{
                    res += String.format("%.2f", Math.abs(Beta[i].getRealPart()));
                }
                res += String.format("x%d", i);
            }
        }
        return res;
    }

    public void calculateAndPrint(){
        constructOLSMatrix();
        OLSEquationMatrix.reduceToEchelon();
        System.out.println(getYasString());
    }

    public String calculateAndGetString(){
        constructOLSMatrix();
        OLSEquationMatrix.reduceToEchelon();
        return getYasString();
    }

    public static void main(String[] args) throws IOException{
        MultivariateLinearRegressionSolver MLGS = new MultivariateLinearRegressionSolver();
        MLGS.textInput("./tubes/matrix/src/MLRS.txt");
        MLGS.constructOLSMatrix();
        // System.out.println("OLS Matrix : ");
        // MLGS.OLSEquationMatrix.printMatrix();
        MLGS.OLSEquationMatrix.reduceToEchelon();
        // System.out.println("OLS Echelon Matrix : ");
        // MLGS.OLSEquationMatrix.printMatrix();
        // MLGS.printRes();
        // MLGS.OLSEquationMatrix.printAugmentedSols();
    }
}
