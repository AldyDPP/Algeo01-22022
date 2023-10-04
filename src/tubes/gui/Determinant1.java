package tubes.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Determinant1 extends TemplateFrame implements ActionListener {
	
	
	public int size;
	TemplateButton next, txtbutton;
	TemplateField jtf = new TemplateField();
	Scanner sc = new Scanner(System.in);
	JLabel inputtext = new JLabel();
	
	Determinant1() {
		this.setTitle("AldyDPP Matrix Calculator -> Determinant");
		//m.keyboardInputMatrix(sc);
		//m.printMatrix();
		
		inputtext = new HeaderText("Input Matrix Size!");
		this.add(inputtext);
		
		jtf = new TemplateField();
		
		txtbutton = new NextTemplateButton();
		txtbutton.setBounds(140, 350, 500, 40);
		txtbutton.setText("Input matrix with txt file instead");
		txtbutton.addActionListener(this);
		txtbutton.addActionListener(e->this.dispose());
		
		next = new NextTemplateButton();
		next.addActionListener(e->this.dispose());
		next.addActionListener(this);
		
		this.add(txtbutton);
		this.add(next);
		this.add(jtf);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==next) {
			String s = jtf.getText();
			try {
				size = Integer.parseInt(s);
				
				if (size < 1) {
					this.dispose();
					new Determinant4(0, true);
				}
				else {
					Determinant2 d2 = new Determinant2(size);
				}
			}
			catch (NumberFormatException n) {
				new ErrorPage("I don't think your inputs were correct");
			}
		}
		
		else if (e.getSource()==txtbutton) {
			new TxtInputPage("det");
		}
	}
}