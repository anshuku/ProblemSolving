package LeetCode.Arrays;

public class P1137NthTribonacciNumber {

	public static void main(String[] args) {

		int num = 5;
		int tr = tribonacci(num);
//		int tr = tribonacciAlt(num);// 7 for num = 5
		System.out.print("The the nth tribonacci number is " + tr);

	}

	public static int tribonacci(int n) {
		if (n <= 1) {
			return n;
		}
		if (n == 2) {
			return 1;
		}
		int[] arr = new int[n + 1];
		arr[0] = 0;
		arr[1] = 1;
		arr[2] = 1;
		for (int i = 3; i <= n; i++) {
			arr[i] = arr[i - 1] + arr[i - 2] + arr[i - 3];
		}
		return arr[n];
	}

	private static int tribonacciAlt(int n) {
		if (n < 2) {
			return n;
		}
		int arr[] = new int[] { 0, 1, 1 };
		for (int i = 3; i <= n; i++) {
			int next = arr[0] + arr[1] + arr[2];
			arr[0] = arr[1];
			arr[1] = arr[2];
			arr[2] = next;
		}
		return arr[2];
	}

}
