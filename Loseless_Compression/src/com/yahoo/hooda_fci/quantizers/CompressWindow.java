package com.yahoo.hooda_fci.quantizers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class CompressWindow extends JFrame{
	
	private JButton compressB ;
	private JButton browseB ;
	private JRadioButton uniformRB;
	private JRadioButton optimalRB;
	private JRadioButton vectorRB;
	private JFileChooser chooseInputFile;
	private JFileChooser chooseOutputFile;
	private ButtonGroup bg;
	private File sourceFile;
	private File destinationFile;
	
	private JLabel label;
	private JLabel quantizerlabel;
	private JTextField quantizerTF;
	
	String tech="Uniform Quantizer";
	CompressWindow()
	{
		super("Compression window");
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//setResizable(false);
		setBounds(100, 100, 455, 180);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		// Select Files
		
		
		// Group button
		bg= new ButtonGroup();
		
		// Radio Buttons
		uniformRB = new JRadioButton("Uniform Quantizer");
		uniformRB.setSelected(true);
		uniformRB.setBounds(6, 17, 129, 23);
		
		optimalRB = new JRadioButton("Optimal Quantizer");
		optimalRB.setBounds(158, 17, 129, 23);
		
		vectorRB = new JRadioButton("Vector Quantizer");
		vectorRB.setBounds(305, 17, 129, 23);
		
		// Compress Button
		compressB = new JButton("Compress");
		compressB.setFont(new Font("Tahoma", Font.BOLD, 12));
		compressB.setBounds(158, 66, 101, 23);
		
		//browse
		browseB = new JButton("Browse");
		browseB.setFont(new Font("Tahoma", Font.BOLD, 12));
		browseB.setBounds(305, 66, 101, 23);
		
		//label
		label = new JLabel("Uniform Quantizer");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(135, 66+23+10, 181, 23);
		
		quantizerTF = new JTextField("3");
		quantizerTF.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantizerTF.setBounds(28, 66, 50, 23);
		
		quantizerlabel = new JLabel("Enter Quantizer");
		quantizerlabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		quantizerlabel.setBounds(6, 43, 181, 23);
		
		// add to group button
		bg.add(uniformRB);
		bg.add(optimalRB);
		bg.add(vectorRB);
		
		// add to panel
		add(uniformRB);
		add(optimalRB);
		add(vectorRB);
		add(compressB);
		add(label);
		add(browseB);
		add(quantizerTF);
		add(quantizerlabel);
		// Actions
		uniformRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Uniform Quantizer");
				tech="Uniform Quantizer";
			}
		});
		
		optimalRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Optimal Quantizer");
				tech="Optimal Quantizer";
			}
		});
		
		vectorRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Vector Quantizer");
				tech="Vector Quantizer";
			}
		});
		
		compressB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int q=0;
				try
				{
					q=Integer.parseInt(quantizerTF.getText());
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Enter correct quantizer" , "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(uniformRB.isSelected())
				{
					double mse = UniformQuantizer.compress(sourceFile, destinationFile.getPath(), q);
					JOptionPane.showMessageDialog(null,"Mean squar error = " + mse ,"MSE" , JOptionPane.INFORMATION_MESSAGE);
				}
				
				else if (optimalRB.isSelected())
				{
					
				}
				
				else
				{
					
				}
				
				JOptionPane.showMessageDialog(null, "The compressed data created in \n" + destinationFile.getPath());
			}
			
			
			
		});
		
		browseB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseInputFile = new JFileChooser("D:");
				chooseInputFile.setDialogTitle("Choose image to compress");
				chooseInputFile.setAcceptAllFileFilterUsed(false);
				
				FileFilter filter = new FileNameExtensionFilter("JPG File","jpg");
				chooseInputFile.addChoosableFileFilter(filter);
				
				while(! (chooseInputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				chooseOutputFile = new JFileChooser("D:");
				chooseOutputFile.setDialogTitle("Choose Destination path");
				chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				while(! (chooseOutputFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				sourceFile = chooseInputFile.getSelectedFile();
				
				try {
					destinationFile = chooseOutputFile.getSelectedFile();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "this");
				}
			}
		});
		
		chooseInputFile = new JFileChooser("D:");
		chooseInputFile.setDialogTitle("Choose image to compress");
		chooseInputFile.setAcceptAllFileFilterUsed(false);
		
		FileFilter filter = new FileNameExtensionFilter("JPG File","jpg");
		chooseInputFile.addChoosableFileFilter(filter);
		
		
		while(! (chooseInputFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		chooseOutputFile = new JFileChooser("D:");
		chooseOutputFile.setDialogTitle("Choose Destination path");
		chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		while(! (chooseOutputFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		sourceFile = chooseInputFile.getSelectedFile();
		
		try {
			destinationFile = chooseOutputFile.getSelectedFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "this");
		}
		
		
	}
}
