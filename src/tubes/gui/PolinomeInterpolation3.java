package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tubes.problems.InterpolationSolver;

public class PolinomeInterpolation3 extends TemplateFrame implements ActionListener {
	
	TemplateButton exit, back, maketxt;
	String outputstr;
	PolinomeInterpolation3(InterpolationSolver interp, double num) {
		this.setTitle("AldyDPP Matrix Calculator -> Polinome Interpolation");
		
		String sol = "";
		String solWithoutHtmlFormatting = "";
		
		/* Generate solution string*/
		interp.generateMatrix();
		interp.generateSolution();
		
		int entercounter = 0;
		sol += ("<html><center>f(x) = ");
		solWithoutHtmlFormatting += ("f(x) = ");
		
        for(int i = interp.degree; i >= 0; i--){
            if (i == interp.degree){
                sol += (
                    String.format("%.2f", interp.coefficients[i].getRealPart())
                    + String.format("x^%d", i));
                
                solWithoutHtmlFormatting += (
                        String.format("%.2f", interp.coefficients[i].getRealPart())
                        + String.format("x^%d", i));
            }
            else if (i > 1){
                if (interp.coefficients[i].getRealPart() >= 0) {sol += (" + "); solWithoutHtmlFormatting += (" + ");}
                if (interp.coefficients[i].getRealPart() < 0) {sol += (" - "); solWithoutHtmlFormatting += (" - ");}
                
                sol += (
                    String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart()))
                    + String.format("x^%d", i));
                
                solWithoutHtmlFormatting += (
                        String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart()))
                        + String.format("x^%d", i));
            }
            else if (i == 1){
                if (interp.coefficients[i].getRealPart() >= 0) {sol += (" + "); solWithoutHtmlFormatting += (" + ");}
                if (interp.coefficients[i].getRealPart() < 0) {sol += (" - "); solWithoutHtmlFormatting += (" - ");}
                sol += (
                    String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart()))
                    + String.format("x", i));
                
                solWithoutHtmlFormatting += (
                        String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart()))
                        + String.format("x^%d", i));
            }
            else{
                if (interp.coefficients[i].getRealPart() >= 0) {sol += (" + "); solWithoutHtmlFormatting += (" + ");}
                if (interp.coefficients[i].getRealPart() < 0) {sol += (" - "); solWithoutHtmlFormatting += (" - ");}
                sol += (
                    String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart())));
                
                solWithoutHtmlFormatting += (
                        String.format("%.2f", Math.abs(interp.coefficients[i].getRealPart()))
                        + String.format("x^%d", i));
            }
            entercounter++;
            if (entercounter % 5 == 4) {
            	sol += "<br>";
            }
        }
        sol += "</center></html>";
        //////////////////////////////
        //System.out.println(sol);
        
        // Header Text
        HeaderText ResultText = new HeaderText("Solutions: ");
		this.add(ResultText);
		
		/*Solution*/
		JLabel solstext = new JLabel(sol);
		int fontsize = 25;
		solstext.setHorizontalAlignment(JLabel.CENTER);
		solstext.setBounds(-20,250,800,400);
		
		//JPanel solsbox = new JPanel();
		
		//solsbox.setBounds(0,250,700,400);
		//solsbox.setOpaque(false);
		
		solstext.setVerticalAlignment(JLabel.TOP);
		solstext.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, fontsize));
		solstext.setForeground(Color.WHITE);
		//solsbox.add(solstext);
		this.add(solstext);
		
		String s = "";
		s += "f(" + num + ") = " + ((float)interp.approximateValue(num));
		JLabel approx = new JLabel();
		approx.setText(s);
		approx.setForeground(Color.WHITE);
		approx.setHorizontalAlignment(JLabel.CENTER);
		approx.setBounds(200,400,400,50);
		approx.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
		this.add(approx);
		
		/*Exit and back home buttons*/
		exit = new ExitTemplateButton();
		exit.setBounds(290,550, 180,40);
		exit.addActionListener(e->this.dispose());
		
		back = new ExitTemplateButton();
		back.setText("Back to Home");
		back.addActionListener(e->this.dispose());
		back.addActionListener(this);
		
		
		this.add(exit);
		this.add(back);
		
		/* Create txt output file button */
		maketxt = new ExitTemplateButton();
		maketxt.setBounds(10,600, 200,40);
		maketxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		maketxt.addActionListener(e->this.dispose());
		maketxt.addActionListener(this);
		maketxt.setText("Convert result to txt file");
		maketxt.setForeground(new Color(130, 130, 130));
		this.add(maketxt);
		
		// outputstr for txt output
		outputstr = solWithoutHtmlFormatting + "\n" + s;
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==back) {
			new LaunchPage();
		}
		else if (e.getSource()==maketxt) {
			new TxtOutputPage(outputstr);
		}
	}
}