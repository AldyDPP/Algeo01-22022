package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class SPL1 extends Determinant1 implements ActionListener {
	TemplateField jtf2;
	JLabel multsymbol;

	SPL1() {
		this.setTitle("AldyDPP Matrix Calculator -> Linear Equation Solver");
		jtf2 = new TemplateField();
		multsymbol = new JLabel();
		
		jtf.setBounds(300,240,50,50);
		jtf2.setBounds(400,240,50,50);
		
		multsymbol.setText("X");
		multsymbol.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		multsymbol.setHorizontalTextPosition(JLabel.CENTER);
		multsymbol.setHorizontalAlignment(JLabel.CENTER);
		multsymbol.setVerticalTextPosition(JLabel.CENTER);
		multsymbol.setForeground(Color.WHITE);
		multsymbol.setBounds(350,240,50,50);
		
		this.add(multsymbol);
		this.add(jtf2);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		int size, size2;
		if (e.getSource()==next) {
			String s = jtf.getText();
			String s2 = jtf2.getText();
			try {
				size = Integer.parseInt(s);
				size2 = Integer.parseInt(s2);
				
				if (size < 1 || size2 < 1) {
					this.dispose();
					new ErrorPage("'That matrix is too small...', says Misaka");
				}
				else {
					new SPL2(size, size2);
				}
			}
			catch (NumberFormatException n) {
				new ErrorPage("'Something something, wrong input', says Misaka");
			}
		}
		
		else if (e.getSource()==txtbutton) {
			new TxtInputPage("spl");
		}
	}
}