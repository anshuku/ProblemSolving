package LeetCode.Math;

/*
 * P3783. Mirror Distance of an Integer - Easy
 * 
 * You are given an integer n.
 * 
 * Define its mirror distance as: abs(n - reverse(n))​​​​​​​ where 
 * reverse(n) is the integer formed by reversing the digits of n.
 * 
 * Return an integer denoting the mirror distance of n​​​​​​​.
 * 
 * abs(x) denotes the absolute value of x.
 * 
 * Approach - Math
 */
public class P3783MirrorDistanceOfAnInteger {

	public static void main(String[] args) {
//		int n = 1;
		int n = 25;
//		int n = 10;
//		int n = 7;
//		int n = 111;

		int mirrorDistance = mirrorDistance(n);
		System.out.println("The mirror distance of n is: " + mirrorDistance);
	}

	public static int mirrorDistance(int n) {
		int num = n;

		int reverse = 0;
		while (num > 0) {
			reverse = reverse * 10 + num % 10;
			num /= 10;
		}
		return Math.abs(reverse - n);
	}
}
