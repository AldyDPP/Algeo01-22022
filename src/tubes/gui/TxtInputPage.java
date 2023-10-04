package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SquareMatrix;
import tubes.problems.BicubicSpline;
import tubes.problems.InterpolationSolver;
import tubes.problems.MultivariateLinearRegressionSolver;

public class TxtInputPage extends TemplateFrame implements ActionListener {
	
	String key;
	TemplateButton next;
	String url;
	TemplateField jtf;
	SquareMatrix m = new SquareMatrix();
	SPLMatrix mspl = new SPLMatrix();
	SPLMatrix decoy = new SPLMatrix();
	JLabel inputtext = new JLabel();
	TxtInputPage(String inpkey) {
		
		key = inpkey;
		jtf = new TemplateField();
		jtf.setBounds(80,200,600,60);
		this.add(jtf);
		
		inputtext.setVerticalAlignment(JLabel.CENTER);
		inputtext.setHorizontalAlignment(JLabel.CENTER);
		inputtext.setText("Input txt File Directory!");
		inputtext.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		inputtext.setBounds(80, 50, 600, 100);
		inputtext.setVerticalAlignment(JLabel.CENTER);
		inputtext.setHorizontalAlignment(JLabel.CENTER);
		inputtext.setOpaque(false);
		inputtext.setForeground(new Color(255,255,255));
		inputtext.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(6)));
		this.add(inputtext);
		
		next = new NextTemplateButton();
		next.addActionListener(this);
		next.addActionListener(e -> this.dispose());
		this.add(next);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==next) {
			url = jtf.getText();
			
			try {
				
				m.txtInputMatrix(url);
				mspl.txtInputMatrix(url);
				decoy.txtInputMatrix(url);
				
				mspl.printMatrix();
				
				
				if (key == "det") {
					new Determinant3(m);
				}
				
				
				else if (key == "inv") {

					if (m.determinantByCofactor() == 0) {
						new Determinant4(0, false);
					}
					
					else {
						new Inverse3(m);
					}
				}
				
				else if (key == "spl") {
					decoy.reduceToEchelon();
					decoy.echelonToReducedEchelon();
					decoy.printMatrix();
					if (decoy.getAugmentedSols() == "The System of Linear Equations has no solutions") {
						new SPL4(null, true, null);
					}
					else {
						new SPL3(mspl);
					}
				}
				
				else if (key == "bcsp") {
					BicubicSpline bicSpl = new BicubicSpline();
					bicSpl.textInput(url);
					bicSpl.createXMatrix();
			        bicSpl.evaluateA();
			        double res = bicSpl.calculateVal(bicSpl.target[0], bicSpl.target[1]);
					new BicubicSpline2(res, bicSpl.target[0], bicSpl.target[1]);
				}
				
				else if (key == "pol") {
					InterpolationSolver interp = new InterpolationSolver();
					interp.textInput(url);
					new PolinomeInterpolation3(interp, interp.aprox);
				}
				else if (key == "reg") {
					MultivariateLinearRegressionSolver MLRS = new MultivariateLinearRegressionSolver();
					MLRS.textInput(url);
					MLRS.constructOLSMatrix();
					MLRS.OLSEquationMatrix.reduceToEchelon();
					new LinearReg3(MLRS, MLRS.m2);
				}
			} 
			
			catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e1) {
				if (key == "det") {
					new ErrorPage("'There's probably something wrong with your txt file...', explains Misaka");
				}
				else if (key == "inv") {
					new ErrorPage("'Please recheck your txt file directory and input again', Misaka politely asks");
				}
				else if (key == "spl") {
					new ErrorPage("'Where's the txt file?', asks Misaka");
				}
				else if (key == "bcsp") {
					new ErrorPage("'Did you make sure your txt file was correct?', asks Misaka");
				}
				else if (key == "pol") {
					new ErrorPage("'I think the txt file was a bit mistaken', Misaka guesses");
				}
				else if (key == "reg") {
					new ErrorPage("'I can't seem to read your txt file', clarifies Misaka");
				}	
			}
		}
	}
}