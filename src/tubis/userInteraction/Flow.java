package tubes.userInteraction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tubes.bonus.Bonus;
import tubes.bonus.BonusRGB;
import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SquareMatrix;
import tubes.problems.BicubicSpline;
import tubes.problems.InterpolationSolver;
import tubes.problems.MultivariateLinearRegressionSolver;

public class Flow {

    public Flow(){
        // Do nothing
    }

    public void start(Scanner sc) throws IOException{
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linear");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic Spline");
        System.out.println("6. Regresi Linear Berganda");
        System.out.println("7. Perbesar Image");
        System.out.println("8. Exit");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan = sc.nextInt();
        while(pilihan <= 0 || pilihan > 8){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan = sc.nextInt();
        }
        switch(pilihan){
            case 1 :
              solveSPL(sc);
              break;
            case 2 :
              findDeterminant(sc);
              break;
            case 3 :
              giveInverse(sc);
              break;
            case 4 :
              interpolations(sc);
              break;
            case 5 :
              bicubicSplineInterpolations(sc);
              break;
            case 6 :
              multivariateLinearRegression(sc);
              break;
            case 7 :
              bonus(sc);
              break;
            case 8 :
              System.exit(0);
              break;
        }
    }

    void solveSPL(Scanner sc) throws IOException{
        System.out.println();
        System.out.println("Submenu Sistem Persamaan Linear");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan = sc.nextInt();
        while(pilihan <= 0 || pilihan > 4){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan = sc.nextInt();
        }
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        SPLMatrix M = new SPLMatrix();
        switch(pilihan2){
            case 1 :
              M.keyboardInputMatrix(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              M.txtInputMatrix(path);
              break;
        }

        String res = "";

        switch(pilihan){
            case 1 :
              res = M.GaussSolve();
              break;
            case 2 :
              res = M.GaussJordanSolve();;
              break;
            case 3 :
              res = M.inverseMatrixSolve();;
              break;
            case 4 :
              res = M.cramerSolve();;
              break;
        }
        processString(res, sc);
    }

    void findDeterminant(Scanner sc) throws IOException{
        System.out.println();
        System.out.println("Submenu Menghitung Determinan");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekspansi Kofaktor");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan = sc.nextInt();
        while(pilihan <= 0 || pilihan > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan = sc.nextInt();
        }
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        SquareMatrix M = new SquareMatrix();
        switch(pilihan2){
            case 1 :
              M.keyboardInputMatrix(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              M.txtInputMatrix(path);
              break;
        }

        String res = "";

        switch(pilihan){
            case 1 :
              res += String.format("%.4f", M.determinantByERO());
              break;
            case 2 :
              res += String.format("%.4f", M.determinantByCofactor());
              break;
        }
        processString(res, sc);
    }
    
    void giveInverse(Scanner sc) throws IOException{
        System.out.println();
        System.out.println("Submenu Mencari Balikan Matris");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Kofaktor");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan = sc.nextInt();
        while(pilihan <= 0 || pilihan > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan = sc.nextInt();
        }
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        SquareMatrix M = new SquareMatrix();
        switch(pilihan2){
            case 1 :
              M.keyboardInputMatrix(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              M.txtInputMatrix(path);
              break;
        }

        M.checkInvertibility();
        SquareMatrix resM = new SquareMatrix();
        String res = "";
        if (SquareMatrix.isInvertible(resM)){
            switch(pilihan){
                case 1 :
                  resM = M.inverseByERO();
                  System.out.println("Matriks Hasil Inverse : ");
                  res = resM.toString();
                  break;
                case 2 :
                  resM = M.inverseByCofactor();
                  System.out.println("Matriks Hasil Inverse : ");
                  res = resM.toString();
                  break;
            }
        }
        else{
            res += "Matriks Tidak Memiliki Invers";
        }
        processString(res, sc);
    }

    void interpolations(Scanner sc) throws IOException{
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        InterpolationSolver IS = new InterpolationSolver();
        switch(pilihan2){
            case 1 :
              IS.keyboardInput(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              IS.textInput(path);
              break;
        }
        String res = IS.solveAndReturnString();
        processString(res, sc);
    }

    void bicubicSplineInterpolations(Scanner sc) throws IOException{
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        BicubicSpline BCS = new BicubicSpline();
        switch(pilihan2){
            case 1 :
              BCS.keyboardInput(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              BCS.textInput(path);
              break;
        }
        String res = BCS.splineAndReturnString();
        processString(res, sc);
    }

    void multivariateLinearRegression(Scanner sc) throws IOException{
        
        System.out.println();
        System.out.println("Pilih cara input file");
        System.out.println("1. Input dengan Keyboard");
        System.out.println("2. Input path file txt");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan2 = sc.nextInt();
        while(pilihan2 <= 0 || pilihan2 > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan2 = sc.nextInt();
        }

        MultivariateLinearRegressionSolver MLRS = new MultivariateLinearRegressionSolver();
        switch(pilihan2){
            case 1 :
              MLRS.keyboardInput(sc);
              break;
            case 2 :
              String path;
              System.out.println("Masukkan path file relatif dari current working directory");
              System.out.print("~ ");
              String decoy = sc.nextLine();
              path = sc.nextLine();
              MLRS.textInput(path);
              break;
        }
        String res = MLRS.calculateAndGetString();
        processString(res, sc);
    }

    void bonus(Scanner sc) throws IOException{
      System.out.println();
      System.out.println("Masukkan path file image yang ingin diperbesar relatif terhadap current working directory");
      System.out.print("~ ");
      String decoy = sc.nextLine();
      String path = sc.nextLine();

      System.out.println();
      System.out.println("Pilih mode zoom");
      System.out.println("1. Zoom Grayscale");
      System.out.println("2. Zoom berwarna");
      System.out.println("Masukkan pilihan opsi");
      System.out.print("~ ");
      int pilihan = sc.nextInt();

      System.out.println();
      System.out.println("Masukkan Faktor Zoom yang diinginkan (Integer)");
      System.out.print("~ ");
      int zoomFactor = sc.nextInt();

      System.out.println();
      System.out.println("Masukkan Nama file hasil zoom");
      System.out.print("~ ");
      decoy = sc.nextLine();
      String resPath = sc.nextLine();

      switch(pilihan){
          case 1 :
            Bonus b = new Bonus();
            b.getImageAndZoom(path, zoomFactor, resPath);
            break;
          case 2 :
            BonusRGB bRGB = new BonusRGB(); // ini gk tau
            bRGB.getImageAndZoom(path, zoomFactor, resPath);
            break;
      }

    }

    void processString(String s, Scanner sc) throws IOException{
        System.out.println("\nPilih cara output hasil");
        System.out.println("1. Output ke Terminal");
        System.out.println("2. Output ke File");
        System.out.println("Masukkan pilihan opsi");
        System.out.print("~ ");
        int pilihan = sc.nextInt();
        while(pilihan <= 0 || pilihan > 2){
            System.out.println("Masukkan pilihan opsi yang valid");
            System.out.print("~ ");
            pilihan = sc.nextInt();
        }

        switch(pilihan){
          case 1 :
            System.out.println(s);
            break;
          case 2 :
            writeToTxt(s, sc);
            break;
        }
    }

    void writeToTxt(String s, Scanner sc) throws IOException{
        s = s.trim();
        System.out.println("\nInput nama file hasil : ");
        System.out.print("~ ");
        String decoy = sc.nextLine();
        String resPath = sc.nextLine();
        resPath = resPath.replace("\n", "");
        File f = new File("res\\" + resPath);
        File fcheck = new File("/res");
        if (!fcheck.exists()) {fcheck.mkdir();}
        f.createNewFile();
        FileWriter fwrite;

        try {
            fwrite = new FileWriter(f,false);
            fwrite.write(s);
            fwrite.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }      
    }
}
