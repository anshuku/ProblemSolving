package LeetCode.MonotonicStack;

import java.util.Arrays;
import java.util.Stack;

/*
 * P1475. Final Prices With a Special Discount in a Shop - Easy
 * 
 * You are given an integer array prices where prices[i] is the price of the ith item in a shop.
 * 
 * There is a special discount for items in the shop. If you buy the ith item, then you 
 * will receive a discount equivalent to prices[j] where j is the minimum index such that 
 * j > i and prices[j] <= prices[i]. Otherwise, you will not receive any discount at all.
 * 
 * Return an integer array answer where answer[i] is the final price you 
 * will pay for the ith item of the shop, considering the special discount.
 * 
 * Approach - Monotonic Stack, Array
 */
public class P1475FinalPricesWithSpecialDiscountInShop {

	public static void main(String[] args) {
//		int[] prices = { 8, 4, 6, 2, 3 };
//		int[] prices = { 1, 2, 3, 4, 5 };
		int[] prices = { 10, 1, 1, 6 };

		int[] finalPricesMStack = finalPricesMStack(prices);
		System.out.println("Monotonic Stack: The discounted final price to pay for ith item: "
				+ Arrays.toString(finalPricesMStack));

		int[] finalPricesArray = finalPricesArray(prices);
		System.out
				.println("Array: The discounted final price to pay for ith item: " + Arrays.toString(finalPricesArray));
	}

	// Monotonic Stack: next smaller item so we've montonically increasing stack.
	// For any given item, we need to find the 1st price that is <= it and comes
	// after it. This find the "next smaller element", which can be efficiently
	// solved using a stack. While processing elements from left to right, we need
	// to determine if the current price can serve as a discount for any previous
	// prices. The stack helps to keep track of those previous prices that haven't
	// found their discount yet. Here, we keep the previous prices' indices to
	// update the final discounted price. If we found a smaller element, our stack
	// must contain all the most recent prices before that element that are greater
	// than it. This means that each element in the stack must be in increasing
	// order of value. This is called a monotonic stack. When we find an element
	// that is <= than the top of the stack, it means a discount can be applied to
	// the stack element. We continue popping prices from the stack unitl the stack
	// is empty or the top price is less than the current price. Then, we push the
	// current price to the top of the stack, to wait for a discount which may or
	// may not come further down. To implement this, we'll maintain a stack of
	// indices (not prices, since we need the positions to apply the discounts). Any
	// prices' indices left on the stack at the end of the main loop had no
	// discount availble.
	// Time complexity - O(n), we iterate through the prices array once. Even though
	// while is nested, each element can be pushed or popped from the stack only
	// once. This emans the total number of operations on the stack across all
	// iterations is at most 2*n (n pushes and n pops).
	// Space complexity - O(n), we use stack which takes at most n space in worst
	// case where all the prices are in increasing order.
	public static int[] finalPricesMStack(int[] prices) {
		int n = prices.length;

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < n; i++) {
			// Process items that can be discounted by current price.
			while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
				// Apply discount to previous item using current price.
				prices[stack.pop()] -= prices[i];
//				prices[stack.peek()] -= prices[i];
//				stack.pop();
			}
			// Add current index to stack
			stack.push(i);
		}
		return prices;
	}

	// Arrays: Faster than stack despite being O(n^2) due to cache line prefetch
	// Time complexity - O(n^2), due to nested for loops in case of prices
	// containing elements in strictly increasing order.
	// Space complexity - O(1)
	private static int[] finalPricesArray(int[] prices) {
		int n = prices.length;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (prices[j] <= prices[i]) {
					prices[i] -= prices[j];
					break;
				}
			}
		}
		return prices;
	}

}
