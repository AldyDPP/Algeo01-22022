package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HeaderText extends JLabel {
	HeaderText(String s) {
		this.setVerticalAlignment(JLabel.CENTER);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setText(s);
		this.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		this.setBounds(80, 50, 600, 100);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setOpaque(false);
		this.setForeground(new Color(255,255,255));
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(6)));
	}
}