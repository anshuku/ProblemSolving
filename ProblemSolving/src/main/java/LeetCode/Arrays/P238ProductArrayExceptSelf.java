package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anshukumar
 * 
 *         Array population via For loop is faster than Arrays.fill()
 * 
 */
public class P238ProductArrayExceptSelf {

	public static void main(String[] args) {
		int[] nums = { -1, 1, 0, -3, 3 };
//		int[] nums = {1, 2, 3, 4};
		int[] prodExceptSelf = productExceptSelfSO(nums);

		System.out.println("The product except self array is " + Arrays.toString(prodExceptSelf));
	}

	public static int[] productExceptSelfSO(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];

		
		//This is faster than Arrays.fill(result, 1);
		for (int i = 0; i < n; i++) {
			result[i] = 1;
		}
		int curr = 1;
 
		for (int i = 0; i < n; i++) {
			result[i] = curr * result[i];
			curr = curr * nums[i];
		}
		System.out.println("the result array is " + Arrays.toString(result));
		curr = 1;
		for (int i = n - 1; i >= 0; i--) {
			result[i] = curr * result[i];
			curr = curr * nums[i];
		}
		System.out.println("the result array is " + Arrays.toString(result));
		return result;
	}

	public static int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] pref = new int[n];
		int[] suff = new int[n];

		pref[0] = 1;
		suff[n - 1] = 1;

		for (int i = 1; i < n; i++) {
			pref[i] = pref[i - 1] * nums[i - 1];
		}
		System.out.println("the pref array is " + Arrays.toString(pref));

		for (int i = n - 2; i >= 0; i--) {
			suff[i] = suff[i + 1] * nums[i + 1];
		}
		System.out.println("the suff array is " + Arrays.toString(suff));

		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = pref[i] * suff[i];
		}
		return result;
	}

	public static int[] productExceptSelfListBF(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		int j = 0;
		List<Integer> list = new ArrayList<>();
		list = Arrays.stream(nums).boxed().collect(Collectors.toList());
		for (int i = 0; i < n; i++) {
			list.remove(i);
			System.out.println("list " + list.toString());
			int num = list.stream().reduce((a, b) -> a * b).get();
			result[i] = num;
			System.out.println("The product is " + num + " and removed value is " + nums[i]);
			list.add(i, nums[i]);
		}
		return result;
	}

	public static int[] productExceptSelfBF(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		int prod = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j == i) {
					continue;
				}
				prod = prod * nums[j];
			}
			result[i] = prod;
			prod = 1;
		}
		return result;
	}

	public static int[] productExceptSelfDivision(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		int prod = 1;

		for (int num : nums) {
			prod *= num;
		}
		for (int i = 0; i < n; i++) {
			result[i] = prod / nums[i];
		}
		return result;
	}

}
