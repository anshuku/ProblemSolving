package LeetCode.Stack.P901OnlineStockSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * P901. Online Stock Span - Medium
 * 
 * Design an algorithm that collects daily price quotes for some stock 
 * and returns the span of that stock's price for the current day.
 * 
 * The span of the stock's price in one day is the maximum number of 
 * consecutive days (starting from that day and going backward) for which 
 * the stock price was less than or equal to the price of that day.
 * 
 * - For example, if the prices of the stock in the last four days is [7,2,1,2] and 
 *   the price of the stock today is 2, then the span of today is 4 because starting 
 *   from today, the price of the stock was less than or equal 2 for 4 consecutive days.
 * 
 * - Also, if the prices of the stock in the last four days is [7,34,1,2] and the 
 *   price of the stock today is 8, then the span of today is 3 because starting from 
 *   today, the price of the stock was less than or equal 8 for 3 consecutive days.
 * 
 * Implement the StockSpanner class:
 * 
 * - StockSpanner() Initializes the object of the class.
 * - int next(int price) Returns the span of the stock's price given that today's price is price.
 * 
 * Approach - Monotonic stack
 * 
 * Monotonically ascending(sorted ascending)
 * Monotonically decreasing(sorted descending)
 */
public class P901OnlineStockSpan {
	List<Integer> prices;
	int[] pricesArr;
	int size;
	Stack<int[]> stack;
	int[][] stackArr;
	int top;

	P901OnlineStockSpan() {
		prices = new ArrayList<>();
		// At most 10^4 calls are made to next
		pricesArr = new int[10000];
		stack = new Stack<>();
		stackArr = new int[10000][2];
		top = -1;
	}

	// Monotonic stack - monotonically decreasing
	// The current price along with the number of days it's span is added to stack
	// For adding two values, top of stack is popped when current price > top price
	// The span is updated for current price with the span of popped price.
	// The popped price won't be needed later as current price span may be reused.
	// When there is a price which is greater than current price or stack is empty
	// update teh stack with the two values - price and span and return the count.
	// Time complexity - O(1) on average for n prices, since the while loop runs
	// on an average n times. Each element from n elements can be popped off once.
	// Amortized analysis - average out time it takes for next in n calls.
	// Space complexity - O(n) for stack, when there is only decresing prices
	private int nextStack(int price) {
		int count = 1;
		while (!stack.isEmpty() && price >= stack.peek()[0]) {
			count += stack.pop()[1];
		}
		stack.add(new int[] { price, count });
		return count;
	}

	private int nextStackArr(int price) {
		int count = 1;
		while (top != -1 && price >= stackArr[top][0]) {
			count += stackArr[top--][1];
		}
		stackArr[++top] = new int[] { price, count };
		return count;
	}

	public int nextArr(int price) {
		pricesArr[size++] = price;
		int count = 0;
		for (int i = size - 1; i >= 0; i--) {
			if (price >= pricesArr[i]) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	public int nextList(int price) {
		prices.add(price);
		int count = 0;
		int n = prices.size();
		for (int i = n - 1; i >= 0; i--) {
			if (price >= prices.get(i)) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		P901OnlineStockSpan pos = new P901OnlineStockSpan();
//		int count1 = pos.nextList(100);
//		int count2 = pos.nextList(80);
//		int count3 = pos.nextList(60);
//		int count4 = pos.nextList(70);
//		int count5 = pos.nextList(60);
//		int count6 = pos.nextList(75);
//		int count7 = pos.nextList(85);

//		int count1 = pos.nextArr(100);
//		int count2 = pos.nextArr(80);
//		int count3 = pos.nextArr(60);
//		int count4 = pos.nextArr(70);
//		int count5 = pos.nextArr(60);
//		int count6 = pos.nextArr(75);
//		int count7 = pos.nextArr(85);

//		int count1 = pos.nextStack(100);
//		int count2 = pos.nextStack(80);
//		int count3 = pos.nextStack(60);
//		int count4 = pos.nextStack(70);
//		int count5 = pos.nextStack(60);
//		int count6 = pos.nextStack(75);
//		int count7 = pos.nextStack(85);

		int count1 = pos.nextStackArr(100);
		int count2 = pos.nextStackArr(80);
		int count3 = pos.nextStackArr(60);
		int count4 = pos.nextStackArr(70);
		int count5 = pos.nextStackArr(60);
		int count6 = pos.nextStackArr(75);
		int count7 = pos.nextStackArr(85);

		System.out.println("The span of the stock prices are: " + count1 + " " + count2 + " " + count3 + " " + count4
				+ " " + count5 + " " + count6 + " " + count7);
	}

}
