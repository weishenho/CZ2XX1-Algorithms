import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateFile {
	
	private static String path = "dataset/";

	//generate random dataset and store in file
	public static void generateRandomDataFile(int n, String filename) {
		PrintWriter pw;
		try {

			pw = new PrintWriter(new FileWriter(path+filename), true);
			int range = 100000;
			Random r = new Random();
			for (int i = 0; i < n; i++)
				pw.println(1 + r.nextInt(range));
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//generate ascending dataset and store in file
	public static void generateAscDataFile(int size, String unsortedFilename) {
		PrintWriter pw;
		try {

			pw = new PrintWriter(new FileWriter(path+unsortedFilename), true);
			for (int i = 1; i <=size; i++)
				pw.println(i);
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//generate descending dataset and store in file
	public static void generateDscDataFile(int size, String unsortedFilename) {
		PrintWriter pw;
		try {

			pw = new PrintWriter(new FileWriter(path+unsortedFilename), true);
			for (int i = size; i >=1; i--)
				pw.println(i);
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//write sorted data to a file
	public static void writeSortedData(String filename, int[] slot) {
		PrintWriter pw;
		try {

			pw = new PrintWriter(new FileWriter(path+filename), true);
			for (int i = 0; i < slot.length; i++)
				pw.println(slot[i]);
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//clear contents in file
	public static void clearFile(String filename) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(path+filename), true);
			pw.print("");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//append text to file
	public static void writePerformance(String filename, String text) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(path+filename, true));
			pw.println(text);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
