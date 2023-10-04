package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tubes.matrix.src.SquareMatrix;

public class Inverse2 extends Inverse1 implements ActionListener {
	
	int i, j;
	double num;
	//int size = DeterminantWindow.getUkuran();
	TemplateButton nextButton2;
	public TemplateField mtfields[][];
	public SquareMatrix m = new SquareMatrix();
	
	Inverse2(int size) {
		this.setTitle("AldyDPP Matrix Calculator -> Inverse");
		
		mtfields = new TemplateField[size][size];
		m.initializeMatrix(size, size);
		this.remove(jtf);
		this.remove(next);
		this.remove(txtbutton);
		inputtext.setText("Input Matrix!");
		JPanel newjp = new JPanel();
		newjp.setOpaque(false);
		newjp.setBounds(230,180, 300, 300);
		newjp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
		GridLayout experimentLayout = new GridLayout(size,size);
		newjp.setLayout(experimentLayout);
		
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				mtfields[i][j] = new TemplateField();
				mtfields[i][j].setBounds(200 + (j*40), 200 + (i*40), 30, 30);
				
				if (size == 2) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
				}
				else if (size == 3) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
				}
				else if (size == 1) {
					mtfields[i][j].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));
				}
				newjp.add(mtfields[i][j]);
				m.contents[i][j] = 0;
			}
		}
		
		nextButton2 = new NextTemplateButton();
		nextButton2.addActionListener(this);
		nextButton2.addActionListener(e -> this.dispose());

		this.add(nextButton2);
		this.add(newjp);
		
		// bg img
		this.add(bg);
		this.revalidate();
        this.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		if (e.getSource()==nextButton2) {
			int r, c;
			r = mtfields.length;
			c = mtfields[0].length;
			for (i = 0; i < r; i++) {
				for (j = 0; j < c; j++) {
					try {
						String s = mtfields[i][j].getText();
						num = Double.parseDouble(s);
						m.contents[i][j] = num;
					}
					catch (NumberFormatException n) {
						m.contents[i][j] = 0;
						flag = true;
					}
				}
			}
			
			if (flag) {
				new ErrorPage("I don't think your inputs were correct");
			}
			else if (m.determinantByCofactor() == 0) {
				new Determinant4(0,false);
			}
			else {
				
				new Inverse3(m);
			}
		}
	}
}