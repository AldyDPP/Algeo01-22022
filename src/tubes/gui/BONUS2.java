package tubes.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import tubes.bonus.Bonus;
import tubes.bonus.BonusRGB;

public class BONUS2 extends TemplateFrame implements ActionListener{
	String inpurl, outurl;
	double zf;
	TemplateButton grayscale, rgb;
	BONUS2(String inpurl_, String outurl_, double zf_) {
		this.setTitle("AldyDPP Matrix Calculator -> Image Enlarger");
		
		inpurl = inpurl_; outurl = outurl_; zf = zf_;
		HeaderText heder = new HeaderText("Pick ZoOm type!");
		
		this.add(heder);
		
		grayscale = new TemplateButton();
		rgb = new TemplateButton();
		
		grayscale.setBounds(200, 200, 150, 75);
		grayscale.setText("GrayScale");
		grayscale.addActionListener(this);
		grayscale.addActionListener(e->this.dispose());
		
		rgb.setBounds(400, 200, 150, 75);
		rgb.setText("RGB");
		rgb.addActionListener(this);
		rgb.addActionListener(e->this.dispose());
		
		this.add(rgb);
		this.add(grayscale);
		
		this.add(bg);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource()==grayscale) {
				Bonus b = new Bonus();
				b.getImageAndZoom(inpurl, zf, outurl);
				new BONUS3();
			}
			else if (e.getSource()==rgb) {
				BonusRGB b = new BonusRGB();
				b.getImageAndZoom(inpurl, zf, outurl);
				new BONUS3();
			}
		}
		catch (IOException exc) {
			new ErrorPage("Misaka actually doesn't know what happened here...");
		}
	}
}