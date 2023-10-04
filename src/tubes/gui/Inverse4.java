package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tubes.matrix.src.SquareMatrix;

public class Inverse4 extends TemplateFrame implements ActionListener{
	TemplateButton exit, back, maketxt;
	String outputstr;
	Inverse4 (SquareMatrix m, int size) {
		this.setTitle("AldyDPP Matrix Calculator -> Inverse");
		
		/*Result text*/
		JLabel ResultText = new JLabel();
		ResultText.setVerticalAlignment(JLabel.CENTER);
		ResultText.setHorizontalAlignment(JLabel.CENTER);
		ResultText.setText("Inverse Matrix: ");
		
		
		ResultText.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		
		ResultText.setBounds(80, 50, 600, 100);
		ResultText.setVerticalAlignment(JLabel.CENTER);
		ResultText.setHorizontalAlignment(JLabel.CENTER);
		
		ResultText.setOpaque(false);
		ResultText.setForeground(new Color(255,255,255));
		ResultText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(6)));
		this.add(ResultText);
		
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
		
		/* Matrix Display */
		JLabel[][] elementsdisplay = new JLabel[size][size];
		JPanel newjp = new JPanel();
		newjp.setOpaque(false);
		newjp.setBounds(230,180, 300, 300);
		newjp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
		GridLayout experimentLayout = new GridLayout(size,size);
		newjp.setLayout(experimentLayout);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j ++) {
				elementsdisplay[i][j] = new JLabel(String.format("%.3f", (float)m.contents[i][j]));
				
				if (size == 2) {
					elementsdisplay[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
				}
				else if (size >= 3) {
					elementsdisplay[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
				}
				else if (size == 1) {
					elementsdisplay[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 75));
				}
				
				elementsdisplay[i][j].setHorizontalAlignment(JLabel.CENTER);
				elementsdisplay[i][j].setVerticalAlignment(JLabel.CENTER);
				elementsdisplay[i][j].setForeground(Color.WHITE);
				elementsdisplay[i][j].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
				newjp.add(elementsdisplay[i][j]);
			}
		}
		
		this.add(newjp);
		/* Create txt output file button */
		maketxt = new ExitTemplateButton();
		maketxt.setBounds(10,600, 200,40);
		maketxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		maketxt.addActionListener(e->this.dispose());
		maketxt.addActionListener(this);
		maketxt.setText("Convert result to txt file");
		maketxt.setForeground(new Color(130, 130, 130));
		
		// convert matrix to outputstr
		String res = "";
		for (int railgun = 0; railgun < size; railgun ++) {
			for (int index = 0; index < size; index ++) {
				res += String.format("%.3f", (float)m.contents[railgun][index]);
				if (index != size - 1) {
					res += " ";
				}
			}
			if (railgun != size - 1) {
				res += "\n";
			}
		}
		outputstr = res;
		
		this.add(maketxt);
		
		// bg img
		this.add(bg);
		this.revalidate();
        this.repaint();
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