import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
	
	static String fileFmt = ".txt";
	private static void sortTest(int size, String unsortedFile, String mergeSortFile, String quickSortFile) {
		double startTime;
		double mergeCPUTime;
		double quickCPUTime;
		int mergeSortSlot[] = new int[size];
		int quickSortSlot[] = new int[size];
		
		//enter data from file their respective array
		Scanner fsc;
		int i = 0;
		try {
			fsc = new Scanner(new File("dataset/"+unsortedFile));
			while (fsc.hasNextInt()) {
				int num = fsc.nextInt();
				mergeSortSlot[i] = num;
				quickSortSlot[i] = num;
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//merge sort the array
		startTime = System.nanoTime();
		int mergeComparisons = mergesort.sort(mergeSortSlot);
		mergeCPUTime = (System.nanoTime() - startTime) / 1000000.0;
		GenerateFile.writeSortedData(mergeSortFile, mergeSortSlot); //store sorted data into a file


		//quick sort the array
		startTime = System.nanoTime();
		int quickComparisons = quicksort.sort(quickSortSlot);
		quickCPUTime = (System.nanoTime() - startTime) / 1000000.0;
		GenerateFile.writeSortedData(quickSortFile, quickSortSlot); //store sorted data into a file
		
		
		//write performance into result text file
		String text = "DataSize:" + size + "\t UnsortFile:" + unsortedFile + "\t|\t MergeSortedFile:" + mergeSortFile
				+ "\t Comparisons:" + mergeComparisons + "\t CPU Time:" + mergeCPUTime +"\t";
		text += "|\t QuickSortedFile:" + quickSortFile + "\t Comparisons:" + quickComparisons + "\t CPU Time:" + quickCPUTime;
		GenerateFile.writePerformance("Results"+fileFmt, text);

	}

	public static void main(String[] args) {
		GenerateFile.clearFile("Results"+fileFmt);
		
		int[] datasize ={10000, 2000, 4000, 6000, 8000, 10000};
		
		int size;
		String unsortedFilename;
		
		//run sort testing
		for(int i=0; i<datasize.length; i++)
		{
			//Random Data set
			size = datasize[i];
			unsortedFilename =  size+"RandIntDataset"+fileFmt;
			GenerateFile.generateRandomDataFile(size, unsortedFilename);
			sortTest(size, unsortedFilename, size+"RandMergeSorted"+fileFmt, size+"RandQuickSorted"+fileFmt);
			
			//ascending data set
			unsortedFilename =  size+"AscIntDataset"+fileFmt;
			GenerateFile.generateAscDataFile(size, unsortedFilename);
			sortTest(size, unsortedFilename, size+"AscMergeSorted"+fileFmt, size+"AscQuickSorted"+fileFmt);
			
			//descending data set
			unsortedFilename =  size+"DscIntDataset"+fileFmt;
			GenerateFile.generateDscDataFile(size, unsortedFilename);
			sortTest(size, unsortedFilename, size+"DscMergeSorted"+fileFmt, size+"DscQuickSorted"+fileFmt);
		}
		
		System.out.println("All datasets and performance statisitcs in dataset directory");
		System.out.println("Performance statisitics written in Results.txt");
		System.out.println("All files in dataset directory are generated at run-time");
		System.out.println("Please ignore the first 3 rows in result as it may not be accurate");

	}

}
