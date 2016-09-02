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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


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
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */


    public static int computeFeatures(String fileName) throws IllegalArgumentException, IOException, UnsupportedAudioFileException {

		// read the wav file
		System.out.println("enter");
		Double[] saveAllDistances = new Double[4];
		List<Float> originalList = new ArrayList<Float>();
		String wavFile = /*"/home/anons/Documents/BSCCSIT/AccentTutor/web-app/mediaOfSounds/"+*/fileName;
		File soundfile = new File(wavFile);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile);
		AudioPreProcessor in = new AudioPreProcessor(audioIn, sampleRate);
		boolean useFirstCoefficient = false;
		MFCC feat = new MFCC(sampleRate, windowSize, numberCoefficients, useFirstCoefficient, minFreq, maxFreq, numberFilters);
		Vector<double[]> features = feat.process(in, audioIn);
		boolean close = false;
		double arr[];
		int m1 = 0;

		for (double[] feature : features) {
			arr = feature;
			for (double anArr : arr) {
				m1++;
				Float aFloat = new Float(anArr);
				if (!(aFloat.isInfinite()) && !(aFloat.isNaN())) {
					originalList.add(aFloat);
				} else {
					close = true;
					break;
				}
			}
			if (close) {
				break;
			}
		}
		String whichOne = "";
		double answer ;
		int file_num = 0;
		int position = 0;
		do {
			answer = 0;
			List<Float> recordedList = new ArrayList<Float>();
			file_num++;
			wavFile = "/home/anons/Documents/BSCCSIT/AccentTutor/web-app/mediaOfSounds/NN"/*+fileName.replace(".wav","")*/+file_num+".wav";
			soundfile = new File(wavFile);
			audioIn = AudioSystem.getAudioInputStream(soundfile);
			in = new AudioPreProcessor(audioIn, sampleRate);
			useFirstCoefficient = false;
			feat = new MFCC(sampleRate, windowSize, numberCoefficients, useFirstCoefficient, minFreq, maxFreq, numberFilters);
			features = feat.process(in, audioIn);
			close = false;
			double arr1[];
			m1 = 0;
			for (double[] feature : features) {
				arr1 = feature;
				for (double anArr : arr1) {
					m1++;
					Float aFloat = new Float(anArr);
					if (!(aFloat.isInfinite()) && !(aFloat.isNaN())) {
						recordedList.add(aFloat);
					} else {
						close = true;
						break;
					}
				}
				if (close) {
					break;
				}
			}

			float[] orginals = new float[originalList.size()];
			float[] records = new float[recordedList.size()];

			int i = 0;
			for (float anArr : originalList) {
				orginals[i++] = anArr;
//			System.out.println("orginals = " + orginals[i++] + "i = " + i);
			}
			i = 0;
			for (float anArr : recordedList) {
				records[i++] = anArr;
//			System.out.println("anArr = " + records[i++]);
			}

			DTW dtw = new DTW(orginals, records);

			answer = dtw.getDistance();
			System.out.println("answer = " + answer);
			saveAllDistances[position++] = answer;
		} while (answer > 1 && file_num < 4);
		int count = 0;
		double avg = 0;
		boolean goLoop = true;
		for(double item: saveAllDistances){
			if(item<1){
				count = 3;
				goLoop = false;
				break;
			}
			avg+=item;
		}
		System.out.println("avg = " + avg);
		if(goLoop){
			avg=avg/4;
			for(double item: saveAllDistances){
				if(item<avg){
					count++;
					if(count>3){
						break;
					}
				}
			}
		}



		// writing the  feature file
//		FileOutputStream fos = null;
//	    DataOutputStream dos = null;
//	    String outputFile = outputFolder + "/" + fileName + ".txt";
//	    try{
//	    	// create file output stream
//	    	File filedir = new File(outputFile);
//	    	File parent_dir = filedir.getParentFile();
//
//	    	if (null != parent_dir)
//	    	{
//	    		parent_dir.mkdirs();
//	    	}
//
//	    	fos = new FileOutputStream(outputFile);
//	    	// create data output stream
//	    	dos = new DataOutputStream(fos);
//	    	double arr[];
//	    	dos.writeInt(features.size() * 13);
//	    	for(int i = 0; i < features.size(); i++){
//	    		arr = features.get(i);
//	    		for(int j = 0; j < arr.length; j++){
//					//System.out.print((float)arr[j] + " ");
//	    			dos.writeFloat((float) arr[j]);
//		    	}
//	    	//System.out.println();
//	    	}
//
//	    }catch(Exception e){
//         // if any I/O error occurs
//         e.printStackTrace();
//	    }finally{ if(dos != null)
//	    	dos.close();
//	    if(fos!=null)
//         fos.close();
//	    }
		System.out.println("count = " + count);
		return count;
    }

}

