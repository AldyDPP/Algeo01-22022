package tubes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import tubes.matrix.src.SquareMatrix;

public class Determinant3 extends TemplateFrame implements ActionListener {
	
	SquareMatrix m = new SquareMatrix();
	int size;
	TemplateButton echelonrowop, cofactor;
	Determinant3(SquareMatrix m2) {
		this.setTitle("AldyDPP Matrix Calculator -> Determinant");
		
		size = m2.rowCount;
		m = m2;
		HeaderText heder = new HeaderText("Pick Calculation Method!");
		this.add(heder);
		
		echelonrowop = new TemplateButton();
		cofactor = new TemplateButton();
		
		echelonrowop.setBounds(230, 250, 300, 75);
		echelonrowop.setText("Echelon Reduction");
		echelonrowop.addActionListener(this);
		echelonrowop.addActionListener(e->this.dispose());
		
		cofactor.setBounds(230, 350, 300, 75);
		cofactor.setText("Cofactor");
		cofactor.addActionListener(this);
		cofactor.addActionListener(e->this.dispose());
		
		this.add(cofactor);
		this.add(echelonrowop);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource()==echelonrowop) {
				double det = m.determinantByERO();
				new Determinant4(det, false);
			}
			else if (e.getSource()==cofactor) {
				double det = m.determinantByCofactor();
				new Determinant4(det, false);
			}
		}
		
		catch (ArrayIndexOutOfBoundsException exc) {
			new ErrorPage("'Try checking your txt file', says Misaka'");
		}
	}
}