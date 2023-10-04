package tubes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import tubes.bonus.Bonus;
import tubes.bonus.BonusRGB;

public class BONUS3 extends TemplateFrame implements ActionListener {
	
	ExitTemplateButton back, exit;
	BONUS3() {
		this.setTitle("AldyDPP Matrix Calculator -> Image Enlarger");
		
		HeaderText heder = new HeaderText("Image successfully generated!");
		
		/*Exit and back home buttons*/
		exit = new ExitTemplateButton();
		exit.setBounds(290,550, 180,40);
		exit.addActionListener(e->this.dispose());
		
		back = new ExitTemplateButton();
		back.setText("Back to Home");
		back.addActionListener(e->this.dispose());
		back.addActionListener(this);
		
		this.add(back);
		this.add(exit);
		this.add(heder);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new LaunchPage();
		}
	}
}