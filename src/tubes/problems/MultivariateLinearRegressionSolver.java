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
    public Matrix m2;
    
    public MultivariateLinearRegressionSolver(){
        // Do Nothing
    }

    public void keyboardInput(Scanner sc){
    // Input Multivariate Linear Regression problem
    // from terminal

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
    // Input Multivariate Linear Regression Problem
    // from txt file
        xyMatrix.txtInputMatrix(path);
        n = xyMatrix.columnCount - 1; // n + 1
        m = xyMatrix.rowCount - 1;
        m2 = new Matrix();
        m2.initializeMatrix(n + 1, 1);
        
        m2.contents[0][0] = 1;
        
        for (int k = 1; k < n + 1; k++) {
        	m2.contents[k][0] = xyMatrix.contents[m][k-1];
        }
        
    }

    SquareMatrix getXMatrix(){
    // getXMatrix defined from the OLS Equation
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
    // getYMatrix defined from the OLS Equation
        return xyMatrix.getMatrixB();
    }

    public void constructOLSMatrix(){
    // Construct SPLMatrix containing SPL made from the OLS
    // Equation and the given data
        SquareMatrix A = new SquareMatrix();
        Matrix B = new Matrix();
        A = getXMatrix();
        B = getYMatrix();
        OLSEquationMatrix = SPLMatrix.augmentABMatrix(
            Matrix.multiplyMatrix(A.transposeOf(), A),
            Matrix.multiplyMatrix(A.transposeOf(), B));
    }

    public SolutionType[] getBetaMatrix(){
    // get Array of Solutions containing the coefficients of each regressor
        OLSEquationMatrix.reduceToEchelon();
        return OLSEquationMatrix.AugmentedSols();
    }

    String getYasString(){
    // get the function in String form
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
    // Solve the Multivariate Linear Regression Problem and print the
    // result to terminal
        constructOLSMatrix();
        OLSEquationMatrix.reduceToEchelon();
        System.out.println(getYasString());
    }

    public String calculateAndGetString(){
    // Solve the Multivariate Linear Regression Problem and return
    // the result as String
        constructOLSMatrix();
        OLSEquationMatrix.reduceToEchelon();
        return getYasString();
    }
}
