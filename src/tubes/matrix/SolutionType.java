package tubes.matrix;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class SolutionType {
    
    // Attribute
    protected double realPart;
    protected HashMap<Character, Double> parametricParts;
    protected int length;

    // Method
    public SolutionType(int n){
        // Do Nothing
        realPart = 0;
        parametricParts = new HashMap<Character, Double>();
        length = n;
    }

    public void setRealPart(double realPart) {
        this.realPart = realPart;
    }

    public double getRealPart() {
        return realPart;
    }

    public HashMap<Character, Double> getParametricParts() {
        return parametricParts;
    }

    public String toString(){
        String res = "";
        if (realPart != 0){
            res += String.format("%.2f ", realPart);
        }
        for (Map.Entry<Character, Double> entry : parametricParts.entrySet()){
            if ((entry.getValue() > (double)0) && (res.length() > 0) ) {res += "+ ";}
            if ((entry.getValue() < (double)0) && (res.length() > 0) ) {res += "- ";}
            if ((entry.getValue() < (double)0) && (res.length() == 0) ) {res += "-";}
            if (entry.getValue() > 0 || entry.getValue() < 0){
                res += String.format("%.2f%c ", Math.abs(entry.getValue()), entry.getKey());
            }
        }
        if (res.length() == 0){res += "0.00";}
        return res;
    }

    static SolutionType add(SolutionType x1, SolutionType x2, double factor){
        SolutionType res = new SolutionType(x1.length);
        res.setRealPart(x1.realPart + factor*x2.realPart);

        res.parametricParts.putAll(x1.parametricParts); // Shallow copy

        x2.parametricParts.forEach(
            (variable, coefficient) 
            -> res.parametricParts.put(
                variable, 
                res.parametricParts.getOrDefault(variable, (double)0) + factor*coefficient)
                );

        return res;
    }

    public static void main(String[] args){
        SolutionType x1 = new SolutionType(3);
        SolutionType x2 = new SolutionType(3);
        x1.realPart = 4;
        x1.parametricParts.put('r', (double)7.75);
        x2.realPart = -7;
        x2.parametricParts.put('r', (double)0.75);
        x2.parametricParts.put('p', (double)-2.5);
        SolutionType x3 = add(x1, x2, (double)-1.5);
        System.out.println(x1.toString());
        System.out.println(x2.toString());
        System.out.println(x3.toString());
    }

}
