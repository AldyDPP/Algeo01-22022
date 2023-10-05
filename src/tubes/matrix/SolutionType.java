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
        // Setter for RealPart attribute
        this.realPart = realPart;
    }

    public double getRealPart() {
        // Getter for RealPart attribute
        return realPart;
    }

    public HashMap<Character, Double> getParametricParts() {
        // Getter for ParametricParts attribute
        return parametricParts;
    }

    public String toString(){
        // Return self as a String
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
        // return SolutionType resulting from x1 + x2*factor
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
}
