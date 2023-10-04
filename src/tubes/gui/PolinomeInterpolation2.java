package tubes.gui;
import tubes.problems.InterpolationSolver;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tubes.problems.InterpolationSolver;

public class PolinomeInterpolation2 extends TemplateFrame implements ActionListener {
	
	InterpolationSolver interp = new InterpolationSolver();
	NextTemplateButton next;
	TemplateField inputx;
	TemplateField[] InputFields;
	int amnt;
	PolinomeInterpolation2(int amount) {
		this.setTitle("AldyDPP Matrix Calculator -> Polinome Interpolation");
		
		amnt = amount;
		
		/*Header text */
		HeaderText inputtext = new HeaderText("Input points and input absis!");
		this.add(inputtext);
		
		/*next button*/
		next = new NextTemplateButton();
		next.addActionListener(this);
		next.addActionListener(e -> this.dispose());
		this.add(next);
		
		/*Points' Inputting */
		GridLayout experimentLayout, experimentLayout2;
		JPanel grid1 = new JPanel();
		JPanel grid2 = new JPanel();
		JLabel boxX = new JLabel("X");
		JLabel boxY = new JLabel("Y");
		boxX.setForeground(Color.WHITE);
		boxY.setForeground(Color.WHITE);
		boxX.setHorizontalTextPosition(JLabel.CENTER);
		boxY.setHorizontalTextPosition(JLabel.CENTER);
		boxX.setHorizontalAlignment(JLabel.CENTER);
		boxY.setHorizontalAlignment(JLabel.CENTER);
		JLabel boxX2 = new JLabel("X");
		JLabel boxY2 = new JLabel("Y");
		boxX2.setForeground(Color.WHITE);
		boxY2.setForeground(Color.WHITE);
		boxX2.setHorizontalTextPosition(JLabel.CENTER);
		boxY2.setHorizontalTextPosition(JLabel.CENTER);
		boxX2.setHorizontalAlignment(JLabel.CENTER);
		boxY2.setHorizontalAlignment(JLabel.CENTER);
		double[] inpX = new double[amount];
		double[] inpY = new double[amount];
		InputFields = new TemplateField[amount*2];
		
		if (amount <= 7) {
			experimentLayout = new GridLayout(amount+1,2);
		}
		else {
			experimentLayout = new GridLayout(8,2);
			experimentLayout2 = new GridLayout(amount-7+1, 2);
			grid2.setLayout(experimentLayout2);
		}
		
		
		grid1.setLayout(experimentLayout);
		grid1.add(boxX);
		grid1.add(boxY);
		
		for (int i = 0; i < (amount*2); i ++) {
			if (i > 13) {
				
				if (i == 14) {
					grid2.add(boxX2);
					grid2.add(boxY2);
				}
				InputFields[i] = new TemplateField();
				grid2.add(InputFields[i]);
				
			}
			else {
				InputFields[i] = new TemplateField();
				grid1.add(InputFields[i]);	
			}
		}
		this.add(grid1);
		grid1.setBounds(325,160,100,amount*50);
		grid1.setOpaque(false);
		grid1.setForeground(Color.WHITE);
		
		if (amount > 7) {
			this.add(grid2);
			grid1.setBounds(220,170,100,350);
			grid2.setBounds(420,170,100,(amount-7)*50);
			grid2.setOpaque(false);
			grid2.setForeground(Color.WHITE);
		}
		
		
		/* X input */
		inputx = new TemplateField();
		inputx.setBounds(350, 515, 50, 30);
		this.add(inputx);
		
		// bg img
		this.add(bg);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next) {
			int i;
			int kx = 0;
			int ky = 0;
			
			interp.xyValue = new double[amnt][2];
			interp.degree = amnt - 1;
			try {
				for (i = 0; i < amnt*2; i++) {
					String s = InputFields[i].getText();
					double num = Integer.parseInt(s);
					
					if (i % 2 == 0) {
						interp.xyValue[kx][0] = num; kx ++; // input x
					}
					else {
						interp.xyValue[ky][1] = num; ky ++;// input y
					}
					
				}
				String s = inputx.getText();
				double num = Double.parseDouble(s);
				new PolinomeInterpolation3(interp, num);
				
			}
			catch (NumberFormatException n) {
				new ErrorPage("'What's important is that you tried your best', Misaka reassures you");
			}
		}
	}
}