package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TemplateField extends JTextField {
	TemplateField() {
		this.setVisible(true);
		this.setBounds(353, 300, 50, 30);
		this.setOpaque(false);
		this.setForeground(new Color(255,255,255));
		this.setLayout(null);
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
		this.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		this.setHorizontalAlignment(JTextField.CENTER);
	}
}