package tubes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ErrorPage extends TemplateFrame implements ActionListener {
	TemplateButton exit, back, maketxt;
	String outputstr;
	
	ErrorPage(String errormsg) {
		this.setTitle("AldyDPP Matrix Calculator -> Error");
		
		HeaderText heder = new HeaderText("Whoops!");
		this.add(heder);
		
		JLabel newjl = new JLabel(); String s;
		JLabel newjl2 = new JLabel(); String s2;
		
		this.setTitle("AldyDPP Matrix Calculator -> ???");
		s = "Uh Oh! Something went wrong!";

		if (errormsg != "" && errormsg != "default") {
			JLabel wow = new JLabel(errormsg);
			wow.setForeground(new Color(255,255,255));
			wow.setHorizontalAlignment(JLabel.CENTER);
			wow.setHorizontalTextPosition(JLabel.CENTER);
			wow.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			wow.setBounds(0, 250, 800,300);
			this.add(wow);
			
			JLabel misaka = new JLabel();
			String parent = "images\\";
			ImageIcon misakaimg = new ImageIcon(parent + "misaka.png");
			
			misaka.setIcon(misakaimg);
			misaka.setBounds(350,150,400,400);
			this.add(misaka);		
		}

		
		newjl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 35));
		newjl.setText(s);
		newjl.setBounds(-10,130,800,300);
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