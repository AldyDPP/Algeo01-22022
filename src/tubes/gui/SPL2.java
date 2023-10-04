package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tubes.matrix.src.SPLMatrix;
import tubes.matrix.src.SolutionType;

public class SPL2 extends SPL1 implements ActionListener {
	int i, j;
	double num;
	TemplateButton nextButton2;
	public TemplateField mtfields[][];
	public SPLMatrix mspl = new SPLMatrix();
	public SPLMatrix decoy = new SPLMatrix();
	
	SPL2(int row, int col) {
		this.setTitle("AldyDPP Matrix Calculator -> Linear Equation Solver");
		
		mtfields = new TemplateField[row][col];
		mspl.initializeMatrix(row, col);
		this.remove(jtf); this.remove(jtf2); this.remove(next); this.remove(txtbutton); 
		this.remove(multsymbol);	
		
		
		inputtext.setText("Input Matrix!");
		JPanel newjp = new JPanel();
		newjp.setOpaque(false);
		newjp.setBounds(230,180, 300, 300);
		newjp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
		GridLayout experimentLayout = new GridLayout(row,col);
		newjp.setLayout(experimentLayout);
		
		for (i = 0; i < row; i++) {
			for (j = 0; j < col; j++) {
				mtfields[i][j] = new TemplateField();
				mtfields[i][j].setBounds(200 + (j*40), 200 + (i*40), 30, 30);
				
				if (row >= 3 && col >= 3) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
				}
				else if (row >= 2 && col >= 2) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
				}
				else if (row >= 1 && col >= 1) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));
				}
				newjp.add(mtfields[i][j]);
				mspl.contents[i][j] = 0;
			}
		}
		
		nextButton2 = new TemplateButton();
		nextButton2.addActionListener(this);
		nextButton2.setBounds(290,550, 180,40);
		nextButton2.setText("Next");
		nextButton2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
		nextButton2.addActionListener(e -> this.dispose());
		
		this.add(newjp);
		this.add(nextButton2);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		if (e.getSource()==nextButton2) {
			int r, c;
			r = mtfields.length;
			c = mtfields[0].length;
			for (i = 0; i < r; i++) {
				for (j = 0; j < c; j++) {
					try {
						String s = mtfields[i][j].getText();
						num = Double.parseDouble(s);
						mspl.contents[i][j] = num;
					}
					catch (NumberFormatException n) {
						mspl.contents[i][j] = 0;
						flag = true;
					}
				}
			}
			
			decoy = mspl;
			
			if (flag) {
				new ErrorPage("'I don't think your inputs were correct', says Misaka");
			}
			else {
				decoy.reduceToEchelon();
				decoy.echelonToReducedEchelon();
				if (decoy.getAugmentedSols() == "The System of Linear Equations has no solutions") {
					new SPL4(null, true, null);
				}
				else {
					new SPL3(mspl);
				}

			}
		}
	}
}