package uk.co.gideonparanoid.styleConvertor;

import java.io.*;
import java.util.ArrayList;


/**
 * small program to convert text with function and variable names with split_with_underscores to camelCase
 * does not take into account things like libraries used (yet, perhaps further development)
 * overwrites file specified unless another argument givenr2
 * 
 * @author Gideon MW Jones
 * @version 1.0
 */
public class StyleConvertor {
	
	public static void main(String[] arguments) {

		if (arguments.length == 0) {
			System.out.println("Specify a filename.");
			System.exit(0);
		} else {

			fileWriter(convertor(fileReader(arguments[0])), arguments[0], arguments[1]);

			System.out.println("Success");
		}
	}

	
	/**
	 * reads a file
	 * @return 	an ArrayList<String> of the lines of the file
	 */
	public static ArrayList<String> fileReader(String filename) {		
		BufferedReader bufferedReader = null;
		ArrayList<String> lines = new ArrayList<String>();

		try { 
			String currentLine;

			bufferedReader = new BufferedReader(new FileReader(new File(filename)));

			while ((currentLine = bufferedReader.readLine()) != null) {
				lines.add(currentLine);
			}

		} catch (IOException iOE) {
			iOE.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) bufferedReader.close();
			} catch (IOException iOE) {
				iOE.printStackTrace();
			}
		}
		return lines;
	}


	/**
	 * converts function and variable names with split_with_underscores to camelCase
	 * @param lines		an ArrayList<String> of the lines of the file
	 * @return			an ArrayList<String> of the lines of the file after conversion
	 */
	public static ArrayList<String> convertor(ArrayList<String> lines) {
		ArrayList<String> result = new ArrayList<String>();

		for (String line : lines) {
			String[] splitLine = line.split("[_]");
			String finalLine = "";
			
			if (splitLine.length > 0) {
				for (String section : splitLine) {				
					finalLine += Character.toString(section.charAt(0)).toUpperCase() + section.substring(1);
				} 
			}

			result.add(finalLine);
		}

		return result;
	}

	
	/**
	 * writes the resultant file
	 * @param lines 			an ArrayList<String> of the lines of the file
	 * @param resultFilename 	the filename of the file to produce, defaults to original filename if null
	 */
	public static void fileWriter(ArrayList<String> lines, String startFilename, String resultFilename) {

		// compiling the file into a single string 
		String content = "";
		for (String line : lines) {
			content += line + "\n";
		}

		try { 
			File file = new File((startFilename == null) ? resultFilename : startFilename);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.close();

		} catch (IOException iOE) {
			iOE.printStackTrace();
		}		
	}
}
