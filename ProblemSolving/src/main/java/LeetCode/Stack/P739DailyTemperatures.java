package LeetCode.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

/*
 * P739. Daily Temperatures - Medium
 * 
 * Given an array of integers temperatures represents the daily temperatures, return an array answer 
 * such that answer[i] is the number of days you have to wait after the ith day to get a warmer 
 * temperature. If there is no future day for which this is possible, keep answer[i] == 0 instead.
 * 
 * Approach - Monotonic stack, ArrayDeque; Array Porbing
 * 
 * Monotonic stack - for cases when problem involves comparing size of elements, order is relevant.
 * 
 * ArrayDeque is faster than Stack.
 */
public class P739DailyTemperatures {

	public static void main(String[] args) {
		int[] temperatures = { 73, 74, 75, 71, 69, 72, 76, 73 };

		int[] nextTempProbeArr = dailyTemperaturesProbeArr(temperatures);
		System.out.println("Prove Array: The days to get higher temperature: " + Arrays.toString(nextTempProbeArr));

		int[] nextTempStackArr = dailyTemperaturesMStackArr(temperatures);
		System.out.println(
				"Monotonic Stack Arr: The days to get higher temperature: " + Arrays.toString(nextTempStackArr));

		int[] nextTempStack = dailyTemperaturesMStack(temperatures);
		System.out.println("Monotonic Stack: The days to get higher temperature: " + Arrays.toString(nextTempStack));

		int[] nextTempBF = dailyTemperaturesBF(temperatures);
		System.out.println("Brue Force: The days to get higher temperature: " + Arrays.toString(nextTempBF));
	}

	// Array: Optimized space - Probe the array to get days
	// Temperatures are checked from right side and result is found moving forward.
	// The result array is used to find the days at current index and replace stack.
	// At a given day, there are two cases:
	// 1. The temperature at current index is >= hottest till now
	// In this case update the hottest and continue iterating backwards.
	// 2. The temperature at current index is not the hottest till now.
	// In this case there is a temperature at higher index > current temperature.
	// To obtain the days ahead for warmer temperature start checking at i+(day=1).
	// While the current temperature is not lower than temperature at higher index
	// Update days to be days + result[currentDay + days]
	// This ensures that the next index leads us to higher temperature than them.
	// This helps skip to the higher temperatures and ultimately to correct value.
	// Time complexity - O(2*N), jumps prevents an index from getting visited twice.
	// Space complexity - O(1)
	private static int[] dailyTemperaturesProbeArr(int[] temperatures) {
		int n = temperatures.length;
		int[] result = new int[n];
		int hottest = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (temperatures[i] >= hottest) {
				hottest = temperatures[i];
				continue;
			}
			// Next warmer day will be at least 1 day ahead.
			int days = 1;
			while (temperatures[i] >= temperatures[i + days]) {
				days += result[i + days];
			}
			result[i] = days;

		}
		return result;
	}

	// Faster than Stack and ArrayDeque Collections
	private static int[] dailyTemperaturesMStackArr(int[] temperatures) {
		int n = temperatures.length;
		int[] stack = new int[n];
		int[] result = new int[n];
		int top = -1;
		for (int i = 0; i < n; i++) {
			while (top != -1 && temperatures[i] > temperatures[stack[top]]) {
				result[stack[top]] = i - stack[top--];
			}
			stack[++top] = i;
		}
		return result;
	}

	// Monotonic stack - store monotonically decreasing temperature
	// At a given day, there are two cases:
	// 1. The element at top of stack is not colder than current day's temperature
	// Or current day's tempearture is not warmer than tempearture at top of stack.
	// Simply add the current temperature at top of stack.
	// 2. The element at top of stack is colder than current day's temperature
	// In this case it may happen that the current day's temperature is higher than
	// the temperature at other days stored in the stack.
	// Check element at top of stack and if the current temperature is more then
	// Populate the result with difference between current index and top of stack.
	// Pop the elements at top. Continue until the current day's temperature is
	// not warmer than the temperatute of day at top of stack or stack is empty.
	// After this push the current day index at top of stack.
	// Time complexity - O(2*N) all the element may be pushed once and popped once.
	// Space complexity - O(N) for non increasing temperatures.
	private static int[] dailyTemperaturesMStack(int[] temperatures) {
		Stack<Integer> stack = new Stack<>();
//		Deque<Integer> stack = new ArrayDeque<Integer>();
		int n = temperatures.length;
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			// below code is redundant and expects stack to be not empty
//			if (temperatures[i] <= temperatures[stack.peek()]) {
//				stack.push(i);
//			} else {73, 74, 75, 71, 69, 72, 76, 73
			while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
				result[stack.peek()] = i - stack.pop();
			}
			stack.push(i);
//			}
		}
		return result;
	}

	public static int[] dailyTemperaturesBF(int[] temperatures) {
		int n = temperatures.length;
		int[] result = new int[n];
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (temperatures[j] > temperatures[i]) {
					result[i] = j - i;
					break;
				}
			}
		}
		return result;
	}

}
