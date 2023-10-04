package tubes.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinearReg1 extends TemplateFrame implements ActionListener {
	
	NextTemplateButton next;
	TemplateField regr, sampl;
	NextTemplateButton txtbutton;
	int regressor, sample;
	int amount;
	LinearReg1() {
		this.setTitle("AldyDPP Matrix Calculator -> Multivariable Linear Regression");
		
		/*Header text*/
		HeaderText inputtext = new HeaderText("Input Sample(L) and Regressor(R)!");
		inputtext.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		this.add(inputtext);
		
		/*Text Field*/
		regr = new TemplateField();
		sampl = new TemplateField();
		
		regr.setBounds(403, 300, 50, 50);
		sampl.setBounds(303, 300, 50, 50);
		
		this.add(regr);
		this.add(sampl);
		
		/* Input TXT */
		txtbutton = new NextTemplateButton();
		txtbutton.setBounds(140, 400, 500, 40);
		txtbutton.setText("Input matrix with txt file instead");
		txtbutton.addActionListener(this);
		txtbutton.addActionListener(e->this.dispose());
		this.add(txtbutton);
		
		/*Next button*/
		next = new NextTemplateButton();
		next.addActionListener(this);
		next.addActionListener(e -> this.dispose());
		this.add(next);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next) {
			try {
				regressor = Integer.parseInt(regr.getText());
				sample = Integer.parseInt(sampl.getText());
				
				if (regressor >= sample) {
					new ErrorPage("'The amount of Samples should be more than regressors', Misaka explains");
				}
				else {
					new LinearReg2(sample, regressor); 
				}
				
			}
			
			catch (NumberFormatException n) {
				new ErrorPage("'You should check your inputs', says Misaka");
			}
		}
		
		else if (e.getSource() == txtbutton) {
			new TxtInputPage("reg");
		}
	}
}