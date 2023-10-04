package tubes.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TxtOutputPage extends TemplateFrame implements ActionListener {
	
	String url = "";
	TemplateField urlinput;
	NextTemplateButton submit;
	String contentsString;
	
	TxtOutputPage(String text) {
		// header text
		HeaderText heder = new HeaderText("Input Output File Name!");
		this.add(heder);
		
		// Input field and submit button
		urlinput = new TemplateField();
		urlinput.setBounds(80,200,600,60);
		this.add(urlinput);
		
		submit = new NextTemplateButton();
		submit.setText("Write File");
		submit.addActionListener(this);
		submit.addActionListener(e->this.dispose());
		this.add(urlinput);
		this.add(submit);
		
		// move input text to contentsString
		contentsString = text;
		
		// bg img
		this.add(bg);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==submit) {
			String parentpath = "res\\"; // CHANGE THIS
			
			String url = urlinput.getText();
	        File fcheck = new File(parentpath);
	        if (!fcheck.exists()) {fcheck.mkdir();}
	        
	        FileWriter fwrite;
	        File f = new File(parentpath + url);
			try {
				// try writing the file
				f.createNewFile();
				fwrite = new FileWriter(parentpath + url);
	            fwrite.write(contentsString);
	            fwrite.close();
	            new TxtOutputPage2();
	            
	        } catch (IOException exc) {
	        	exc.printStackTrace();
	            new ErrorPage("'I couldn't find that directory', says Misaka");
	        }
			
		}
	}
}