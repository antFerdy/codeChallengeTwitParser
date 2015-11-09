package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class FileManager {
	private static File fileIn;
	private static FileInputStream fileInStream; 
	private static String sep = File.separator;
	private static InputStreamReader inStreamReader;
	private static PrintWriter firstFeatWriter;
	private static PrintWriter secondFearWriter;
	
	
	/**
	 * create tweet input stream reader 
	 **/
	public static InputStreamReader createTwitInput() throws FileNotFoundException, UnsupportedEncodingException {
		fileIn = new File("tweet_input" + sep + "tweets.txt");
		fileInStream = new FileInputStream(fileIn);
		inStreamReader = new InputStreamReader(fileInStream, "UTF-8");
		return inStreamReader;
	}
	
	
	/**
	 * close tweet input stream reader 
	 **/
	public static void closeTwitInput() throws IOException {
		fileInStream.close();
		inStreamReader.close();
	}

	/**
	 * create output for first feature logic 
	 * @throws IOException 
	 **/
	public static PrintWriter createFirstFeatOut() throws IOException {
		String sep = File.separator;
		File f = new File("tweet_output" + sep + "ft1.txt");
		if(!f.exists())
			f.createNewFile();
		firstFeatWriter = new PrintWriter(f, "UTF-8");
		return firstFeatWriter;
	}

	/**
	 *  Close printwriter for first feature
	 **/
	public static void closeFirstFeatOutput() {
		firstFeatWriter.flush();
		firstFeatWriter.close();
	}

	
	
	/**
	 * Save second feature result in file
	 * @throws IOException 
	 **/
	public static void saveSecondFeatureOutput(double countAvgDegree) throws IOException {
    	File f = new File("tweet_output" + sep + "ft2.txt");
		if(!f.exists())
			f.createNewFile();
		
		if(secondFearWriter == null)
			secondFearWriter = new PrintWriter(f, "UTF-8");
		
		DecimalFormat df = new DecimalFormat("####0.00");
		String degreeStr = df.format(countAvgDegree);
		secondFearWriter.println(degreeStr);
		
	}


	public static void closeSecondFeatOutput() {
	    if(secondFearWriter != null) {
	    	secondFearWriter.flush();
	    	secondFearWriter.close();
    	}
	}

}
