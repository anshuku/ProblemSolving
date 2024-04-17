package LeetCode.Arrays;

public class P215KthLargestElementArray {

	public static void main(String[] args) {
		int[] nums = {3,2,1,5,6,4};
		int k = 2;
		
		int kthLargest = findKthLargest(nums, k);
		System.out.println("Kth Largest element " + kthLargest);
	}
	
	public static int findKthLargest(int[] nums, int k) {
        int N = nums.length;
        for(int i = N/2-1; i>=0; i--){
            heapify(nums, i, N);
        }
        int [] arr = new int[N];
        for(int i = N-1; i>0; i--){
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            heapify(nums, 0, i);
        }
        return nums[N-k];
    }

    static void heapify(int [] arr, int num, int N){
        int largest = num;
        int left = 2*num + 1;
        int right = 2*num + 2;

        if(left < N && arr[largest] < arr[left]){
            largest = left;
        }
        if(right < N && arr[largest] < arr[right]){
            largest = right;
        }
        if(num != largest){
            int temp = arr[largest];
            arr[largest] = arr[num];
            arr[num] = temp;
            heapify(arr, largest, N);
        }
    }

}
