package LeetCode.Arrays;

import java.util.Arrays;

/*
 * Problem#42 - Trapping Rain Water
 * Difficulty: Hard
 * 
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining.
 * 
 * Optimized Approach - Use two pointers
 * Time Complexity - O(n)
 * Space Complexity - O(n)
 */
public class P42TrappingRainWater {

	public static void main(String[] args) {

		int[] height = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };// 6
//		int[] height = { 4, 2, 0, 3, 2, 5 };// 9
//		int[] height = { 4, 2, 3 };// 1

		P42TrappingRainWater ptr = new P42TrappingRainWater();

		int area = ptr.trapTwoPointer(height);

//		int area = ptr.trapTwoPointerPrevious(height);

//		int area = ptr.trapArray(height);

//		int area = ptr.trapArrayPrevious(height);

//		int area = ptr.trapPeak(height);

//		int area = ptr.trapInputs(height);
		System.out.println("Max trapped rainwater is " + area);
	}

	private int trapPeak(int[] height) {
		int n = height.length;
		if (n < 3) {
			return 0;
		}
		int leftMost = 0, rightMost = 0;
		int peak = 0;
		int water = 0;
		for (int i = 0; i < n; i++) {
			if (height[peak] < height[i])
				peak = i;
		}
		for (int i = 0; i < peak; i++) {
			if (leftMost < height[i]) {
				leftMost = height[i];
			} else {
				water += leftMost - height[i];
			}
		}
		for (int i = n - 1; i >= peak; i--) {
			if (rightMost < height[i]) {
				rightMost = height[i];
			} else {
				water += rightMost - height[i];
			}
		}
		return water;
	}

	private int trapTwoPointer(int[] height) {
		int n = height.length;
		if (n < 3) {
			return 0;
		}
		int left = 0, right = n - 1;
		int maxLeft = height[left], maxRight = height[right];
		int area = 0;
		while (left < right) {
			if (maxLeft < maxRight) {
				left++;
				maxLeft = Math.max(maxLeft, height[left]);
				area += maxLeft - height[left];
			} else {
				right--;
				maxRight = Math.max(maxRight, height[right]);
				area += maxRight - height[right];
			}
		}
		return area;
	}

	// Most optimized
	private int trapTwoPointerPrevious(int[] height) {
		int n = height.length;
		if (n < 2) {
			return 0;
		}
		int area = 0;
		int left = 0, right = n - 1;
		int maxLeft = height[left], maxRight = height[right];

		while (left < right) {
			if (maxLeft < maxRight) {
				left++;
				int currArea = maxLeft - height[left];
				if (currArea > 0) {
					area += currArea;
				}
				maxLeft = Math.max(maxLeft, height[left]);
			} else {
				right--;
				int currArea = maxRight - height[right];
				if (currArea > 0) {
					area += currArea;
				}
				maxRight = Math.max(maxRight, height[right]);
			}
		}
		return area;
	}

	private int trapArray(int[] height) {
		int n = height.length;
		if (n < 3) {
			return 0;
		}
		int[] left = new int[n];
		left[0] = height[0];// no boundary and water spills
		for (int i = 1; i < n; i++) {
			left[i] = Math.max(height[i], left[i - 1]);
		}
		System.out.println("The left array is " + Arrays.toString(left));

		int[] right = new int[n];
		right[n - 1] = height[n - 1];// no boundary and water spills
		for (int i = n - 2; i >= 0; i--) {
			right[i] = Math.max(height[i], right[i + 1]);
		}
		System.out.println("The right array is " + Arrays.toString(right));

		int maxArea = 0;
		for (int i = 0; i < n; i++) {
			maxArea += Math.min(left[i], right[i]) - height[i];
		}
		return maxArea;
	}

	private int trapArrayPrevious(int[] height) {
		int n = height.length;
		if (n < 3) {
			return 0;
		}
		int area = 0;
		int left[] = new int[n];
		left[0] = height[0];
		for (int i = 1; i < n; i++) {
			left[i] = Math.max(left[i - 1], height[i - 1]);
		}
		int right[] = new int[n];
		right[n - 1] = height[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			right[i] = Math.max(right[i + 1], height[i + 1]);
		}
		for (int i = 0; i < n; i++) {
			int currArea = Math.min(left[i], right[i]) - height[i];
			if (currArea > 0) {
				area += currArea;
			}
		}
		return area;
	}

	// Fails for input {4,2,3}
	public int trap(int[] height) {
		int n = height.length;
		if (n < 2) {
			return 0;
		}
		int maxArea = 0;

		for (int i = 1; i < n; i++) {
			if (height[i] < height[i - 1]) {
				int left = i - 1;
				i++;
				while (i < n && height[i] < height[left]) {
					i++;
				}
				int right = i;
				int area = 0;
				if (i < n && height[right] >= height[left]) {
					for (int j = left + 1; j < right; j++) {
						area += height[left] - height[j];
					}
					maxArea += area;
				} else {
					i = left + 1;
				}
			}
		}
		return maxArea;
	}

}
