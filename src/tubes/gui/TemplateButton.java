package tubes.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class TemplateButton extends JButton {
	TemplateButton() {
		this.setText("Change this!");
		this.setVerticalAlignment(JButton.CENTER);
		this.setHorizontalAlignment(JButton.CENTER);
		this.setFocusable(true);
		this.setOpaque(false);
		this.setForeground(new Color(255,255,255));
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
		this.setBounds(217, 175, 330, 40);
		this.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	}
}