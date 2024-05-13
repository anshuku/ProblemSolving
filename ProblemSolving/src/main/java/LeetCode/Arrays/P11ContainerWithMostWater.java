package LeetCode.Arrays;

public class P11ContainerWithMostWater {

	public static void main(String[] args) {

		int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
		P11ContainerWithMostWater pcw = new P11ContainerWithMostWater();
//		int maxArea = pcw.maxArea(height);
		int maxArea = pcw.maxAreaOpt(height);

		System.out.println("The container with most water has area " + maxArea);
	}

	private int maxAreaOpt(int[] height) {
		int n = height.length;
		int left = 0;
		int right = n - 1;
		int area = 0;
		while (left < right) {
			int heightNew = Math.min(height[left], height[right]);
			int areaNew = heightNew * (right - left);
			area = Math.max(area, areaNew);
			while (left < right && height[left] <= heightNew) {
				left++;
			}
			while (left < right && height[right] <= heightNew) {
				right--;
			}
		}
		return area;
	}

	public int maxArea(int[] height) {
		int n = height.length;
		int left = 0;
		int right = n - 1;
		int area = 0;

		while (left < right) {
			int areaNew = Math.min(height[left], height[right]) * (right - left);
			area = Math.max(area, areaNew);
			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
		}
		return area;
	}

}
