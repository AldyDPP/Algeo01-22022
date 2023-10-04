package tubes.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SolutionType;

public class SPL3 extends TemplateFrame implements ActionListener {
	SPLMatrix mspl = new SPLMatrix();
	int size;
	TemplateButton gauss, gaussjordan, inverse, cramer;
	SPL3(SPLMatrix m2) {
		this.setTitle("AldyDPP Matrix Calculator -> Linear Equation Solver");
		
		size = m2.rowCount;
		mspl = m2;
		HeaderText heder = new HeaderText("Pick Calculation Method!");
		this.add(heder);
		
		gauss = new TemplateButton();
		gaussjordan = new TemplateButton();
		inverse = new TemplateButton();
		cramer = new TemplateButton();
		
		gauss.setBounds(225, 200, 300, 75);
		gauss.setText("Gauss Elimnation");
		gauss.addActionListener(this);
		gauss.addActionListener(e->this.dispose());
		gauss.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		
		gaussjordan.setBounds(225, 300, 300, 75);
		gaussjordan.setText("Gauss-Jordan Elimination");
		gaussjordan.addActionListener(this);
		gaussjordan.addActionListener(e->this.dispose());
		gaussjordan.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
		
		inverse.setBounds(225, 400, 300, 75);
		inverse.setText("Inverse Matrix");
		inverse.addActionListener(this);
		inverse.addActionListener(e->this.dispose());
		inverse.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	
		cramer.setBounds(225, 500, 300, 75);
		cramer.setText("Cramer Method");
		cramer.addActionListener(this);
		cramer.addActionListener(e->this.dispose());
		cramer.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		
		this.add(gaussjordan);
		this.add(gauss);
		this.add(cramer);
		this.add(inverse);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			SolutionType[] sols = mspl.solveFromScratch();
			String solsstr;
			if (e.getSource()==gauss) {
				solsstr = mspl.GaussSolve();
				new SPL4(sols , false, solsstr);
			}
			
			else if (e.getSource()==gaussjordan) {
				solsstr = mspl.GaussJordanSolve();
				new SPL4(sols , false, solsstr);
			}
			
			else if (e.getSource()==inverse) {
				if (mspl.rowCount != mspl.columnCount - 1) {
					new ErrorPage("'*Sighs*, I can't solve this with inverse matrix, the variable matrix isn't a square!', Misaka exclaims");
				}
				else {
					solsstr = mspl.inverseMatrixSolve();
					new SPL4(sols , false, solsstr);
				}
			}
			
			else if (e.getSource()==cramer) {
				if (mspl.rowCount != mspl.columnCount - 1) {
					new ErrorPage("'*Sighs*, I can't solve this with Cramer method, the variable matrix isn't a square!', Misaka exclaims");
				}
				else {
					solsstr = mspl.cramerSolve();
					new SPL4(sols , false, solsstr);
				}
			}
		}
		
		catch (ArrayIndexOutOfBoundsException exc) {
			
			new ErrorPage("'Try checking your txt file', says Misaka'");
		}
	}
}