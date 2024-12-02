package LeetCode.Stack;

import java.util.Arrays;
import java.util.Stack;

/*
 * P735. Asteroid Collision - Medium
 * 
 * We are given an array asteroids of integers representing asteroids in a row.
 * For each asteroid, the absolute value represents its size, and the sign represents 
 * its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.
 * Find out the state of the asteroids after all collisions. If two asteroids meet, 
 * the smaller one will explode. If both are the same size, both will explode. 
 * Two asteroids moving in the same direction will never meet.
 * 
 * Approach - Stack
 */
public class P735AsteroidCollision {

	public static void main(String[] args) {
		int[] asteroids = { 5, 10, -5 };

//		int[] asteroids = { 8, -8 };

//		int[] asteroids = { -2, -1, 1, 2 };

//		int[] asteroids = { 1, -1, -2, -2 };

		int[] remains = asteroidCollision(asteroids);

		System.out.println("The remaining asteroids after collision: " + Arrays.toString(remains));

	}

	public static int[] asteroidCollision(int[] asteroids) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < asteroids.length; i++) {
			if (!stack.isEmpty() && (asteroids[i] < 0 && stack.peek() > 0)) {// 5 10 -5
				int val = 0;
				while (!stack.isEmpty() && stack.peek() > 0) {
					val = stack.peek() + asteroids[i];
					if (val == 0) {
						stack.pop();
						break;
					} else if (val > 0) {
						break;
					}
					stack.pop();
				}
				if (val < 0) {
					stack.push(asteroids[i]);
				}
			} else {
				stack.push(asteroids[i]);
			}
		}

		int[] result = new int[stack.size()];
		int i = 0;
		for (int num : stack) {
			result[i++] = num;
		}
		return result;
		// Lazy loaded and takes more time
//		return stack.stream().mapToInt(Integer::intValue).toArray();
	}

}
