package LeetCode.Graphs;

/*
 * P997. Find the Town Judge - Easy
 * 
 * In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town judge.
 * 
 * If the town judge exists, then:
 * 
 * 1. The town judge trusts nobody.
 * 2. Everybody (except for the town judge) trusts the town judge.
 * 3. There is exactly one person that satisfies properties 1 and 2.
 * 
 * You are given an array trust where trust[i] = [ai, bi] representing that 
 * the person labeled ai trusts the person labeled bi. If a trust relationship 
 * does not exist in trust array, then such a trust relationship does not exist.
 * 
 * Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.
 * 
 * Approach - Graph: Indegree, Outdegree
 * 
 * Can there be more than one town judge, no. As they must trust each other.
 */
public class P997FindTheTownJudge {

	public static void main(String[] args) {
//		int n = 2;
//		int[][] trust = { { 1, 2 } };

//		int n = 3;
//		int[][] trust = { { 1, 3 }, { 2, 3 } };

//		int n = 3;
//		int[][] trust = { { 1, 3 }, { 2, 3 }, { 3, 1 } };

//		int n = 3;
//		int[][] trust = { { 1, 2 }, { 2, 3 } };

		int n = 4;
		int[][] trust = { { 1, 3 }, { 1, 4 }, { 2, 3 }, { 2, 4 }, { 4, 3 } };

//		int n = 4;
//		int[][] trust = { { 1, 2 }, { 3, 2 }, { 1, 3 }, { 4, 1 }, { 3, 1 }, { 2, 1 }, { 2, 3 }, { 4, 3 }, { 4, 2 },
//				{ 3, 4 }, { 2, 4 } };

//		int n = 3;
//		int[][] trust = { { 1, 2 }, { 3, 2 }, { 3, 1 } };

		int judgeIndegree = findJudgeIndegree(n, trust);
		System.out.println("Indegree: The judge of the town is: " + judgeIndegree);

		int judgeInOut = findJudgeInOut(n, trust);
		System.out.println("Indegree Outdegree: The judge of the town is: " + judgeInOut);
	}

	// Indegree: One Array
	// We don't need separate arrays for indegree and outdegree. We can instead
	// build a single Array with the result of indegree - outdegree for each person.
	// The max indegree is N - 1, it represents everybody trusting the person(except
	// themselves, they can't trust themselves). The minimum indegree is 0, it
	// represents not trusting anybody. The max value of indegree - outdegree = (N -
	// 1) - 0 = N-1. The town judge is the only person to possibly have this.
	// Time complexity - O(E)
	// Space complexity - O(N)
	private static int findJudgeIndegree(int n, int[][] trust) {
		int len = trust.length;
		if (len < n - 1) {
			return -1;
		}
		int[] indegree = new int[n + 1];

		for (int[] relation : trust) {
			indegree[relation[1]]++;
			indegree[relation[0]]--;
		}

		for (int i = 1; i <= n; i++) {
			if (indegree[i] == n - 1) {
				return i;
			}
		}
		return -1;
	}

	// Indegree and Outdegree: Two Arrays
	// The trust relationships form a graph. Each trust pair, [a,b] represents a
	// directed edge going from a to b. In Graph Theory, we say the outdegree of a
	// vertex(person) is the number of direct edges going out of it. For this graph,
	// the outdegree of the vertex represents the number of other people that person
	// trusts. We say that the indegree of a vertex(person) is the number of
	// directed edges going into it. Here, it represents the number of people that
	// trust that person. The town judge can be defined in terms of indegree = n - 1
	// and outdegree = 0 as it trust nobody. and everybody trusts them(except
	// themselves). Also, it's impossible for there to be a town judge if there are
	// not at least n - 1 edges in the trust array. As, the town judge must have N -
	// 1 in-going edges.
	// Time complexity - O(E), where N = number of people, E = edges or trust
	// relationships. We loop over trust array in O(E) time to build the indegree
	// and outdegree. We then loop over the people to check the judge in O(N) time.
	// One can mention cost as O(max(N,E)) = O(N+E), as we don't know whether E or N
	// is bigger. However, we terminate early if E < N - 1. It means that in the
	// best case, the time is O(1). In woest case, we know E >= N - 1. For purpose
	// of big-oh notation, E has to be bigger, and so we can drop N.
	// Space complexity - O(N), for degree indegree and outdegree arrays.
	public static int findJudgeInOut(int n, int[][] trust) {
		int len = trust.length;
		if (len < n - 1) {
			return -1;
		}
		int[] indegree = new int[n + 1];
		int[] outdegree = new int[n + 1];

		for (int[] relation : trust) {
			indegree[relation[1]]++;
			outdegree[relation[0]]++;
		}

		for (int i = 1; i <= n; i++) {
			if (indegree[i] == n - 1 && outdegree[i] == 0) {
				return i;
			}
		}

		return -1;
	}

}
