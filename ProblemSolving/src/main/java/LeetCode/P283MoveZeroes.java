package LeetCode;

public class P283MoveZeroes {

	public static void main(String[] args) {
		int[] nums = {0,1,0,3,12};
		moveZeroesSwap(nums);
		
		for(int num: nums) {
			System.out.print(num + " ");
		}
	}
	
	public static void moveZeroesSwap(int[] nums) {
        int n = nums.length;
        for (int i = 0, j = 0; i < n; i++) {
            if(nums[i] != 0){
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
    }
	
	public static void moveZeroes(int[] nums) {
        int n = nums.length;
        int j = 0;
        for (int i = 0; i < n; i++) {
            if(nums[i] != 0){
                nums[j++] = nums[i];
            }
        }
        for(;j<nums.length; j++){
            nums[j] = 0;
        }
    }
	
	public static void moveZeroesNewArr(int[] nums) {
        int n = nums.length;
        int arr[] = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if(nums[i] != 0){
                arr[j++] = nums[i];
            }
        }
        j = 0;
        for(int num: arr){
            nums[j] = arr[j++];
        }
    }

}
