package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * P1203. Sort Items by Groups Respecting Dependencies - Hard
 * 
 * There are n items each belonging to zero or one of m groups where group[i] is the group 
 * that the i-th item belongs to and it's equal to -1 if the i-th item belongs to no group. 
 * The items and the groups are zero indexed. A group can have no item belonging to it.
 * 
 * Return a sorted list of the items such that:
 * 
 * > The items that belong to the same group are next to each other in the sorted list.
 * > There are some relations between these items where beforeItems[i] is a list containing all 
 * the items that should come before the i-th item in the sorted array (to the left of the i-th item).
 * 
 * Return any solution if there is more than one solution and return an empty list if there is no solution.
 * 
 * Approach - Kahn's Algo: Topological sorting
 */
public class P1203SortItemsByGroupsRespectingDependencies {

	public static void main(String[] args) {
		int n = 8; // [6,3,4,1,5,2,0,7]
		int m = 2;
		int[] group = { -1, -1, 1, 0, 0, 1, 0, -1 };
		int[] group2 = Arrays.copyOf(group, n);
		List<List<Integer>> beforeItems = Arrays.asList(Arrays.asList(), Arrays.asList(6), Arrays.asList(5),
				Arrays.asList(6), Arrays.asList(3, 6), Arrays.asList(), Arrays.asList(), Arrays.asList());

//		int n = 8; // []
//		int m = 2;
//		int[] group = { -1, -1, 1, 0, 0, 1, 0, -1 };
//		int[] group2 = Arrays.copyOf(group, n);
//		List<List<Integer>> beforeItems = Arrays.asList(Arrays.asList(), Arrays.asList(6), Arrays.asList(5),
//				Arrays.asList(6), Arrays.asList(3), Arrays.asList(), Arrays.asList(4), Arrays.asList());

		int[] sortedItemsOpt = sortItemsOpt(n, m, group, beforeItems);
		System.out.println("Opt: The sorted list of items are: " + Arrays.toString(sortedItemsOpt));

		int[] sortedItems = sortItems(n, m, group2, beforeItems);
		System.out.println("The sorted list of items are: " + Arrays.toString(sortedItems));
	}

	// Kahn's Algo: Topological sorting
	// Sort items according to item dependencies and Sort groups according to
	// inter-group dependencies. We then combine them.
	// We don't need to toplogically sort items separately inside each group.
	// Instead:
	// 1. Topologically sort all items globally.
	// 2. Topologically sort groups globally.
	// 3. Collect items by group while preserving the global item order.
	// 4. Output groups in group-topological order.
	// Because the global item order already respects every item dependency, and the
	// group order already respects every cross-group dependency, simply
	// concatenating the gouped items produces a valid final answer.
	// the groupId handles this piece of problem statement "where group[i] is the
	// group that the i-th item belongs to and it's equal to -1 if the i-th item
	// belongs to no group." also "A group can have no item belonging to it."
	// As a candidate one must recognize it quickly. The trick is not to think of -1
	// and empty groups as special cases. Instead, normalize the input first, then
	// solve a cleaner problem. When group[i] == -1, which means "this item belongs
	// to no group". If we try to handle the items without groups(say item 1 and 2),
	// then we'll need the special logic if(group[i] == -1) {...}. everywhere:
	// <> building group graph <> sorting groups <> output construction. That's
	// messy. The key observation is to ask: What does "belongs to no group" really
	// mean for ordering? Suppose item 1 has no group. It can;t be mixed inside
	// another group. The final answer requires: Items belonging to the same group
	// must be contiguous. An ungrouped item should behave like a group containing
	// exactly one item, so that every item belongs to exactly one group. we start
	// from groupId = m, which makes every item belong to some group after
	// normalization. The problem now becomes: "Sort items where every item belongs
	// to exactly one group." which is cleaner.
	// For "A group can have no item belonging to it" - It's a trap. If say group 1,
	// 2, 4 contains no items. One may waste time trying to remove these groups. But
	// the code groupItem.getOrDefault(groupIndex, new ArrayList<>()), handles it.
	// Here, it returns empty list and the inner loop runs zero times. No special
	// handling is needed.
	// A useful pattern during intervies: When one sees: Special value, -1, null,
	// empty, unassigned. Ask: Can I transform it into a normal value before
	// solving? This is called input normalization. For graph problems: -1 = parent.
	// -> creates virtual root. Interval problems: null boundary replace with +- Inf
	// (Integer.MAX_VALUE). For this problem we create singleton group. Then we only
	// solve one version of the problem. The interview thought process. Ideally,
	// within the first few minutes: Step 1: Notice: Item belong to groups.
	// Dependencies exist between items. There are 2 levels: Items, Groups. Step 2:
	// Notice group = -1, Ask: Can I pretend every item belongs to a group? Answer:
	// Yes. Create a group for every -1 item. Now: Every item belongs to exactly one
	// group. Step 3: Notice: item dependency crossing groups implies group
	// dependency. Example: item A -> item B. group(A) = 0 and group(B) = 2,
	// therefore group0 -> group2. Step 4: Now the problem naturally decomposes
	// into: Topological sort items, Topological sort groups, Merge. At this point,
	// one can get the solution. The biggest insight of the entire problem is not
	// the toplogical sort as most candidates know Kahn's algorithm. The biggest
	// insight is: Convert every -1 item into its own singleton group, so the entire
	// problem becomes "every item belongs to exactly one group". This removes
	// nearly all special-case complexity.
	// Time complexity - O(n + e + g), where n is given, g = number of groups after
	// assigning unique groups to items having no group and e is total number of
	// dependency relations across all beforeItems or the total number of
	// prerequisite relations all beforeItems = sum(beforeItems[i].size()).
	// Here g <= n + m. ATQ m <= n, g = O(n), so O(n+e).
	// Space complexity - O(n + e + g), Item graph: O(n + e), Group graph: O(g + e)
	// in worst case, Indegree arrays: O(n + g), Topological order arrays: O(n + g),
	// groupItem map: O(n).
	private static int[] sortItemsOpt(int n, int m, int[] group, List<List<Integer>> beforeItems) {
		// Sort all items regardless of group dependencies.
		int[] indegreeItem = new int[n];
		List<Integer>[] adjListItem = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjListItem[i] = new ArrayList<>();
		}

		int groupId = m;

		// If an item doesn't belong to any group, assign it a unique group id / new
		// group.
		for (int i = 0; i < n; i++) {
//			adjListItem[i] = new ArrayList<>(); // faster

			if (group[i] == -1) { // For item belonging to no group.
				// The items belonging to no group now belongs to exactly 1 group.
				// Later we want every item to appear inside a group block.
				group[i] = groupId++;
			}
		}

		// Sort all groups regardless of item dependencies.
		// groupId means number of groups = original groups + standalone items
		int[] indegreeGroup = new int[groupId];
		List<Integer>[] adjListGroup = new ArrayList[groupId];

		for (int i = 0; i < groupId; i++) {
			adjListGroup[i] = new ArrayList<>();
		}

		// We construct both graphs simultaneously
		for (int curr = 0; curr < n; curr++) { // process current item
			for (int prev : beforeItems.get(curr)) { // prev -> curr relationship
				// Each (prev -> curr) represents an edge in the item graph.
				indegreeItem[curr]++;
				adjListItem[prev].add(curr);

				// If they belong to different groups, add an edge in the group graph.
				// The dependency crosses groups
				// For 1 -> 3, item 1 in group A and item 2 in group B.
				// group A must come before group B.
				// groupA -> groupB
				// We ignore same group edges, as it would create self-loops which is not
				// needed. Item ordering already handles it. Example: 0 -> 1 both in group 0.
				// Group0 -> Group0 whould create a self-loop.
				if (group[curr] != group[prev]) {
					indegreeGroup[group[curr]]++;
					adjListGroup[group[prev]].add(group[curr]);
				}
			}
		}

		// Topologically sort nodes in the 2 graphs, return an empty array for cycle.

		// Sort items according to item dependencies
		int[] itemOrder = topologicalSort(indegreeItem, adjListItem);
		// Sort groups according to inter-group dependencies
		int[] groupOrder = topologicalSort(indegreeGroup, adjListGroup);

		// If either of the graphs contains a cycle. Example: For 0 -> 1 and 1 -> 0,
		// toplogical sort fails and return new int[0].
		if (itemOrder.length == 0 || groupOrder.length == 0) {
			return new int[0]; // or return new int[]{};
		}

		// Group items according to item order
		// Suppose itemOrder (topolofically sorted) produces [4,0,2,1,3]
		// Groups: 4 -> Group2 | 0 -> Group0 | 2 -> Group1 | 1 -> Group0 | 3 -> Group1
		Map<Integer, List<Integer>> groupItem = new HashMap<>();

		// Items are sorted regardless of groups, we need to differentiate them by the
		// groups they belong to.
		// groupItem for the given example produces
		// Group0 -> [0,1] | Group1 -> [2,3] | Group2 -> [4] where the items remain in
		// toplogical order inside each group.
		for (int item : itemOrder) {
			groupItem.computeIfAbsent(group[item], k -> new ArrayList<>()).add(item);
		}

		int[] answer = new int[n];
		int index = 0;

		// Traverse groups in topological order
		// Concatenate sorted items in all sorted groups.
		// [group1, group 2, ...] -> [(item 1, item 2, ...), (item 1, item 2, ...), ...]
		// Example groupOrder = [2,0,1]
		// groupOrder: [2,0,1] | Group2 -> [4] | Group0 -> [0,1] | Group1 -> [2,3]
		// answer: [4,0,1,2,3]
		// All items of a group stay together, all dependencies remain valid.
		for (int groupIndex : groupOrder) {// Get all items belonging to this group and append them.
			for (int item : groupItem.getOrDefault(groupIndex, new ArrayList<>())) { // handles groups without items.
				answer[index++] = item;
			}
		}
		return answer;
	}

	private static int[] topologicalSort(int[] indegree, List<Integer>[] adjList) {
		Queue<Integer> queue = new LinkedList<>();

		int n = indegree.length;

		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		int[] order = new int[n];
		int index = 0;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			order[index++] = node;

			for (int neighbor : adjList[node]) {
				indegree[neighbor]--; // Equivalent to deleting edge.

				// If all prerequisites satisfied, it becomes available.
				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}
		// Check valid DAG othewise some nodes never reached indegree 0 and hence cycle
		// exists.
		return index == n ? order : new int[0];
	}

	public static int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
		int[] indegreeItem = new int[n];
		List<Integer>[] adjListItem = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			adjListItem[i] = new ArrayList<>();
		}

		int groupId = m;

		for (int i = 0; i < n; i++) {
			if (group[i] == -1) {
				group[i] = groupId++;
			}
		}

		int[] indegreeGroup = new int[groupId];
		List<Integer>[] adjListGroup = new ArrayList[groupId];

		for (int i = 0; i < groupId; i++) {
			adjListGroup[i] = new ArrayList<>();
		}

		for (int curr = 0; curr < n; curr++) {
			for (int prev : beforeItems.get(curr)) {
				indegreeItem[curr]++;
				adjListItem[prev].add(curr);

				if (group[curr] != group[prev]) {
					indegreeGroup[group[curr]]++;
					adjListGroup[group[prev]].add(group[curr]);
				}
			}
		}

		List<Integer> itemOrder = topologicalOrder(indegreeItem, adjListItem);
		List<Integer> groupOrder = topologicalOrder(indegreeGroup, adjListGroup);

		if (itemOrder.isEmpty() || groupOrder.isEmpty()) {
			return new int[0]; // or return new int[]{};
		}

		Map<Integer, List<Integer>> orderedGroups = new HashMap<>();

		for (int item : itemOrder) {
			orderedGroups.computeIfAbsent(group[item], k -> new ArrayList<>()).add(item);
		}

		List<Integer> answer = new ArrayList<>();

		for (int groupIndex : groupOrder) {
			answer.addAll(orderedGroups.getOrDefault(groupIndex, new ArrayList<>()));
		}

		return answer.stream().mapToInt(Integer::intValue).toArray();
	}

	private static List<Integer> topologicalOrder(int[] indegree, List<Integer>[] adjList) {
		Queue<Integer> queue = new LinkedList<>();

		for (int i = 0; i < indegree.length; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		List<Integer> orderList = new ArrayList<>();

		while (!queue.isEmpty()) {
			int node = queue.poll();
			orderList.add(node);

			for (int neighbor : adjList[node]) {
				indegree[neighbor]--;

				if (indegree[neighbor] == 0) {
					queue.offer(neighbor);
				}
			}
		}

		return orderList.size() == adjList.length ? orderList : new ArrayList<>();
	}

}
