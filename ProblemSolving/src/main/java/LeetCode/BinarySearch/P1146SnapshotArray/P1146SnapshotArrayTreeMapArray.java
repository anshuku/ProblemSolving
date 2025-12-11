package LeetCode.BinarySearch.P1146SnapshotArray;

import java.util.Map;
import java.util.TreeMap;

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
 * 
 * We focus on historical record of each element and record the value of the 
 * modified element when set is called. This reduces the memory required to store the history 
 * of the array's elements and improve query times for specific snapshots since we save an
 * element nums[i] only when it's modified by set operation. 
 * For this we create a list/map of records for each index i. A record contains the snapshot id
 * and the value of the element in that snapshot, in the format of (snap_id, nums[i]). 
 * We store this in an array where the key is i. We collect every record of nums[i] in records[i].
 * To retrieve the value of a specific nums[i] with the given snapshot snap_id = 2, we find 
 * insertion position of snap_id in the list/map of records for nums[0]. It may happen that snap_id
 * is not present in the list/map. We use binary search to find the record with highest snapshot id
 * that is <= snap_id.
 */
public class P1146SnapshotArrayTreeMapArray {

	TreeMap<Integer, Integer>[] map;
	int snap_id = 0;

	P1146SnapshotArrayTreeMapArray(int length) {
		map = new TreeMap[length];
		for (int i = 0; i < length; i++) {
			TreeMap<Integer, Integer> entry = new TreeMap<>();
			entry.put(0, 0);
			map[i] = entry;
		}
	}

	public void set(int index, int val) {
		map[index].put(snap_id, val);
	}

	public int snap() {
		return snap_id++;
	}

	public int get(int index, int snap_id) {
		Map.Entry<Integer, Integer> entry = map[index].floorEntry(snap_id);
		return entry.getValue();
	}

	public static void main(String[] args) {
		P1146SnapshotArrayTreeMapArray snapshot = new P1146SnapshotArrayTreeMapArray(3);
		snapshot.set(0, 5);
		System.out.println(snapshot.snap());
		snapshot.set(0, 6);
		System.out.println(snapshot.get(0, 0));

//		P1146SnapshotArrayTreeMapArray snapshot = new P1146SnapshotArrayTreeMapArray(1);
//		snapshot.set(0, 4);
//		snapshot.set(0, 16);
//		snapshot.set(0, 13);
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(0, 0));
//		System.out.println(snapshot.snap());

//		P1146SnapshotArrayTreeMapArray snapshot = new P1146SnapshotArrayTreeMapArray(4);
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.snap());
//		System.out.println(snapshot.get(3, 1));
//		snapshot.set(2, 4);
//		System.out.println(snapshot.snap());
//		snapshot.set(1, 4);

//		P1146SnapshotArrayTreeMapArray snapshot = new P1146SnapshotArrayTreeMapArray(1);
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
