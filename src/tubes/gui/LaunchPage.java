package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class LaunchPage extends TemplateFrame implements ActionListener {
	
	TemplateButton OptionButtons[] = new TemplateButton[8];
	LaunchPage() {
		/* Welcome Text */
		JLabel welcometext = new JLabel();
		ImageIcon welcometextimg = new ImageIcon(this.parent + "welcometext.png");
		
		welcometext.setVerticalAlignment(JLabel.CENTER);
		welcometext.setHorizontalAlignment(JLabel.CENTER);
		welcometext.setIcon(welcometextimg);
		// welcometext.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		
		welcometext.setBounds(80, 50, 600, 100);
		welcometext.setVerticalAlignment(JLabel.CENTER);
		welcometext.setHorizontalAlignment(JLabel.CENTER);
		
		welcometext.setOpaque(false);
		welcometext.setForeground(Color.WHITE);
		welcometext.setBackground(new Color(180, 180, 180));
		welcometext.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(6)));
		this.add(welcometext);
		
		
		/* Nav Buttons */
		int i;
		for (i = 0; i < 8; i ++) {
			OptionButtons[i] = new TemplateButton();
			OptionButtons[i].setBounds(217, 175 + (i*60), 330, 40);
			// OptionButtons[i].setBackground(new Color(0,0,0,50));
			
			OptionButtons[i].addActionListener(e -> this.dispose());
			OptionButtons[i].addActionListener(this);
			OptionButtons[i].setFont(new Font(Font.DIALOG_INPUT, Font.ITALIC, 17));
			
			this.add(OptionButtons[i]);

		}
		
		OptionButtons[0].setText("Linier Equation");
		OptionButtons[1].setText("Determinant");
		OptionButtons[2].setText("Inverse Matrix");
		OptionButtons[3].setText("Polinomial Interpolation");
		OptionButtons[4].setText("Bicubic Spline Interpolation");
		OptionButtons[5].setText("Multiple Linear Regression");
		OptionButtons[6].setText("BONUS");
		OptionButtons[7].setText("Exit");
		/*BG IMG*/
		this.add(bg);
		
		this.revalidate(); // Ensure proper layout
        this.repaint();    // Trigger a repaint
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==OptionButtons[0]) {
			new SPL1();
		}
		else if (e.getSource()==OptionButtons[1]) {
			new Determinant1();
		}
		else if (e.getSource()==OptionButtons[2]) {
			new Inverse1();
		}
		else if (e.getSource()==OptionButtons[3]) {
			new PolinomeInterpolation1();
		}
		else if (e.getSource()==OptionButtons[4]) {
			new BicubicSpline1();
		}
		else if (e.getSource()==OptionButtons[5]) {
			new LinearReg1();
		}
		else if (e.getSource()==OptionButtons[6]) {
			new BONUS();
		}
	}
}