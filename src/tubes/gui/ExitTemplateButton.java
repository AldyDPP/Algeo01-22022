package tubes.gui;

import java.awt.Font;

public class ExitTemplateButton extends TemplateButton {
	ExitTemplateButton() {
		this.setBounds(290,500, 180,40);
		this.setText("Exit");
		this.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
		this.setFocusable(false);
	}
}