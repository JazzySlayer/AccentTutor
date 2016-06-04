/*
 * 
 * Copyright 2013 Digital Audio Processing Lab, Indian Institute of Technology.  
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 *
 */

/**
 *
 * @author  : Jigar Gada
 * @contact : jigargada23@yahoo.com
 */

package accenttutor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class FeatureFileExtractor {
	
	// This extractor is for wavFiles only with 44 bytes of header. For other File format 
	// Kindly  mail me.
	//Sampling rate
	static float sampleRate = 8000;
	//windowSize should always be power of 2^n. Preferably closer to newWindowSize.
	static int windowSize = 256;
	// actual window size. Calculate it according to the sampling rate.
	// by default it is 25.625ms which is equivalent to 205 samples for
	// 8000 Hz sampling rate.
	public static int newWindowSize = 205;
	// hopsize by default is 10ms
	public static int hopsize = 80;
	//No of mel filters with the first coefficient as energy.
	static int numberCoefficients = 13;
	//Lower edge of filter
	static double minFreq = 133;
	//Upper edge of filter
	static double maxFreq = 3500;
	//Number of mel filter banks
	static int numberFilters = 31;
	
	
	/**
	 * This function computes the MFCC features of the wav file.<br>
	 * It takes the file name, input folder and the output folder as parameters
	 * and stores the mfc files in the output folder with extension .mfc
	 * @param fileName : name of the wav file to be processed
	 * @param inputFolder : folder in which the wav files are stored
	 * @param outputFolder : folder in which the mfc files will be stored
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	/*public static void computeFeatures(String fileName, String inputFolder, String outputFolder) throws IllegalArgumentException, IOException, UnsupportedAudioFileException {

		// read the wav file
		String wavFile = inputFolder + "/" + fileName + ".wav";
		System.out.println("converting " + wavFile + " to mfc...");
		File soundfile = new File(wavFile);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile);
		AudioPreProcessor in = new AudioPreProcessor(audioIn,sampleRate);

		boolean useFirstCoefficient = true;
		MFCC feat = new MFCC(sampleRate, windowSize, numberCoefficients, useFirstCoefficient, minFreq, maxFreq, numberFilters);
		Vector <double[]> features = feat.process(in,audioIn);

		System.out.println("Vector Size :"+features.size());
        double arr[];
        for (double[] feature : features) {
            arr = feature;
            for (double anArr : arr) {
                System.out.print((float) anArr + " ");
            }
            System.out.println();
        }

		// writing the  feature file
		*//*FileOutputStream fos = null;
	    DataOutputStream dos = null;
	    String outputFile = outputFolder + "/" + fileName + ".mfc";
	    try{
	    	// create file output stream
	    	File filedir = new File(outputFile);
	    	File parent_dir = filedir.getParentFile();

	    	if (null != parent_dir)
	    	{
	    		parent_dir.mkdirs();
	    	}

	    	fos = new FileOutputStream(outputFile);
	    	// create data output stream
	    	dos = new DataOutputStream(fos);
	    	double arr[];
	    	dos.writeInt(features.size() * 13);
	    	for(int i = 0; i < features.size(); i++){
	    		arr = features.get(i);
	    		for(int j = 0; j < arr.length; j++){
					//System.out.print((float)arr[j] + " ");
	    			dos.writeFloat((float) arr[j]);
		    	}
	    	//System.out.println();
	    	}

	    }catch(Exception e){
         // if any I/O error occurs
         e.printStackTrace();
	    }finally{ if(dos != null)
	    	dos.close();
	    if(fos!=null)
         fos.close();
	    }*//*

	}*/

    public static void computeFeatures(String fileName, String inputFolder) throws IllegalArgumentException, IOException, UnsupportedAudioFileException {

        // read the wav file
        String wavFile = inputFolder + "/" + fileName + ".wav";
        System.out.println("converting " + wavFile + " to mfc...");
        File soundfile = new File(wavFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile);
        AudioPreProcessor in = new AudioPreProcessor(audioIn,sampleRate);

        boolean useFirstCoefficient = false;
        MFCC feat = new MFCC(sampleRate, windowSize, numberCoefficients, useFirstCoefficient, minFreq, maxFreq, numberFilters);
        Vector <double[]> features = feat.process(in,audioIn);

        System.out.println("Vector Size :"+features.size());
        double arr[];
        for (double[] feature : features) {
            arr = feature;
            for (double anArr : arr) {
                System.out.print((float) anArr + " ");
            }
//            System.out.println();
        }

        // writing the  feature file
		/*FileOutputStream fos = null;
	    DataOutputStream dos = null;
	    String outputFile = outputFolder + "/" + fileName + ".mfc";
	    try{
	    	// create file output stream
	    	File filedir = new File(outputFile);
	    	File parent_dir = filedir.getParentFile();

	    	if (null != parent_dir)
	    	{
	    		parent_dir.mkdirs();
	    	}

	    	fos = new FileOutputStream(outputFile);
	    	// create data output stream
	    	dos = new DataOutputStream(fos);
	    	double arr[];
	    	dos.writeInt(features.size() * 13);
	    	for(int i = 0; i < features.size(); i++){
	    		arr = features.get(i);
	    		for(int j = 0; j < arr.length; j++){
					//System.out.print((float)arr[j] + " ");
	    			dos.writeFloat((float) arr[j]);
		    	}
	    	//System.out.println();
	    	}

	    }catch(Exception e){
         // if any I/O error occurs
         e.printStackTrace();
	    }finally{ if(dos != null)
	    	dos.close();
	    if(fos!=null)
         fos.close();
	    }*/

    }

}

