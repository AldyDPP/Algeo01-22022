package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BONUS extends TemplateFrame implements ActionListener {
	
	NextTemplateButton next;
	String inputurl, outputurl;
	TemplateField infield, outfield, zffield;
	double zoomfactor;
	BONUS() {
		this.setTitle("AldyDPP Matrix Calculator -> Image Enlarger");
		
		// I'm not even going to bother commenting any of this...
		HeaderText heder = new HeaderText("Input Stuff!");
		this.add(heder);
		
		infield = new TemplateField();
		outfield = new TemplateField();
		zffield = new TemplateField();
		
		JLabel inlabel = new JLabel("Input file path:"); inlabel.setBounds(30,200,400,50); inlabel.setForeground(Color.WHITE);
		JLabel outlabel = new JLabel("Output file path:"); outlabel.setBounds(30,300,400,50); outlabel.setForeground(Color.WHITE);
		JLabel zflabel = new JLabel("Zoom factor:"); zflabel.setBounds(30,400,400,50); zflabel.setForeground(Color.WHITE);
		
		inlabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		outlabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		zflabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		
		this.add(inlabel);
		this.add(outlabel);
		this.add(zflabel);
		
		infield.setBounds(270,200,400,50);
		outfield.setBounds(270,300,400,50);
		zffield.setBounds(270,400,50,50);
		
		infield.setHorizontalAlignment(JTextField.RIGHT);
		outfield.setHorizontalAlignment(JTextField.RIGHT);
		zffield.setHorizontalAlignment(JTextField.RIGHT);
		
		this.add(infield);
		this.add(outfield);
		this.add(zffield);
		
		/* Next */
		next = new NextTemplateButton();
		next.setBounds(290,600, 180,40);
		next.addActionListener(this);
		next.addActionListener(e -> this.dispose());
		this.add(next);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==next) {
			try {
				inputurl = infield.getText();
				outputurl = outfield.getText();
				zoomfactor = Double.parseDouble(zffield.getText());
				new BONUS2(inputurl, outputurl, zoomfactor);
				
			}
			catch (NumberFormatException exc) {
				new ErrorPage("'I think one of your inputs were wrong...'");
			}
		}
	}
}