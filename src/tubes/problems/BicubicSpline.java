package tubes.problems;

import java.io.IOException;
import java.util.Scanner;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SquareMatrix;

public class BicubicSpline {

    int n;
    int m;
    SquareMatrix X;
    SPLMatrix OLSEquationMatrix = new SPLMatrix();
    public Matrix Y;
    Matrix a;
    public double[] target;

    public BicubicSpline(){
        // Do nothing
    }

    public void createXMatrix(){
    // Create X Matrix defined by the Bicubic Spline Interpolation
    // equation
        X = new SquareMatrix();
        X.initializeMatrix(16, 16);
        for(int y = 0; y < 2; y++){
            for(int x = 0; x < 2; x++){
                int baseRowIdx = x + 2*y; // Map (0, 0) -> 0, (1, 0) -> 1, etc.
                for(int j = 0; j < 4; j++){
                    for(int i = 0; i < 4; i++){
                        X.contents[baseRowIdx][4*j + i] = Math.pow(x, i)*Math.pow(y, j);
                        X.contents[4 + baseRowIdx][4*j + i] = i*Math.pow(x, (i > 1) ? i-1 : 0)*Math.pow(y, j);
                        X.contents[8 +baseRowIdx][4*j + i] = j*Math.pow(x, i)*Math.pow(y, (j > 1) ? j - 1 : 0);
                        X.contents[12 + baseRowIdx][4*j + i] = i*j*Math.pow(x, (i > 1) ? i-1 : 0)*Math.pow(y, (j > 1) ? j - 1 : 0);
                    }
                }
            }
        }
    }

    public void keyboardInput(Scanner sc){
    // Input values for the bicubic spline problem from the terminal
        Y = new Matrix();
        Y.initializeMatrix(16, 1);
        for(int i = 0; i < 16; i++){
            Y.contents[i][0] = sc.nextDouble();
        }
        target = new double[2];
        target[0] = sc.nextDouble();
        target[1] = sc.nextDouble();
    }

    public void textInput(String path) throws IOException{
    // Input values for the bicubic spline problem from txt file
        SquareMatrix temp = new SquareMatrix();
        temp.txtInputMatrix(path);
        temp.printMatrix();
        Y = new Matrix();
        Y.initializeMatrix(16, 1);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                Y.contents[4*i + j][0] = temp.contents[i][j];
            }
        }
        target = new double[2];
        target[0] = temp.contents[4][0];
        target[1] = temp.contents[4][1];
    }

    public void evaluateA(){
    // Evaluate matrix of coefficients a
    // using inverse matrix method
        a = new Matrix();
        a.initializeMatrix(16, 1);
        a = Matrix.multiplyMatrix(X.inverseByERO(), Y);
    }

    public double calculateVal(double x, double y){
    // Calculate the approximated value at point (x, y)
    // from the obtained a coefficients
        double res = 0.00;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                res += (a.contents[4*i + j][0])*Math.pow(x, i)*Math.pow(y, j);
            }
        }
        return res;
    }

    public void splineAndPrint(){
    // Solve the bicubic spline problem and print the result to the terminal
        createXMatrix();
        evaluateA();
        System.out.println(String.format(
            "Nilai dari f(%.4f, %.4f) = %.4f", target[0], target[1], calculateVal(target[0], target[1])
        ));
    }

    public String splineAndReturnString(){
    // Solve the bicubic spline problem and return the result as String
        createXMatrix();
        evaluateA();
        String res = String.format(
            "Nilai dari f(%.4f, %.4f) = %.4f\n", target[0], target[1], calculateVal(target[0], target[1])
        );
        return res;
    }
}
