package LeetCode.MonotonicStack;

import java.util.Arrays;
import java.util.Stack;

/*
 * P853. Car Fleet - Medium
 * 
 * There are n cars at given miles away from the starting mile 0, traveling to reach the mile target.
 * 
 * You are given two integer arrays position and speed, both of length n, where position[i] is 
 * the starting mile of the ith car and speed[i] is the speed of the ith car in miles per hour.
 * 
 * A car cannot pass another car, but it can catch up and then travel next to it at the speed of the slower car.
 * 
 * A car fleet is a single car or a group of cars driving next to each other. 
 * The speed of the car fleet is the minimum speed of any car in the fleet.
 * 
 * If a car catches up to a car fleet at the mile target, it will still be considered as part of the car fleet.
 * 
 * Return the number of car fleets that will arrive at the destination.
 * 
 * Constraints:
 * > n == position.length == speed.length
 * > 1 <= n <= 10^5
 * > 0 < target <= 10^6
 * > 0 <= position[i] < target
 * > All the values of position are unique.
 * > 0 < speed[i] <= 10^6
 * 
 * Approach - Array Sorting, Monotonic Stack
 * 
 * double time = (double) (target - position[i]) / speed[i];
 * Here, double type cast at right expression is important
 */
public class P853CarFleet {

	public static class Car {
		int position;
		double time;

		Car(int position, double time) {
			this.position = position;
			this.time = time;
		}
	}

	public static void main(String[] args) {
//		int target = 12;
//		int[] position = { 10, 8, 0, 5, 3 };
//		int[] speed = { 2, 4, 1, 1, 3 };

//		int target = 10;
//		int[] position = { 3 };
//		int[] speed = { 3 };

//		int target = 100;
//		int[] position = { 0, 2, 4 };
//		int[] speed = { 4, 2, 1 };

		int target = 10;
		int[] position = { 6, 8 };
		int[] speed = { 3, 2 };

		int fleetCountObject = carFleetObject(target, position, speed);
		System.out.println("Object: The number of car fleets that will arrive at destination: " + fleetCountObject);

		int fleetCountDoubleArray = carFleetDoubleArray(target, position, speed);
		System.out.println(
				"Double Array: The number of car fleets that will arrive at destination: " + fleetCountDoubleArray);
	}

	// Sort
	// The lead fleet is the fleet furthest in position (rightmost) tracked with
	// position array. If the car S (Second) behind the lead car F (First) would
	// arrive earlier (tracked by calculating time = (target - position)/speed),
	// then S forms a fleet with the lead car F and the fleet moves at speed of F
	// (slower ATQ). Otherwise, fleet F is final as no car catch upto it - cars
	// behind S would form fleets with S, never F.
	// Algorithm:
	// We use Car (position, time) array and sort the cars by position. We iterate
	// in reverse order. If the lead fleet drives away, then count it and continue.
	// Otherwise, merge the fleets and continue.
	// Time complexity - O(nlogn), as complexity is dominated by sorting of array.
	// Space complexity - O(n), to store the Car array.
	public static int carFleetObject(int target, int[] position, int[] speed) {
		int n = speed.length;

		Car[] cars = new Car[n];

		for (int i = 0; i < n; i++) {
			double time = (double) (target - position[i]) / speed[i];
			cars[i] = new Car(position[i], time);
		}

		Arrays.sort(cars, (a, b) -> Integer.compare(a.position, b.position));

//		int count = 1; // lone car is fleet (if only single car)
//		for (int i = n - 1; i > 0; i--) {
//			if (cars[i - 1].time <= cars[i].time) { // if cars[i-1] arrives at same time as cars[i]
//				cars[i - 1] = cars[i];
//			} else { // else, cars[i] arrives sooner, it can't be caught.
//				count++;
//			}
//		}

//		int count = 0;
//		double time = 0;

//		for (int i = n - 1; i >= 0; i--) {
//			if (cars[i].time > time) {
//				time = cars[i].time;
//				count++;
//			}
//		}

//		return count;

		Stack<Double> stack = new Stack<>();

		for (int i = n - 1; i >= 0; i--) {
			if (!stack.isEmpty() && cars[i].time <= stack.peek()) {
				continue;
			}
			stack.push(cars[i].time);
		}

		return stack.size();
	}

	private static int carFleetDoubleArray(int target, int[] position, int[] speed) {
		int n = position.length;
		double[][] cars = new double[n][2];

		for (int i = 0; i < n; i++) {
			double time = (double) (target - position[i]) / speed[i];
			cars[i] = new double[] { position[i], time };
		}

		Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));

//		int count = 1;
//		for (int i = n - 1; i > 0; i--) {
//			if (cars[i - 1][1] > cars[i][1]) {
//				count++;
//			} else {
//				cars[i - 1] = cars[i];
//			}
//		}

//		int count = 0;
//		double time = 0;

//		for (int i = n - 1; i >= 0; i--) {
//			if (cars[i][1] > time) {
//				time = cars[i][1];
//				count++;
//			}
//		}

//		return count;

		Stack<Double> stack = new Stack<>();

		for (int i = n - 1; i >= 0; i--) {
			if (!stack.isEmpty() && cars[i][1] <= stack.peek()) {
				continue;
			}
			stack.push(cars[i][1]);
		}

		return stack.size();
	}

}
