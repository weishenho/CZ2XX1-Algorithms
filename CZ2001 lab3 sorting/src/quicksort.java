public class quicksort {
	
	
	private static int slot[];
	private static int comparisons;
	
	//start quickstorting
	public static int sort(int[] a){
		slot = a;
		comparisons = 0;
		quicksort(0, slot.length-1);
		return comparisons;
	}
	
	private static void quicksort(int n, int m)
	{
		
		if(n>=m) return;
		
		int pivot_pos = partition(n, m);
		
		quicksort(n, pivot_pos-1);
		quicksort(pivot_pos+1, m);
		
	}

	private static int partition(int low, int high) {
		int ls, pivot;
		
		int mid = (low+high)/2;
		
		swap(low, mid);
		
		pivot = slot[low];
		ls = low;
		
		//compare array elements with pivot and swap if necessary
		for(int i=low+1; i<=high; i++)
		{
			comparisons++;
			if(slot[i] < pivot)
				swap(i, ++ls);
		}
		swap(low, ls);
		
		return ls;
	}

	private static void swap(int a, int b) {
		int temp = slot[a];
		slot[a] = slot[b];
		slot[b] = temp;	
	}

}
