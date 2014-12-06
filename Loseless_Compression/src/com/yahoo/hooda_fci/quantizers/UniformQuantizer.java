package com.yahoo.hooda_fci.quantizers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UniformQuantizer {

	public static double compress(File sourceImgPath, String destinationFile, int quantizer)
	{
		int levels = (int)  Math.pow(2, quantizer); 
		int step = 256 / levels ;
		double MSE = 0;



		// get image into 2D array
		
		
		int img[][]=ImageRW.readImage(sourceImgPath.getPath());
		System.out.println(quantizer + " " + img.length + " " + img[0].length);
		StringBuilder code = new StringBuilder("");
		// get code represent each pixel 
		for(int i=0;i<img.length;i++)
		{
			for (int j = 0; j < img[i].length; j++) {
				code.append(getCode(img[i][j], step, quantizer));
				MSE += Math.pow(img[i][j] - getQ_1(img[i][j], step), 2);
			}
		}
		MSE/= (img.length*img[0].length);
		while(code.length()%31 !=0)
			code.append("0");

		//System.out.println(code.length());
		File compFile = new File(destinationFile+ "\\"  + sourceImgPath.getName().substring(0,sourceImgPath.getName().length()-4) + "comp.txt");
		try {
			compFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try 
		{
			System.out.println(ImageRW.height+" "+ ImageRW.width);
			
			fos = new FileOutputStream(compFile);
			oos = new ObjectOutputStream(fos);
			oos.writeInt(quantizer);
			oos.writeInt(img.length);
			oos.writeInt(img[0].length);

			for(int i=0;i<code.length();i+=31)
			{
				int comp = Integer.parseInt(code.substring( i, i+31 ),2);
				oos.writeInt(comp);
				//System.out.println(i+"_"+comp);
				//				System.out.println("_length after adding comp"+outPut.length());
			}
			oos.close();
			fos.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return MSE;
	}
	/////////////////////////////////

	private static String getCode (int num, int step, int q )
	{
		num/=step;
		
		String x = Integer.toBinaryString(num);
		while(x.length()<q)
			x="0"+x;
				
		return x;
			

	}
	
	////////////////
	private static int getQ_1 (int num, int step )
	{
		num/=step;
		num*=step;
		num+=(step*.5);
		
		return num;
	}
	////////////////////////////////////////////////////////////////////////////////////////////


	public static void deCompress(File sourceTextFile, String destinationpath) 
	{
		int quantizer =0;
		int length =0;
		int wideth =0;
		int levels =0;
		int step =0;

		// carry upper limit of each level

		
		int [][] array =null;
		
		StringBuilder codes = new StringBuilder("");
		
		try 
		{
			FileInputStream fis = new FileInputStream(sourceTextFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			quantizer = ois.readInt();
			//heightwidth[0]= ois.readInt();
			//heightwidth[1]= ois.readInt();
			length = ois.readInt();
			wideth = ois.readInt();
			
			levels = (int)  Math.pow(2, quantizer); 
			step = 256 / levels ;
			array = new int [length][wideth];
			
			System.out.println(quantizer + " " + length + " " + wideth);
			
			while(true)
			{
				try
				{
					String currentCode = Integer.toBinaryString(ois.readInt() );
					while(currentCode.length()<31)
						currentCode = "0" + currentCode;

					codes.append(currentCode);

					//System.out.println(Integer.toBinaryString(ois.readInt()));
					//System.out.println( ois.readInt() );
				}
				catch(Exception e)
				{
					break;
				}

			}

			ois.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0 ;
		for (int i = 0; i < array.length; i++) 
		{
			for (int j = 0; j < array[0].length; j++) 
			{
				int num = Integer.parseInt(codes.substring(count, count+quantizer), 2);		
				// Q^-1
				array[i][j] = (num*step)+(step/2);		
				count+=quantizer;
			}
			//System.out.println(array[i][0]);
		}
		//System.out.println(array[1][1]);
		ImageRW.height = array.length;
		ImageRW.width = array[0].length;
		ImageRW.writeImage(array, destinationpath+"\\"+sourceTextFile.getName().substring(0,sourceTextFile.getName().length()-4) + ".jpg");
		
	}

	
}
