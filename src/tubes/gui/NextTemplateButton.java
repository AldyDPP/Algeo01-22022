package tubes.gui;

import java.awt.Font;

public class NextTemplateButton extends TemplateButton {
	NextTemplateButton() {
		this.setText("Next");
		this.setBounds(290,550, 180,40);
		this.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
	}
}