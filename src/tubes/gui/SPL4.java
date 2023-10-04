package tubes.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import tubes.matrix.src.SolutionType;

public class SPL4 extends TemplateFrame implements ActionListener {
	
	TemplateButton exit, back, maketxt;
	String outputstr;
	JLabel[] solstext;
	SPL4(SolutionType[] sols, boolean flag, String solsstr) {
		this.setTitle("AldyDPP Matrix Calculator -> Linear Equation Solver");
		
		if (!flag) {
			outputstr = "";
			solstext = new JLabel[sols.length];
			for (int i=0; i<sols.length - 1; i++) {
				
				outputstr += "x" + (i+1) + " = " + sols[i].toString() + "\n";
				
				solstext[i] = new JLabel();
				if (i < 6) {
					solstext[i].setBounds(100, 200 + (i*50), 1000, 50);					
				}
				else {
					solstext[i].setBounds(420, - 100+ (i*50), 1000, 50);	
				}
				solstext[i].setText("x" + (i+1) + " = " + sols[i].toString());
				solstext[i].setHorizontalTextPosition(JLabel.LEFT);
				solstext[i].setHorizontalAlignment(JLabel.LEFT);
				solstext[i].setForeground(Color.WHITE);
				solstext[i].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
				this.add(solstext[i]);
				// solstext[i].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(6)));
			}
		}
		
		else {
			JLabel newjl = new JLabel("There are no solutions to this equation!");
			newjl.setBounds(-100,180,1000,300);
			newjl.setOpaque(false);
			newjl.setForeground(new Color(255,255,255));
			newjl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));	
			newjl.setHorizontalTextPosition(JLabel.CENTER);
			newjl.setHorizontalAlignment(JLabel.CENTER);
			this.add(newjl);
		}
		
		
		HeaderText ResultText = new HeaderText("Solutions: ");
		this.add(ResultText);
		
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
		
		this.add(maketxt);
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