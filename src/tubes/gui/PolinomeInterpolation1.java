package tubes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class PolinomeInterpolation1 extends TemplateFrame implements ActionListener {
	
	NextTemplateButton next;
	NextTemplateButton txtbutton;
	TemplateField inpamount;
	int amount;
	PolinomeInterpolation1() {
		this.setTitle("AldyDPP Matrix Calculator -> Polinome Interpolation");
        
		/*Header text*/
		HeaderText inputtext = new HeaderText("Input amount of points!");
		this.add(inputtext);
		
		/*Text Field*/
		inpamount = new TemplateField();
		this.add(inpamount);
		
		/*TXT Button*/
		txtbutton = new NextTemplateButton();
		txtbutton.setBounds(140, 350, 500, 40);
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
			String s = inpamount.getText();
			
			try {
				amount = Integer.parseInt(s);
				new PolinomeInterpolation2(amount);
			}
			catch (NumberFormatException n) {
				new ErrorPage("'At least one of your inputs were wrong...', Misaka sighs");
			}
		}
		
		else if (e.getSource() == txtbutton) {
			new TxtInputPage("pol");
		}
	}
}