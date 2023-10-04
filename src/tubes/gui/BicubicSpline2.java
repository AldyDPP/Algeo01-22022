package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class BicubicSpline2 extends TemplateFrame implements ActionListener {
	TemplateButton exit, back, maketxt;
	String outputstr;
	BicubicSpline2(double res, double x, double y) {
		this.setTitle("AldyDPP Matrix Calculator -> Bicubic Spline");
		
		/*header Text*/
		HeaderText resulttext = new HeaderText("Approximation for f(" + x + ", " + y  + ")");
		this.add(resulttext);
		
		/* Result */
		String s = String.format("%.4f", res);
		outputstr = s;
		JLabel newjl = new JLabel();
		newjl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));
		this.add(newjl);
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
		
		this.add(exit);
		this.add(back);
		
		/* Create txt output file button */
		maketxt = new ExitTemplateButton();
		maketxt.setBounds(10,600, 200,40);
		maketxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		maketxt.addActionListener(e->this.dispose());
		maketxt.addActionListener(this);
		maketxt.setText("Convert result to txt file");
		maketxt.setForeground(new Color(130, 130, 130));
		
		this.add(maketxt);
		
		//bg img
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==back) {
			new LaunchPage();
		}
		else if (e.getSource()==maketxt) {
			new TxtOutputPage(outputstr);
		}
	}
}