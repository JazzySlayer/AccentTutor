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

import com.accenttutor.Configuration;

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


    public static List<Double> computeFeatures(String fileName, Configuration conf) throws IllegalArgumentException, IOException, UnsupportedAudioFileException {

		// read the wav file
		List<Double> resultLists = new ArrayList<Double>();
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
			wavFile = conf.getTemplateName()/*"/home/anons/Documents/BSCCSIT/AccentTutor/web-app/mediaOfSounds/NN"*//*+fileName.replace(".wav","")*/+file_num+".wav";
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
			saveAllDistances[position++] = answer;
		} while (answer > 0 && file_num < 4);
		int count = 0;
		double tot = 0;
		double avg = 0;
		boolean match = false;
		boolean nearZeroDistance = false;

		for (double item:saveAllDistances){
			resultLists.add(item);
			tot+=item;
		}

		for (double dist:resultLists){
			if (dist<1){
				nearZeroDistance = true;
				match = true;
				break;
			}
		}
		if (!nearZeroDistance){
			avg/=4;
			for (double item:resultLists){
				if(item<avg){
					count++;
					if(count>3){
						resultLists.add(avg);
						match = true;
						break;
					}
				}
			}
		}
		if (match){
			resultLists.add(1.0);
		}
		else {
			resultLists.add(0.0);
		}
		/*for(double item: saveAllDistances){
			if(item<1){
				count = 3;
				goLoop = false;
				break;
			}
			avg+=item;
        }
		if(goLoop){
			avg=avg/4;
			System.out.println("avg = " + avg);
			for(double item: saveAllDistances){
				if(item<avg){
					count++;
					if(count>3){
						break;
					}
				}
			}
		}*/
		System.out.println("Match = " + match);
		return resultLists;
    }

}

