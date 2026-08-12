package LeetCode.Strings;

/*
 * P3986. Number of Elapsed Seconds Between Two Times - Easy
 * 
 * You are given two valid times startTime and endTime, each represented as a string in the format "HH:MM:SS".
 * 
 * Return the number of seconds that have elapsed from startTime to endTime.
 * 
 * Approach - String, Clock Maths
 */
public class P3986NumberOfElapsedSecondsBetweenTwoTimes {

	public static void main(String[] args) {
//		String startTime = "01:00:00";
//		String endTime = "01:00:25";

		String startTime = "12:34:56";
		String endTime = "13:00:00";

		int seconds = secondsBetweenTimes(startTime, endTime);
		System.out.println("The seconds between the given start and end time is: " + seconds);
	}

	// Time complexity - O(1)
	// Space complexity - O(1)
	public static int secondsBetweenTimes(String startTime, String endTime) {
		String[] timeStart = startTime.split(":");
		String[] timeEnd = endTime.split(":");

		int hours = Integer.parseInt(timeEnd[0]) - Integer.parseInt(timeStart[0]);

		int minutes = Integer.parseInt(timeEnd[1]) - Integer.parseInt(timeStart[1]);
		if (minutes < 0) {
			minutes += 60;
			hours--;
		}

		int seconds = Integer.parseInt(timeEnd[2]) - Integer.parseInt(timeStart[2]);
		if (seconds < 0) {
			seconds += 60;
			minutes--;
		}

		return hours * 3600 + minutes * 60 + seconds;
	}

}
