package tubes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TxtOutputPage2 extends TemplateFrame implements ActionListener {
	
	ExitTemplateButton exit, back;
	TxtOutputPage2() {
		// heder text
		HeaderText heder = new HeaderText("File Successfully Written!");
		this.add(heder);
		
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
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==back) {
			new LaunchPage();
		}
	}
}