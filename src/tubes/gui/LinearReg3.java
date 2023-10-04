package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SolutionType;
import tubes.problems.MultivariateLinearRegressionSolver;

public class LinearReg3 extends TemplateFrame implements ActionListener {
	ExitTemplateButton exit, back, maketxt;
	String outputstr;
	LinearReg3(MultivariateLinearRegressionSolver MLRS, Matrix m1) {
		this.setTitle("AldyDPP Matrix Calculator -> Multivariable Linear Regression");
		
		// System.out.printf("%d", MLRS.n);
		
		/* Header text */
		HeaderText resulttext = new HeaderText("Solutions:");
		this.add(resulttext);
		
		
		/* Solution texts */
		String res = "";
		String resWithoutHtmlFormatting = "";
		
		MLRS.constructOLSMatrix();
		MLRS.OLSEquationMatrix.reduceToEchelon();
        SolutionType[] Beta = MLRS.getBetaMatrix();
        res += "<html><center>y = ";
        resWithoutHtmlFormatting += "y = ";
        for(int i = 0; i < MLRS.n + 1; i++){
            if (i == 0 && Beta[0].getRealPart() != 0.00) {
                res += (String.format("%.3f", Beta[i].getRealPart()));
                resWithoutHtmlFormatting += (String.format("%.2f", Beta[i].getRealPart()));
            }
            else{
                if (Beta[i].getRealPart() == 0) {continue;}
                if (Beta[i].getRealPart() > 0 && res.length() > 4){res += " + "; resWithoutHtmlFormatting += " + ";}
                if (Beta[i].getRealPart() < 0 && res.length() > 4){res += " - "; resWithoutHtmlFormatting += " - ";}
                
                if (res.length() <= 4){
                    res += String.format("%.3f", Beta[i].getRealPart());
                    resWithoutHtmlFormatting += String.format("%.2f", Beta[i].getRealPart());
                }
                else{
                    res += String.format("%.3f", Math.abs(Beta[i].getRealPart()));
                    resWithoutHtmlFormatting += String.format("%.2f", Math.abs(Beta[i].getRealPart()));
                }
                res += String.format("x%d", i);
                resWithoutHtmlFormatting += String.format("x%d", i);
            }
            
            if (i % 4 == 3) {
            	res += "<br>";
            }
        }
        res += "</center><html>";
        
        
        
        /*Solution*/
		JLabel solstext = new JLabel(res);
		int fontsize = 20;
		fontsize = 30;
		solstext.setBounds(-20,250,800,400);
		solstext.setHorizontalAlignment(JLabel.CENTER);
		solstext.setHorizontalTextPosition(JLabel.CENTER);
		
		solstext.setVerticalAlignment(JLabel.TOP);
		solstext.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, fontsize));
		solstext.setForeground(Color.WHITE);
		
		//solstext.setPreferredSize(new Dimension(500, 200));
		
		//solsbox.add(solstext);
		this.add(solstext);
		
			
		String absides = "";
		String s = "";
		
		for (int k = 1; k < m1.rowCount; k++) {
			absides += String.format("%.1f", m1.contents[k][0]);
			if (k != m1.rowCount - 1) {
				absides += ",";
			}
		}
		
		s += "f(" +  absides + ") = "; // + hasil
		
		Matrix m2 = new Matrix();
		m2.initializeMatrix(1, m1.rowCount);
		for (int k = 0; k < m2.columnCount; ++k) {
			m2.contents[0][k] = Beta[k].getRealPart();
		}
		
		Matrix mhasil = Matrix.multiplyMatrix(m2, m1);
		
		m2.printMatrix();
		m1.printMatrix();
		
		s += String.format("%.2f", mhasil.contents[0][0]);
		
		JLabel approx = new JLabel();
		approx.setText(s);
		approx.setForeground(Color.WHITE);
		approx.setHorizontalAlignment(JLabel.CENTER);
		approx.setBounds(200,400,400,50);
		approx.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
		this.add(approx);
		
		
		/* Create txt output file button */
		maketxt = new ExitTemplateButton();
		maketxt.setBounds(10,600, 200,40);
		maketxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		maketxt.addActionListener(e->this.dispose());
		maketxt.addActionListener(this);
		maketxt.setText("Convert result to txt file");
		maketxt.setForeground(new Color(130, 130, 130));
		this.add(maketxt);
		
		
		outputstr = resWithoutHtmlFormatting + "\n" + s;
		
		/* exit button */
		exit = new ExitTemplateButton();
		exit.setBounds(290,550, 180,40);
		exit.addActionListener(e->this.dispose());
		
		back = new ExitTemplateButton();
		back.setText("Back to Home");
		back.addActionListener(e->this.dispose());
		back.addActionListener(this);
		this.add(exit);
		this.add(back);
		
		this.add(bg);

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new LaunchPage();
		}
		else if (e.getSource()==maketxt) {
			new TxtOutputPage(outputstr);
		}
	}
}