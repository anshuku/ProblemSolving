package LeetCode.Queue;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P933. Number of Recent Calls - Easy
 * 
 * You have a RecentCounter class which counts the number of recent requests within a certain time frame.
 * Implement the RecentCounter class:
 * - RecentCounter() Initializes the counter with zero recent requests.
 * - int ping(int t) Adds a new request at time t, where t represents some time in milliseconds, 
 *  and returns the number of requests that has happened in the past 3000 milliseconds 
 *  (including the new request). Specifically, return the number of requests that have 
 *  happened in the inclusive range [t - 3000, t].
 *  
 *  It is guaranteed that every call to ping uses a strictly larger value of t than the previous call.
 *  
 *  Approach - Queue, int[]
 */
public class P933NumberRecentCalls {

	Queue<Integer> queue = new LinkedList<>();

	// size of records is 10^4, since at most 10^4 calls are made
	private int[] records = new int[10000];
	private int start, end;

	public P933NumberRecentCalls() {
		queue = new LinkedList<>();
		start = 0;
		end = 0;
	}

	// Queue stores even big numbers one after the other
	// After adding the current number, check if the head of the queue has a
	// number within the range of current time and time - 3000 and poll accordingly
	// The size gives the number of requests within last 3000ms.
	public int pingQueue(int t) {

		queue.add(t);
		int start = t - 3000;

		while (queue.peek() < start) {
			queue.poll();
		}
		return queue.size();
	}

	// start and end are indices which keep track of size
	// the bigger numbers are stored one after the other
	public int pingRecords(int t) {
		while (start < end && (t - records[start] > 3000)) {
			start++;
		}
		records[end++] = t;
		return end - start;
	}

	public static void main(String[] args) {

		P933NumberRecentCalls q = new P933NumberRecentCalls();

		int request1 = q.pingQueue(1);
		System.out.println("Queue: The number of requests in last 3000 seconds " + request1);

		int request2 = q.pingQueue(3);
		System.out.println("Queue: The number of requests in last 3000 seconds " + request2);

		int request3 = q.pingQueue(1000);
		System.out.println("Queue: The number of requests in last 3000 seconds " + request3);

		int request4 = q.pingQueue(3001);
		System.out.println("Queue: The number of requests in last 3000 seconds " + request4);

		int request5 = q.pingQueue(3002);
		System.out.println("Queue: The number of requests in last 3000 seconds " + request5);

		int requestRecords1 = q.pingRecords(1);
		System.out.println("Records: The number of requests in last 3000 seconds " + requestRecords1);

		int requestRecords2 = q.pingRecords(3);
		System.out.println("Records: The number of requests in last 3000 seconds " + requestRecords2);

		int requestRecords3 = q.pingRecords(1000);
		System.out.println("Records: The number of requests in last 3000 seconds " + requestRecords3);

		int requestRecords4 = q.pingRecords(3001);
		System.out.println("Records: The number of requests in last 3000 seconds " + requestRecords4);

		int requestRecords5 = q.pingRecords(3002);
		System.out.println("Records: The number of requests in last 3000 seconds " + requestRecords5);

	}

}
