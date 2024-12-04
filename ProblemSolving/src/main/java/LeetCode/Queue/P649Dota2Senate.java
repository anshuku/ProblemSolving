package LeetCode.Queue;

import java.util.LinkedList;
import java.util.Queue;

/*
 * P649. Dota2 Senate - Medium
 * 
 * In the world of Dota2, there are two parties: the Radiant and the Dire.
 * The Dota2 senate consists of senators coming from two parties. Now the Senate wants 
 * to decide on a change in the Dota2 game. The voting for this change is a 
 * round-based procedure. In each round, each senator can exercise one of the two rights:
 * 
 * - Ban one senator's right: A senator can make another senator lose 
 *   all his rights in this and all the following rounds.
 * - Announce the victory: If this senator found the senators who still have rights to vote 
 * are all from the same party, he can announce the victory and decide on the change in the game.
 * 
 * Given a string senate representing each senator's party belonging. The character '
 * R' and 'D' represent the Radiant party and the Dire party. Then if there are 
 * n senators, the size of the given string will be n.
 * 
 * The round-based procedure starts from the first senator to the last senator 
 * in the given order. This procedure will last until the end of voting. All the senators 
 * who have lost their rights will be skipped during the procedure.
 * 
 * Suppose every senator is smart enough and will play the best strategy for his own party. 
 * Predict which party will finally announce the victory and change the Dota2 game. 
 * The output should be "Radiant" or "Dire".
 * 
 * Approach - Queues; char array, count and recursion
 */
public class P649Dota2Senate {

	public static void main(String[] args) {
//		String senate = "RD";

		String senate = "DDRRR";

//		String senate = "R";

		String winnerArr = predictPartyVictoryArr(senate);
		System.out.println("Array: The winner of the two parties - " + winnerArr);

		String winnerQueue = predictPartyVictoryQueue(senate);
		System.out.println("Queue: The winner of the two parties - " + winnerQueue);
	}

	private static String predictPartyVictoryArr(String senate) {
		char[] senateArr = senate.toCharArray();
		return evaluate(senateArr, 0);
	}

	// r and d are the ones which decide who is going to win
	// d is updated only if the count<=0 D is 1st or D is more
	// otherwise the D's senate is removed with 0.
	// r is updated only if the count>=0 R is 1st or R is more
	// otherwise the R's senate is removed with 0.
	private static String evaluate(char[] senateArr, int count) {
		int r = 0;
		int d = 0;
		for (int i = 0; i < senateArr.length; i++) {
			if (senateArr[i] == 'D') {
				if (count <= 0) {
					d++;
				} else {
					senateArr[i] = '0';
				}
				count--;
			} else if (senateArr[i] == 'R') {
				if (count >= 0) {
					r++;
				} else {
					senateArr[i] = '0';
				}
				count++;
			}
		}
		if (d == 0 && r != 0) {
			return "Radiant";
		} else if (d != 0 && r == 0) {
			return "Dire";
		}
		return evaluate(senateArr, count);
	}

	public static String predictPartyVictoryQueue(String senate) {
		char[] senateArr = senate.toCharArray();
		Queue<Integer> radiants = new LinkedList<>();
		Queue<Integer> dires = new LinkedList<>();
		int n = senateArr.length;
		for (int i = 0; i < senateArr.length; i++) {
			if (senateArr[i] == 'R') {
				radiants.add(i);
			} else {
				dires.add(i);
			}
		}

		while (!radiants.isEmpty() && !dires.isEmpty()) {
			if (radiants.peek() < dires.peek()) {
				radiants.add(n++);
			} else {
				dires.add(n++);
			}
			// Greedy as we are removing next senator which will increase win chance
			radiants.poll();
			dires.poll();
		}
		return radiants.isEmpty() ? "Dire" : "Radiant";

	}

}
