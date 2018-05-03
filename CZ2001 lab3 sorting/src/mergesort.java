public class mergesort {

	private static int comparisons;

	//start mergesorting
	public static int sort(int[] slot) {
		comparisons = 0;
		mergesort(slot);
		return comparisons;
	}

	//divide the array then merge
	private static void mergesort(int[] slot) {
		int size = slot.length;

		if (size < 2)
			return;

		
		//split the array into two
		int mid = size / 2;
		int leftsize = mid;
		int rightsize = size - mid;
		int[] left = new int[leftsize];
		int[] right = new int[rightsize];

		for (int i = 0; i < mid; i++) {
			left[i] = slot[i];

		}
		for (int i = mid; i < size; i++) {
			right[i - mid] = slot[i];
		}
		
		//recursively split the array
		mergesort(left);
		mergesort(right);
		merge(left, right, slot);

	}

	private static void merge(int[] left, int[] right, int[] arr) {
		int leftSize = left.length;
		int rightSize = right.length;
		int i = 0, j = 0, k = 0;
		
		//compare between 2 array
		while (i < leftSize && j < rightSize) {
			comparisons++;
			if (left[i] <= right[j]) {
				arr[k] = left[i];
				i++;
				k++;
			} else {
				arr[k] = right[j];
				k++;
				j++;
			}
		}
		
		//carry over the left overs
		while (i < leftSize) {
			arr[k] = left[i];
			k++;
			i++;
		}
		while (j < leftSize) {
			arr[k] = right[j];
			k++;
			j++;
		}

	}// end of merge

}
