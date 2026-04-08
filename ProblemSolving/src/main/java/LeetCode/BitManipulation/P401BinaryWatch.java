package LeetCode.BitManipulation;

import java.util.ArrayList;
import java.util.List;

/*
 * P401. Binary Watch - Easy
 * 
 * A binary watch has 4 LEDs on the top to represent the hours (0-11), 
 * and 6 LEDs on the bottom to represent the minutes (0-59). Each LED 
 * represents a zero or one, with the least significant bit on the right.
 * 
 * - For example, the below binary watch reads "4:51".
 * 
 * Given an integer turnedOn which represents the number of LEDs that 
 * are currently on (ignoring the PM), return all possible times the 
 * watch could represent. You may return the answer in any order.
 * 
 * The hour must not contain a leading zero.
 * - For example, "01:00" is not valid. It should be "1:00".
 * 
 * The minute must consist of two digits and may contain a leading zero.
 * - For example, "10:2" is not valid. It should be "10:02".
 * 
 * Approach - Bit Manipulation
 * 
 * 8 4 2 1 | sum = 15 | range 0-11
 * 0-1 | 0:00
 * 1-4 | 1:00, 2:00, 4:00, 8:00
 * 2-5 | 3:00, 5:00, 9:00, 6:00, 10:00
 * 3-2 | 7:00, 11:00
 * 4-0 |
 * 32 16 8 4 2 1 | sum = 63 | range 0-59
 * 0-1 | 0:00
 * 1-6 | 0:01, 0:02, 0:04, 0:08, 0:16, 0:32
 * 2-15 | 0:03, 0:05, 0:09, 0:17, 0:33, 0:06, 0:10, 0:18, 0:34, 0:12, 0:20, 0:36, 0:24, 0:40, 0:48
 * 3-
 * 4-
 * 5-
 * 6-0
 */
public class P401BinaryWatch {

	public static void main(String[] args) {
		int turnedOn = 1;
//		int turnedOn = 9;
//		int turnedOn = 0;

		List<String> possibleTimesBinaryEnum = readBinaryWatchBinaryEnum(turnedOn);
		System.out
				.println("Binary enumeration: The possible times with the given turnedOn: " + possibleTimesBinaryEnum);

		List<String> possibleTimesEnumHrMins = readBinaryWatchEnumHrMins(turnedOn);
		System.out.println("Enum hour minutes: The possible times with the given turnedOn: " + possibleTimesEnumHrMins);
	}

	// Binary Enumeration - bitmasking
	// We consider all the 2^10 = 1024 possible configurations of the ligths. Each
	// configuration can be represented by a 10-bit binary number, where the higher
	// 4 bits represent the hour and the lower 6 bits represent the minute. For each
	// configuration, we extract the hour and minute values using bitwise
	// operations. If both values fall within their valid ranges and the total
	// number of 1s in the binary representation equals turnedOn, we add the
	// corresponding time to the answer.
	// Time complexity - O(1), the total number of enumerations is constant and
	// independent of input.
	// Space complexity - O(1), we don't consider space required for output.
	private static List<String> readBinaryWatchBinaryEnum(int turnedOn) {
		List<String> result = new ArrayList<>();
		if (turnedOn > 8) {
			return result;
		}
		// enumerate all bitmasks and filter using bitCount
		for (int i = 0; i < 1024; i++) {
			int h = i >> 6; // right 6 bits are removed and gives upper 4 bits
			int m = i & 63; // left 4 bits are removed and gives lower 6 bits
			if (h < 12 && m < 60 && Integer.bitCount(i) == turnedOn) { // Can check number of set bits == turnedOn early
				result.add(h + ":" + (m < 10 ? "0" : "") + m);
			}
		}
		return result;
	}

	// Enumerating Hours and Minutes
	// As per the problem, hour is represented using 4 bits and the minute is
	// represented using 6 bits. The bit value of 0 means that the light is off,
	// while a bit value of 1 indicates that the light is on.
	// We can enumerate all possible hour values[0, 11] and minute values[0, 59].
	// For each combination, we compure the total number of 1s in their binary
	// representations. If this total equals turnedOn, we add the corresponding time
	// to the answer.
	public static List<String> readBinaryWatchEnumHrMins(int turnedOn) {
		List<String> result = new ArrayList<>();
		if (turnedOn > 8) {
			return result;
		}
		for (int h = 0; h < 12; h++) {
			for (int m = 0; m < 60; m++) {
				if (Integer.bitCount(m) + Integer.bitCount(h) == turnedOn) {
					result.add(h + ":" + (m < 10 ? "0" : "") + m);
				}
			}
		}
		return result;
	}

}
