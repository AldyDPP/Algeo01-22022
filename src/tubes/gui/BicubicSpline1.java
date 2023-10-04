package tubes.gui;

import java.awt.BasicStroke;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SquareMatrix;
import tubes.problems.BicubicSpline;

public class BicubicSpline1 extends TemplateFrame implements ActionListener {
	
	double num;
	double[] inptarget;
	NextTemplateButton next;
	TemplateField[][] mtfields;
	SquareMatrix tmpm = new SquareMatrix();
	TemplateField targetField0, targetField1;
	TemplateButton txtinputbutton;
	BicubicSpline1() {
		this.setTitle("AldyDPP Matrix Calculator -> Bicubic Spline");
		
		mtfields = new TemplateField[4][4];
		tmpm.initializeMatrix(4, 4);
		
		/* Header text */
		HeaderText inputtext = new HeaderText("Input 4x4 Matrix and X,Y values!");
		this.add(inputtext);
		
		/*Input 4x4 matriks (tmpm) */
		JPanel newjp = new JPanel();
		newjp.setOpaque(false);
		newjp.setBounds(250,180, 250, 250);
		newjp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
		GridLayout experimentLayout = new GridLayout(4,4);
		newjp.setLayout(experimentLayout);
		
		int i, j;
		int k = 0;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				mtfields[i][j] = new TemplateField();
				mtfields[i][j].setBounds(200 + (j*40), 200 + (i*40), 30, 30);
				newjp.add(mtfields[i][j]);
				tmpm.contents[i][j] = 0;
			}
		}
		this.add(newjp);
		
		/*txt input button*/
		txtinputbutton = new NextTemplateButton();
		txtinputbutton.setBounds(230, 520, 300, 60);
		txtinputbutton.addActionListener(this);
		txtinputbutton.addActionListener(e->this.dispose());
		txtinputbutton.setText("Input with txt file instead");
		this.add(txtinputbutton);
		
		
		/*Next button*/
		next = new NextTemplateButton();
		next.setBounds(290,600, 180,40);
		next.addActionListener(this);
		next.addActionListener(e->this.dispose());
		this.add(next);
		
		/*Input 2 nilai target ( target = new double[2]; )*/
		targetField0 = new TemplateField();
		targetField1 = new TemplateField();
		
		targetField0.setBounds(330,450, 40, 40);
		targetField1.setBounds(380,450, 40, 40);
		
		this.add(targetField0);
		this.add(targetField1);
		
		//bg img
		this.add(bg);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		if (e.getSource()==next) {
			int r, c, i, j;
			r = mtfields.length;
			c = mtfields[0].length;
			
			/*Pindah hasil bacaan di ui ke tmpm*/
			for (i = 0; i < r; i++) {
				for (j = 0; j < c; j++) {
					try {
						String s = mtfields[i][j].getText();
						num = Double.parseDouble(s);
						tmpm.contents[i][j] = num;
					}
					catch (NumberFormatException n) {
						tmpm.contents[i][j] = 0;
						flag = true;
					}
				}
			}
			
			/* Pindah tmpm ke Y*/
			BicubicSpline bicSpl = new BicubicSpline();
			bicSpl.Y = new Matrix();
			bicSpl.Y.initializeMatrix(16, 1);
			
			for(i = 0; i < 4; i++){
	            for(j = 0; j < 4; j++){
	                bicSpl.Y.contents[4*i + j][0] = tmpm.contents[i][j];
	            }
	        }
			
			/*Pindah hasil baca gui ke target double*/
			inptarget = new double[2];
			try {
				String s0 = targetField0.getText();
				String s1 = targetField1.getText();
				inptarget[0] = Double.parseDouble(s0);
				inptarget[1] = Double.parseDouble(s1);
				bicSpl.target = new double[2];//ini gk harus 
				bicSpl.target[0] = inptarget[0];//tp drpd takut 
				bicSpl.target[1] = inptarget[1];//ada error ato gmn2
				
				bicSpl.createXMatrix();
		        bicSpl.evaluateA();
				System.out.println(""+bicSpl.calculateVal(inptarget[0],inptarget[1]));
				double res = bicSpl.calculateVal(inptarget[0],inptarget[1]);
				
				new BicubicSpline2(res, inptarget[0], inptarget[1]);
			}
			catch (NumberFormatException n) {
				new ErrorPage("'Maybe you should check your inputs', suggests Misaka");
			}
		}
		
		else if (e.getSource() == txtinputbutton) {
			new TxtInputPage("bcsp");
		}
	}
	
}