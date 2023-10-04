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

public class Inverse1 extends TemplateFrame implements ActionListener {
	
	public int size;
	TemplateButton next, txtbutton;
	TemplateField jtf = new TemplateField();
	Scanner sc = new Scanner(System.in);
	HeaderText inputtext;
	Inverse1() {
		
		this.setTitle("AldyDPP Matrix Calculator -> Inverse");
		
		/*Input text*/
		inputtext = new HeaderText("Input Matrix Size");
		this.add(inputtext);
		
		/*Input size*/
		jtf = new TemplateField();
		
		/*txt button*/
		txtbutton = new TemplateButton();
		txtbutton.setBounds(140, 350, 500,40);
		txtbutton.setText("Input matrix with txt file instead");
		txtbutton.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
		txtbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));;
		txtbutton.setFocusable(false);
		txtbutton.addActionListener(this);
		txtbutton.addActionListener(e->this.dispose());
		
		/*next button*/
		next = new NextTemplateButton();
		next.addActionListener(e->this.dispose());
		next.addActionListener(this);
		
		this.add(txtbutton);
		this.add(next);
		this.add(jtf);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==next) {
			String s = jtf.getText();
			try {
				size = Integer.parseInt(s);
				
				if (size < 1) {
					this.dispose();
					new ErrorPage("'I'm going to need a matrix bigger than that', requests Misaka");
				}
				else {
					new Inverse2(size);
				}
			}
			catch (NumberFormatException n) {
				new ErrorPage("I don't think your inputs were correct");
			}
		}
		
		else if (e.getSource()==txtbutton) {
			new TxtInputPage("inv");
		}
	}
}