package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tubes.matrix.src.Matrix;
import tubes.problems.MultivariateLinearRegressionSolver;

public class LinearReg2 extends TemplateFrame implements ActionListener {
	
	int i, j;
	double num;
	NextTemplateButton next;
	public TemplateField mtfields[][], mtfields2[];
	int regr, sampl;
	TemplateField inputx;
	
	LinearReg2(int sample, int regressor) {
		this.setTitle("AldyDPP Matrix Calculator -> Multivariable Linear Regression");
		
		mtfields = new TemplateField[sample][regressor+1];
		mtfields2 = new TemplateField[regressor];
		regr = regressor;
		sampl = sample;
		
		HeaderText inputtext = new HeaderText("Input all samples and absides!");
		this.add(inputtext);
		
		JPanel newjp = new JPanel();
		newjp.setOpaque(false);
		newjp.setBounds(230,180, 300, 300);
		newjp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
		
		GridLayout experimentLayout = new GridLayout(sample,regressor+1);
		newjp.setLayout(experimentLayout);
		
		for (i = 0; i < sample; i++) {
			for (j = 0; j < regressor + 1; j++) {
				
				mtfields[i][j] = new TemplateField();
				mtfields[i][j].setBounds(200 + (j*40), 200 + (i*40), 30, 30);
				
				if (regressor >= 4 || sample >= 4) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
				}
				else if (regressor >= 2 || sample >= 2) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
				}
				else if (regressor >= 1 || sample >= 1) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));
				}
				newjp.add(mtfields[i][j]);
			}
		}
		
		// input absides
		JPanel newjp2 = new JPanel();
		newjp2.setOpaque(false);
		newjp2.setBounds(230,500, 300, 35);
		GridLayout experimentLayout2 = new GridLayout(1, regressor);
		newjp2.setLayout(experimentLayout2);
		
		for (int k = 0; k < regressor; k++) {
			mtfields2[k] = new TemplateField();
			newjp2.add(mtfields2[k]);
		}
		
		this.add(newjp2);
		
		/* Next */
		next = new NextTemplateButton();
		next.setBounds(290,600, 180,40);
		next.addActionListener(this);
		next.addActionListener(e -> this.dispose());
		
		this.add(newjp);
		this.add(next);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		double x;
		Matrix m1 = new Matrix();
		m1.initializeMatrix(regr+1, 1);
		
		
		if (e.getSource()==next) {
			MultivariateLinearRegressionSolver MLRS = new MultivariateLinearRegressionSolver();
			MLRS.xyMatrix.initializeMatrix(sampl, regr + 1);
			try {
				for (i = 0; i < sampl; i++) {
					for (j = 0; j < regr + 1; j++) {
						MLRS.xyMatrix.contents[i][j] = Double.parseDouble(mtfields[i][j].getText());
					}
				}
				
				m1.contents[0][0] = 1;
				for (j = 1; j < regr + 1; j++) {
					m1.contents[j][0] = Double.parseDouble(mtfields2[j-1].getText());
				}
				
				MLRS.n = regr + 1; 
				MLRS.m = sampl;
				
				
				
				new LinearReg3(MLRS, m1);
			}
			
			catch (NumberFormatException n) {
				new ErrorPage("'Please input correctly next time', Misaka advises");
			}
		}
		
	}
}