import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Hashing {
	
	final static int SIZE = 1000;
	static String[] linearHashTable;
	static String[] doubleHashTable;
	
	static int linearInsertCollisionCount;
	static int linearSuccessInsert;
	static int linearSearchComparison;
	
	static int doubleInsertCollisionCount;
	static int doubleSuccessInsert;
	static int doubleSearchComparison;

	
	public static int hashFunction(int k){
		return k % SIZE;
	}
	
	public static int generateHashValue(String k){
		//convert each character to ascii value and sum them together;
		int hashValue= 0;
		char character;
		int ascii;
		for(int i=0; i<k.length(); i++)
		{
			character = k.charAt(i);
			ascii = (int) character * (i+1);
			hashValue += ascii;
		}
		return hashValue;
	}
	
	/* Linear Probing Methods */
	public static int linearRehash(int j){
		return (j+1) % SIZE;
	}
	
	public static int linearInsert(String newEntry){
		
		int hashValue = generateHashValue(newEntry);
		int code = hashFunction(hashValue);
		int loc = code;
		
		while(linearHashTable[loc] != null && !linearHashTable[loc].equals("obsolete")){
			linearInsertCollisionCount++;
			if(linearHashTable[loc].equals(newEntry))
				return -1; //same entry found
			else{
				loc = linearRehash(loc);
				if(loc == code) return 0; //table full
			}		
		}
		linearHashTable[loc] = newEntry; //insert into empty slot
		linearSuccessInsert++;
		return 1; //insert success
	}
	
	public static int linearSearch(String k){
		int hashValue = generateHashValue(k);
		int code = hashFunction(hashValue);
		int loc = code;
		
		while(linearHashTable[loc] != null){
			linearSearchComparison++;
			if(linearHashTable[loc].equals(k)){
				return loc;
			}
			else{
				loc = linearRehash(loc);  //rehash to next location
				if(loc == code) break; //not in hash table
			}
		}
		return -1;
	}
	/* End of Linear Probing Methods */
	

	/* Double Hashing Methods  */
	public static int doubleHashIncr(int k){
		return (k%8) + 1;
	}
	public static int doubleRehash(int j, int d){
		return (j+d) % SIZE;
	}
	
	public static int doubleHashInsert(String newEntry){
		
		int hashValue = generateHashValue(newEntry);
		
		int code = hashFunction(hashValue);
		int loc = code;
		int inc = doubleHashIncr(hashValue);
		
		while(doubleHashTable[loc] != null && !doubleHashTable[loc].equals("obsolete")){
			doubleInsertCollisionCount++;
			if(doubleHashTable[loc].equals(newEntry))
				return -1; //same entry found
			else{
				loc = doubleRehash(loc, inc); //rehash to next location
				if(loc == code) return 0; //table full
			}		
		}
		doubleHashTable[loc] = newEntry; //insert into empty slot
		doubleSuccessInsert++;
		return 1;
	}
	
	public static int doubleHashSearch(String k){
		int hashValue = generateHashValue(k);
		
		int code = hashFunction(hashValue);
		int loc = code;
		int inc = doubleHashIncr(hashValue);
		

		while(doubleHashTable[loc] != null && !doubleHashTable[loc].isEmpty()){
			doubleSearchComparison++;
			if(doubleHashTable[loc].equals(k)){
				return loc;
			}
			else{
				loc = doubleRehash(loc, inc);  //rehash to next location
				if(loc == code) break; //not in hash table
			}
		}
		return -1;
	}
	/* End of Double Hashing Methods */ 

	
	
	

	public static void main(String[] args) {

		//program description
		System.out.println("-This program compares linear probing and double hashing,");
		System.out.println("-in terms of collisions, comparisons and CPU time. (lesser is better)");
		System.out.println("-Each algorithm manage their own hash table.");
		System.out.println("-Vehicle registration number dataset are used for illustration.");

		Scanner isc = new Scanner(System.in);
		int choice;
		int entriesToAdd = 0;
		
		String item;
		String searchKeyword;
		
		
		//variables for search
		int index;
		int countSearch;
		int totalLinearCompare;
		int totalDoubleCompare;
		int unsucessLinearSearch;
		int unsucessDblSearch;
		double startTime;
		double cpuTime;
		double totalLinearCPU;
		double totalDoubleCPU;
		
		String format = "%-32s%s%n";
		
		try{

			do {
				System.out.println("\nPress <Enter> to continue...");
				System.in.read();
				
				System.out.println("Perform the following:");
				System.out.println("1: Insert entries into hash tables");
				System.out.println("2: Search hash tables");
				System.out.println("3: Random Search Test");
				System.out.println("4: Quit");
				System.out.print("Enter Choice: ");
				choice = isc.nextInt();
				switch (choice) {
				case 1:
					int insertCount = 0;					
					linearHashTable = new String[SIZE];
					doubleHashTable = new String[SIZE];
					
					System.out.print("Set amount of data in hash tables (i.e. 100, 300, 500, 700 to 900): ");
					entriesToAdd = isc.nextInt();
					
					int totalLinearCollision = 0;
					int totalDblCollision = 0;

					linearSuccessInsert = 0;
					doubleSuccessInsert = 0;
					System.out.print(System.getProperty("user.dir"));
					if(entriesToAdd <= 1000){
						Scanner fsc = new Scanner(new File("dataset/1000VehicleNumber.txt"));	
						while(fsc.hasNextLine() && insertCount < entriesToAdd){
							
							linearInsertCollisionCount = 0;
							doubleInsertCollisionCount = 0;
							
							item = fsc.nextLine().trim();
							
							System.out.println("Inserting "+item+" into linear probing table...");
							if(linearInsert(item) == 1)
								System.out.println("Insertion Successful     Collisions: " + linearInsertCollisionCount);		
							else
								System.out.println("Insertion Unsuccessful     Collisions: " + linearInsertCollisionCount);
								
							
							System.out.println("Inserting "+item+" into double hashing table...");
							if(doubleHashInsert(item) == 1)
								System.out.println("Insertion Successful     Collisions: " + doubleInsertCollisionCount);			
							else
								System.out.println("Insertion Unsuccessful     Collisions: " + doubleInsertCollisionCount);
								
							System.out.println();
							totalLinearCollision += linearInsertCollisionCount;
							totalDblCollision += doubleInsertCollisionCount;
							insertCount++;
						}
						System.out.println("\nResult:");
						System.out.println("Linear Probing");
						System.out.println("Collisions occured: " +totalLinearCollision);
						System.out.println("Successful insertions: " +linearSuccessInsert+" out of "+entriesToAdd);
						System.out.println("------------------------------------------------------------");
						System.out.println("Double Hashing");
						System.out.println("Collisions occured: " +totalDblCollision);
						System.out.println("Successful insertions: " +doubleSuccessInsert+" out of "+entriesToAdd);
						
						fsc.close();

					}
					else System.out.println("Hash tables are full, maximum 1000 entries in hash tables");
				
					break;
				case 2:
					countSearch = 0;
					totalLinearCompare = 0;
					totalDoubleCompare = 0;
					
					totalLinearCPU = 0;
					totalDoubleCPU = 0;
					
					unsucessLinearSearch = 0;
					unsucessDblSearch = 0;
					while(true)
					{
						
						System.out.print("\nEnter a Vehicle registration number to search (-1 to exit): ");
						searchKeyword = isc.next();
						
						if(searchKeyword.equals("-1")) break;
						
						countSearch++;
						
						linearSearchComparison = 0;
						doubleSearchComparison = 0;
						
						//perform search for linear probing
						System.out.println("\nLinear Probing:");
						
						startTime = System.nanoTime();
						index = linearSearch(searchKeyword);
						cpuTime = (System.nanoTime() - startTime) / 1000000.0;
						totalLinearCPU += cpuTime;
						
						
						if(index != -1)
							System.out.println("Found "+searchKeyword+" at index "+index);
						else{
							unsucessLinearSearch++;
							System.out.println(searchKeyword+" not found");
						}
							
						System.out.println("Comparisons: "+linearSearchComparison+"     "+"CPU Time: "+ String.format("%.4f", cpuTime) + " ms");
						
						System.out.println("------------------------------------------------------------");
						
						//perform search for Double Hashing
						System.out.println("Double Hashing:");
						startTime = System.nanoTime();
						index = doubleHashSearch(searchKeyword);
						cpuTime = (System.nanoTime() - startTime) / 1000000.0;
						totalDoubleCPU += cpuTime;

						if(index != -1)
							System.out.println("Found "+searchKeyword+" at index "+index);
						else{
							unsucessDblSearch++;
							System.out.println(searchKeyword+" not found");
						}
						System.out.println("Comparisons: "+doubleSearchComparison+"     "+"CPU Time: "+ String.format("%.4f", cpuTime) + " ms");
						
						totalLinearCompare += linearSearchComparison;
						totalDoubleCompare += doubleSearchComparison;
						
					}// end of while loop
					
					
					//Generate Statistics report
					if(countSearch > 0 ){
						System.out.println("\nSearch Statistics");
						System.out.println("Number of Searches: "+countSearch);
						System.out.printf(format, "Linear Probing", "Records in Hash table: " + linearSuccessInsert);
						System.out.printf(format, "Searches found: " + (countSearch-unsucessLinearSearch), "Searches not found: " + unsucessLinearSearch);
						double linearAvgCompare = (double) totalLinearCompare/countSearch;
						System.out.printf(format, "Total Comparisons: "+totalLinearCompare, "Average Comparisons: " + totalLinearCompare +"/"+countSearch+" = "+ String.format("%.2f", linearAvgCompare));
						System.out.printf(format, "Total CPU Time: "+ String.format("%.4f", totalLinearCPU) + " ms", "Average CPU Time: "+ String.format("%.4f", (totalLinearCPU/countSearch)) + " ms");
					
						System.out.println("---------------------------------------------------------------------------------------");
						System.out.printf(format, "Double Hashing", "Records in Hash table: " + doubleSuccessInsert);
						System.out.printf(format, "Searches found: " + (countSearch-unsucessDblSearch), "Searches not found: " + unsucessDblSearch);
						double dblAvgCompare = (double) totalDoubleCompare/countSearch;
						System.out.printf(format, "Total Comparisons: "+totalDoubleCompare, "Average Comparisons: " + totalDoubleCompare +"/"+countSearch+" = "+ String.format("%.2f", dblAvgCompare));
						System.out.printf(format, "Total CPU Time: "+ String.format("%.4f", totalDoubleCPU) + " ms", "Average CPU Time: "+ String.format("%.4f", (totalDoubleCPU/countSearch)) + " ms");
					}
					
					
					break;
				case 3:
					countSearch = 0;
					totalLinearCompare = 0;
					totalDoubleCompare = 0;
					
					totalLinearCPU = 0;
					totalDoubleCPU = 0;
					
					unsucessLinearSearch = 0;
					unsucessDblSearch = 0;
					
					String path;
					int searchRange = 999;
					
					System.out.println("\n1: Test Successful Search only");
					System.out.println("2: Test unsuccessful Search only");
					System.out.println("3: Mix successful and unsuccessful");
					System.out.print("Enter Choice: ");
					choice = isc.nextInt();
					if(choice==1){
						path = "dataset/1000VehicleNumber.txt";
						searchRange = entriesToAdd - 1 ;
					}
					else if(choice==2) path = "dataset/1000UnsuccessSearch.txt";
					else path = "dataset/1000MixSearchTest.txt";
						
					
					System.out.print("\nEnter number of searches: ");
					countSearch = isc.nextInt();
					
					System.out.println("Searching "+countSearch+" entries...");
					for(int i=0; i<countSearch; i++){
						linearSearchComparison = 0;
						doubleSearchComparison = 0;
						int randomNum = (int)(Math.random()*searchRange); 
						String line = Files.readAllLines(Paths.get(path)).get(randomNum).trim();
						System.out.println("\nSearching "+line+"...");
						System.out.println("Linear Probing:");
						
						startTime = System.nanoTime();
						index = linearSearch(line);
						cpuTime = (System.nanoTime() - startTime) / 1000000.0;
						totalLinearCPU += cpuTime;
						
						
						if(index != -1)
							System.out.println("Found "+line+" at index "+index);
						else{
							unsucessLinearSearch++;
							System.out.println(line+" not found");
						}
							
						System.out.println("Comparisons: "+linearSearchComparison+"     "+"CPU Time: "+ String.format("%.4f", cpuTime) + " ms");
						
						System.out.println("------------------------------------------------------------");
						
						//perform search for Double Hashing
						System.out.println("Double Hashing:");
						startTime = System.nanoTime();
						index = doubleHashSearch(line);
						cpuTime = (System.nanoTime() - startTime) / 1000000.0;
						totalDoubleCPU += cpuTime;

						if(index != -1)
							System.out.println("Found "+line+" at index "+index);
						else{
							unsucessDblSearch++;
							System.out.println(line+" not found");
						}
						System.out.println("Comparisons: "+doubleSearchComparison+"     "+"CPU Time: "+ String.format("%.4f", cpuTime) + " ms");
						totalLinearCompare += linearSearchComparison;
						totalDoubleCompare += doubleSearchComparison;
					}
					
					
					//Generate Statistics report
					if(countSearch > 0 ){
						System.out.println("\nSearch Statistics");
						System.out.println("Number of Searches: "+countSearch);
						System.out.printf(format, "Linear Probing", "Records in Hash table: " + linearSuccessInsert);
						System.out.printf(format, "Searches found: " + (countSearch-unsucessLinearSearch), "Searches not found: " + unsucessLinearSearch);
						double linearAvgCompare = (double) totalLinearCompare/countSearch;
						System.out.printf(format, "Total Comparisons: "+totalLinearCompare, "Average Comparisons: " + totalLinearCompare +"/"+countSearch+" = "+ String.format("%.2f", linearAvgCompare));
						System.out.printf(format, "Total CPU Time: "+ String.format("%.4f", totalLinearCPU) + " ms", "Average CPU Time: "+ String.format("%.4f", (totalLinearCPU/countSearch)) + " ms");
					
						System.out.println("---------------------------------------------------------------------------------------");
						System.out.printf(format, "Double Hashing", "Records in Hash table: " + doubleSuccessInsert);
						System.out.printf(format, "Searches found: " + (countSearch-unsucessDblSearch), "Searches not found: " + unsucessDblSearch);
						double dblAvgCompare = (double) totalDoubleCompare/countSearch;
						System.out.printf(format, "Total Comparisons: "+totalDoubleCompare, "Average Comparisons: " + totalDoubleCompare +"/"+countSearch+" = "+ String.format("%.2f", dblAvgCompare));
						System.out.printf(format, "Total CPU Time: "+ String.format("%.4f", totalDoubleCPU) + " ms", "Average CPU Time: "+ String.format("%.4f", (totalDoubleCPU/countSearch)) + " ms");
					}
					break;
			
				}
			} while (choice < 4);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("\nThank You");
		System.out.println("Program terminating...");

	}

}
