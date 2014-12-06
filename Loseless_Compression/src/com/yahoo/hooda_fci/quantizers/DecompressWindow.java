package com.yahoo.hooda_fci.quantizers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.UNKNOWN;

import java.io.File;

public class DecompressWindow extends JFrame{

	private JButton deCompressB ;
	private JRadioButton uniformRB;
	private JRadioButton optimalRB;
	private JRadioButton vectorRB;
	private JFileChooser chooseInputFile;
	private JFileChooser chooseOutputFile;
	private ButtonGroup bg;
	private File sourceFile;
	private File destinationFile;
	private JLabel label;
	private JButton browseB ;
	
	DecompressWindow()
	{
		super("Decompression window");

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//setResizable(false);
		setBounds(100, 100, 455, 180);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);



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
		deCompressB = new JButton("Decompress");
		deCompressB.setFont(new Font("Tahoma", Font.BOLD, 12));
		deCompressB.setBounds(158, 66, 111, 23);

		//browse
		browseB = new JButton("Browse");
		browseB.setFont(new Font("Tahoma", Font.BOLD, 12));
		browseB.setBounds(305, 66, 101, 23);

		label = new JLabel("Uniform Quantizer");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(135, 66+23+10, 181, 23);
		
		add(label);
		
		// Actions
		uniformRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Uniform Quantizer");
			}
		});
		
		optimalRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Optimal Quantizer");
			}
		});
		
		vectorRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Vector Quantizer");
			}
		});
		
		// add to group button
		bg.add(uniformRB);
		bg.add(optimalRB);
		bg.add(vectorRB);

		// add to panel
		add(uniformRB);
		add(optimalRB);
		add(vectorRB);
		add(deCompressB);
		add(browseB);

		// Actions
		deCompressB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(uniformRB.isSelected())
				{	
				
					UniformQuantizer.deCompress(sourceFile,destinationFile.getPath() );
					
				}

				else if (optimalRB.isSelected())
				{
					
				}

				else
				{
					
					
				}

				JOptionPane.showMessageDialog(null, "The decompressed data created in \n" + destinationFile.getPath());
			}

		});
		
		browseB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseInputFile = new JFileChooser("D:");
				chooseInputFile.setDialogTitle("Choose file to Decompress");
				chooseInputFile.setAcceptAllFileFilterUsed(false);
				FileFilter filter = new FileNameExtensionFilter("Text File","txt");
				chooseInputFile.addChoosableFileFilter(filter);
				
				while(! (chooseInputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				chooseOutputFile = new JFileChooser("D:");
				chooseOutputFile.setDialogTitle("Choose Destination path");
				chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				while(! (chooseOutputFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				sourceFile = chooseInputFile.getSelectedFile();
				
				destinationFile = chooseOutputFile.getSelectedFile();
					
			}
		});
		

		// Select Files
		chooseInputFile = new JFileChooser("D:");
		chooseInputFile.setDialogTitle("Choose file to Decompress");
		chooseInputFile.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("Text File","txt");
		chooseInputFile.addChoosableFileFilter(filter);
		
		while(! (chooseInputFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);

		chooseOutputFile = new JFileChooser("D:");
		chooseOutputFile.setDialogTitle("Choose Destination file");
		chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		while(! (chooseOutputFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		sourceFile = chooseInputFile.getSelectedFile();
		destinationFile = chooseOutputFile.getSelectedFile();
		
		
		
		
	}
}
