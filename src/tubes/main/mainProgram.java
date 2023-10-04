package tubes.main;

import tubes.userInteraction.*;

import java.io.IOException;
import java.util.Scanner;

public class mainProgram {
   //uncomment this if want to use console!
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        Flow f = new Flow();
        f.start(sc);
    }
}
