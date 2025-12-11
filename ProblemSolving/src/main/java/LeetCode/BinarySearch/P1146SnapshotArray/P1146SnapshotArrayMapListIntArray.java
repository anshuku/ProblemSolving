package LeetCode.BinarySearch.P1146SnapshotArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * P1146 Snapshot Array - Medium
 * 
 * Implement a SnapshotArray that supports the following interface:
 * 
 * > SnapshotArray(int length) initializes an array-like data structure 
 * with the given length. Initially, each element equals 0.
 * 
 * void set(index, val) sets the element at the given index to be equal to val.
 * 
 * int snap() takes a snapshot of the array and returns the 
 * snap_id: the total number of times we called snap() minus 1.
 * 
 * int get(index, snap_id) returns the value at the given index, 
 * at the time we took the snapshot with the given snap_id
 * 
 * Approach - Binary Search, Map
 * 
 * Here after initializing the Snapshot array we can call set, snap and get in any order
 * for any number of times.
 */
public class P1146SnapshotArrayMapListIntArray {

	// Map stores the list of array in [snap_id, value] pair stored as int[]
	// at a given index. Every index stores a list of [snap_id, value] pairs.
	Map<Integer, List<int[]>> map;
	int snap_id;

	P1146SnapshotArrayMapListIntArray(int length) {
		map = new HashMap<>();
		// Intialize each index with default value 0 at snap_id -1.
		for (int i = 0; i < length; i++) {
			List<int[]> list = new ArrayList<>();
			// Putting -1,0 as timestamp, value to enable binary search later
			list.add(new int[] { -1, 0 });
			map.put(i, list);
		}
	}

	// We capture snapshot/use snap_id in set(). It always stores the current
	// snap_id and value. Else just updates the val at the given snap_id.
	public void set(int index, int val) {
		// Get the list at the index
		List<int[]> history = map.get(index);
		int n = history.size();
		// we check the if the current snap_id is present at the end of the List
		// If it's present we just update the val.
		if (history.get(n - 1)[0] == snap_id) {
			history.get(n - 1)[1] = val; // override the latest change in this snap_id
		} else {
			// If the snap_id is not at the end we add the snap_id, val at end
			history.add(new int[] { snap_id, val });
		}
	}

	// It increments the snap_id only and returns it.
	public int snap() {
		return snap_id++;
	}

	// To traverse the List<int[]> we need to use Binary search
	// Binary search helps to find the value at that snapshot.
	// Here snap_id may not be present in the list so we use binary search to find
	// the record with the highesht snap_id <= given snap_id.
	public int get(int index, int snap_id) {
		List<int[]> history = map.get(index);
		int start = 0;
		int end = history.size() - 1;
		int ans = 0;
		// Upper bound binary search to find the snap_id <= given snap_id
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (history.get(mid)[0] <= snap_id) {
				ans = history.get(mid)[1];
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {

		P1146SnapshotArrayMapListIntArray snapshot = new P1146SnapshotArrayMapListIntArray(3);
		snapshot.set(0, 5);
		System.out.println(snapshot.snap());
		snapshot.set(0, 6);
		System.out.println(snapshot.get(0, 0));

//		P1146SnapshotArrayMapListIntArray snapshot = new P1146SnapshotArrayMapListIntArray(1);
//		snapshot.set(0, 4);
//		snapshot.set(0, 16);
//		snapshot.set(0, 13);
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(0, 0));
//		System.out.println(snapshot.snap());

//		P1146SnapshotArrayMapListIntArray snapshot = new P1146SnapshotArrayMapListIntArray(4);
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(3, 1));
//		snapshot.set(2, 4);
//		System.out.println(snapshot.snap());
//		snapshot.set(1, 4);

//		P1146SnapshotArrayMapListIntArray snapshot = new P1146SnapshotArrayMapListIntArray(1);
//		snapshot.set(0, 15);
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(0, 2));
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(0, 0));
	}

}
