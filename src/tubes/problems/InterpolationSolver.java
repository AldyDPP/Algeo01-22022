package tubes.problems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SolutionType;

public class InterpolationSolver {

    public double[][] xyValue;
    public int degree;
    SPLMatrix coefficientSPL;
    public SolutionType[] coefficients;
    public double aprox = 1;

    public InterpolationSolver(){
        // Do Nothing
    }

    public void keyboardInput(Scanner sc){
    // Get Interpolation problem from terminal
        int n;

        System.out.print("Number of points : ");
        n = sc.nextInt();
        degree = n - 1;
        xyValue = new double[n][2];

        for(int i = 0; i < n; i++){
            System.out.print(String.format("Point %d: ", i + 1));
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            xyValue[i][0] = x;
            xyValue[i][1] = y;
        }
    }
    
    public void textInput(String path) throws IOException{
    // Get Interpolation problem from text file
        String data = "";
        data = new String(
            Files.readAllBytes(Paths.get(path)));
        String[] arr = null;

        arr = data.split("\n");

        degree = arr.length - 2;

        xyValue = new double[degree + 1][2];
        for(int i = 0; i < degree + 1; i++){
            String[] subArr = null;
            subArr = arr[i].split(" ");
            xyValue[i][0] = Double.parseDouble(subArr[0]);
            xyValue[i][1] = Double.parseDouble(subArr[1]);
        }
        
        aprox = Double.parseDouble(arr[arr.length-1]);
    }

    public void generateMatrix() {
    // Generate SPL Matrix from given values
        coefficientSPL = new SPLMatrix();
        coefficientSPL.initializeMatrix(degree + 1, degree + 2);
        for(int i = 0; i < degree + 1; i++){
            double x = xyValue[i][0];
            double y = xyValue[i][1];
            for(int j = 0; j < degree + 1; j++){
                double vals = Math.pow(x, (double)j);
                coefficientSPL.contents[i][j] = vals;
            }
            coefficientSPL.contents[i][degree + 1] = y;
        }
    }

    public void generateSolution(){
    // Generate coefficient of the polynomial from
    // the Matrix
        coefficients = new SolutionType[degree + 1];
        coefficients = coefficientSPL.solveFromScratch();
    }

    public double approximateValue(double x){
    // Approximate the value of the interpolated polynomial
    // at x
        double res = 0.00;
        for(int i = 0; i < degree + 1; i++){
            res += coefficients[i].getRealPart()*Math.pow(x, i); 
        }
        return res;
    }

    String getSolsString(){
    // Get interpolated polynomial in String form
        String res = "f(x) = ";
        for(int i = degree; i >= 0; i--){
            if (i == degree){
                res +=
                    String.format("%.2f", coefficients[i].getRealPart())
                    + String.format("x^%d", i);
            }
            else if (i > 1){
                if (coefficients[i].getRealPart() >= 0) {System.out.print(" + ");}
                if (coefficients[i].getRealPart() < 0) {System.out.print(" - ");}
                res +=
                    String.format("%.2f", Math.abs(coefficients[i].getRealPart()))
                    + String.format("x^%d", i);
            }
            else if (i == 1){
                if (coefficients[i].getRealPart() >= 0) {System.out.print(" + ");}
                if (coefficients[i].getRealPart() < 0) {System.out.print(" - ");}
                res +=
                    String.format("%.2f", Math.abs(coefficients[i].getRealPart()))
                    + String.format("x", i);
            }
            else{
                if (coefficients[i].getRealPart() >= 0) {System.out.print(" + ");}
                if (coefficients[i].getRealPart() < 0) {System.out.print(" - ");}
                res +=
                    String.format("%.2f", Math.abs(coefficients[i].getRealPart()));
            }
        }
        return res;
    }

    public void solveAndPrint(){
    // Solve interpolation problem and print the result
        generateMatrix();
        generateSolution();
        System.out.print(getSolsString());
    }

    public String solveAndReturnString(){
    // Solve interpolation problem and return the result
    // as String
        generateMatrix();
        generateSolution();
        return getSolsString();
    }

}
