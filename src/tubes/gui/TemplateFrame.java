package tubes.gui;
import java.awt.Color;
import javax.swing.*;

import tubes.matrix.src.SPLMatrix;

public class TemplateFrame extends JFrame {
	/* Frame setup */
	protected String parent = "images\\";
	JLabel bg;
	ImageIcon bgimg;
	TemplateFrame() {
		
		this.setVisible(true);
		this.setSize(780, 700);
		this.setTitle("AldyDPP Matrix Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false); //ganti false ntaran
		
		/* Logo and color */
		Color color1 = new Color(0,10,0);
		this.setForeground(color1);
		this.getContentPane().setBackground(color1);
		
		ImageIcon logo = new ImageIcon(this.parent + "misaka.png"); // change this
		this.setIconImage(logo.getImage());
		
		/*bg img*/
		bg = new JLabel();
		bgimg = new ImageIcon(this.parent + "aldydpp.jpg");
		bg.setIcon(bgimg);
		bg.setLayout(null);
		bg.setBounds(this.getBounds());
		
		bg.setVerticalAlignment(JLabel.TOP);
		bg.setHorizontalAlignment(JLabel.CENTER);
		
		this.revalidate(); // Ensure proper layout
        this.repaint();    // Trigger a repaint
	}
}