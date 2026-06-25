package LeetCode.Arrays;

/*
 * P3964. Minimum Lights to Illuminate a Road - Medium
 * 
 * You are given an integer array lights of length n, representing positions 0 through n - 1 on a road.
 * 
 * For each position i:
 * 
 * > If lights[i] = v, where v > 0, there is a working bulb at position i that 
 * illuminates every position from max(0, i - v) to min(n - 1, i + v), inclusive.
 * > If lights[i] = 0, there is no working bulb at position i.
 * 
 * A position is visible if it is illuminated by at least one working bulb.
 * 
 * You may install additional bulbs at any positions. Each additional bulb installed 
 * at position j illuminates positions from max(0, j - 1) to min(n - 1, j + 1), inclusive.
 * 
 * Return the minimum number of additional bulbs required to make every position on the road visible.
 * 
 * Approach - Arrays iteration, Greedy
 */
public class P3964MinimumLightsToIlluminateRoad {

	public static void main(String[] args) {
		int[] lights = { 0, 0, 0, 0 };

//		int[] lights = { 0, 0, 0, 2, 0 };

		int minLights = minLights(lights);
		System.out.println("The minimum number of additional bulbs required are: " + minLights);
	}

	public static int minLights(int[] lights) {
		int n = lights.length;

		if (n == 1) {
			if (lights[0] == 0) {
				return 1;
			}
			return 0;
		}

		int[] iplusv = new int[n];
		int[] iminusv = new int[n];

		for (int i = 0; i < n; i++) {
			if (lights[i] > 0) {
				iplusv[i] = Math.min(n - 1, i + lights[i]);
				iminusv[i] = Math.max(0, i - lights[i]);
			}
		}

		int[] illuminated = new int[n];

		for (int i = 0; i < n; i++) {
			if (lights[i] > 0) {
				int start = iminusv[i];
				int end = iplusv[i];

				if (end - start + 1 == n) {
					return 0;
				}

//				if (start != 0 || end != 0) {
				for (int j = start; j <= end; j++) {
					illuminated[j] = 1;
				}
//				}
			}
		}

		int bulbs = 0;
		for (int i = 0; i < n; i++) {
			if (illuminated[i] == 0) {
				bulbs++;
				i += 2;
//				illuminated[i] = 1;
//
//				if (i + 1 < n) {
//					illuminated[i + 1] = 1;
//				}
//				if (i + 2 < n) {
//					illuminated[i + 2] = 1;
//				}
			}
		}

		return bulbs;
	}

}
