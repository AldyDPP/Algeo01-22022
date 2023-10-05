package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Determinant4 extends Determinant1 implements ActionListener {
	TemplateButton exit, back, maketxt;
	String outputstr;
	
	Determinant4(double det, boolean flag) {
		this.setTitle("AldyDPP Matrix Calculator -> Determinant");
		
		this.remove(jtf);
		this.remove(next);
		this.remove(txtbutton);
		inputtext.setText("Your Determinant is: ");
		
		JLabel newjl = new JLabel(); String s;
		JLabel newjl2 = new JLabel(); String s2;
			
		if (det == 0) {
			JLabel wow = new JLabel("'This matrix doesn't have an inverse', says Misaka.");
			wow.setForeground(new Color(255,255,255));
			wow.setHorizontalAlignment(JLabel.CENTER);
			wow.setHorizontalTextPosition(JLabel.CENTER);
			wow.setBounds(0, 250, 800,300);
			this.add(wow);
			
			JLabel misaka = new JLabel();
			String parent = "images\\";
			ImageIcon misakaimg = new ImageIcon(parent + "misaka.png");
			
			misaka.setIcon(misakaimg);
			misaka.setBounds(190,200,400,400);
			this.add(misaka);
		}

		s = det+"";
		outputstr = s;
		newjl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));	
		
		newjl.setText(s);
		newjl.setBounds(-10,180,800,300);
		newjl.setOpaque(false);
		newjl.setForeground(new Color(255,255,255));
		newjl.setHorizontalTextPosition(JLabel.CENTER);
		newjl.setHorizontalAlignment(JLabel.CENTER);
		
		/*Exit and back home buttons*/
		exit = new ExitTemplateButton();
		exit.setBounds(290,550, 180,40);
		exit.addActionListener(e->this.dispose());
		
		back = new ExitTemplateButton();
		back.setText("Back to Home");
		back.addActionListener(e->this.dispose());
		back.addActionListener(this);
		
		/* Create txt output file button */
		maketxt = new ExitTemplateButton();
		maketxt.setBounds(10,600, 200,40);
		maketxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		maketxt.addActionListener(e->this.dispose());
		maketxt.addActionListener(this);
		maketxt.setText("Convert result to txt file");
		maketxt.setForeground(new Color(130, 130, 130));
		
		if (!flag) {
			this.add(maketxt);
		}
		this.add(newjl);
		this.add(exit);
		this.add(back);
		
		// bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new LaunchPage();
		}
		else if (e.getSource()==maketxt) {
			new TxtOutputPage(outputstr);
		}
	}
}