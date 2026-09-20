package LeetCode.Stack;

import java.util.Stack;

/*
 * P155. Min Stack - Medium
 * 
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * 
 * Implement the MinStack class:
 * > MinStack() initializes the stack object.
 * > void push(int value) pushes the element value onto the stack.
 * > void pop() removes the element on the top of the stack.
 * > int top() gets the top element of the stack.
 * > int getMin() retrieves the minimum element in the stack.
 * 
 * You must implement a solution with O(1) time complexity for each function.
 * 
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(value);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 * 
 * Constraints:
 * > -2^31 <= val <= 2^31 - 1
 * > Methods pop, top and getMin operations will always be called on non-empty stacks.
 * > At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
 * 
 * Approach - Stack, 2 Stacks
 */
public class P155MinStack {

	// We need to implement the solution in O(1) time, hence discard this.
	// Similarly, we can't use Binary Search Tree or Heap to find the min element as
	// they take O(logn) time each. Also, find, add, and remove take O(logn) time.
	static class MinStackBruteForce {

		Stack<Integer> stack;
		int min;

		public MinStackBruteForce() {
			stack = new Stack<>();
			min = Integer.MAX_VALUE;
		}

		public void push(int value) {
			stack.push(value);
			min = Math.min(min, value);
		}

		public void pop() {
			int val = stack.pop();
			if (val == min) {
				min = Integer.MAX_VALUE;
				for (int num : stack) {
					min = Math.min(min, num);
				}
			}
		}

		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return min;
		}
	}

	// Stack of Value / Minimum Pairs
	// An invariant is something that is always true or consistent. One should
	// always be on the lookup of useful invariants when problem-solving in maths or
	// computer-science.
	// In Stack, we only ever add (push) and remove (pop) numbers from the top. An
	// important invariant of a stack is that when a new number, say x is placed on
	// a stack, the numbers below it will not change for as long as number x remains
	// on the stack. Numbers could change above x for as long as x remains, but
	// there is no change below x till that time. So, whenever x is at the top, the
	// minimum will always be the same, as it's simply the minimum out of x and all
	// the numbers below it. So, in addition to putting a number in the MinStack, we
	// can also put its corresponding minimum value alongside it. Then whenever a
	// particular number is at the top of the MinStack, we can get both the top as
	// well as its corresponding minimum value.
	// To determine the corresponding minimum for the new number in O(1), we can
	// take the minimum of the current number and the number immediately before or
	// the actual stack top value via peek() method.
	// Time complexity - O(1) for all the operations. push(), checking the top of
	// the stack, comparing number and pushing to the top of a Stack or adding to
	// the end of an array or List are O(1) operations. pop(), popping from a stack
	// (or removing from the end of an Array or List) is an O(1) operation. top(),
	// looking at top is done in O(1) time which is same in getMin() as we keep a
	// track of the min value.
	// Space complexity - O(n), in worst case where all the operations are push, we
	// need O(2n) space for number and min.
	static class MinStackArray {

		Stack<int[]> stack;

		public MinStackArray() {
			stack = new Stack<>();
		}

		public void push(int value) {
			// If the stack is empty, then the min value is the first value we add.
			if (stack.isEmpty()) {
				stack.push(new int[] { value, value });
			}

			int min = stack.peek()[1];
			min = Math.min(min, value);
			stack.push(new int[] { value, min });
		}

		public void pop() {
			stack.pop();
		}

		public int top() {
			return stack.peek()[0];
		}

		public int getMin() {
			return stack.peek()[1];
		}
	}

	// 2 Stacks
	// In previous approach we were always storing 2 values, which is not needed all
	// the time as te minimum values are very repetitive. We can use 2 stacks in the
	// MinStack class. The main stack keeps track of the the order numbers arrived
	// (standard stack), and the 2nd stack (min-tracker) tracks current minimum. In
	// push() method, the items should always be pushed to the main stack, but they
	// should only bes pushed onto the min-tracker stack if they are smaller as well
	// as equal to the current top of it which is due to pop() method. In pop()
	// method, we pop the top of the main stack. But, for the min-tracker stack, we
	// cannot pop all the time as min values might be needed. We pop the min-tracker
	// stack's top based on the check top of main stack == top of min tracker stack.
	// This way we can ensure the new minimum would now be the top of the
	// min-tracker stack. But this can lead to problem if there are multiple minimum
	// values back to back. That is why we keep redundant minimum in min-tracker
	// stack while pushing the values with help of smaller as well as equal to the
	// current top condition. This will add duplicates but ensure no bugs in code.
	// Time complexity - O(1) for all the operations.
	// Space complexity - O(n) for all push operations in stack.
	static class MinStack2Stacks {

		Stack<Integer> stack;
		Stack<Integer> minStack;

		public MinStack2Stacks() {
			stack = new Stack<>();
			minStack = new Stack<>();
		}

		public void push(int value) {
			stack.push(value);

			if (minStack.isEmpty() || minStack.peek() >= value) {
				minStack.add(value);
			}
		}

		public void pop() {
			// We use int values or .equals() as we use a Integer Stack so == compares
			// references instead of int value. Even though it works for -128 to 127, but
			// beyond that numbers are not cached internally and new references are created
			// for every new push to stack via stack.push(val), this val is new Integer(val)
			// which creates a new object in heap.
			// Why use .equals() or .intValue() with == for stack's peek value comparision?
			// This is caused by Java Integer vs int value comparision issue caused by
			// autoboxing and integer caching.
			// Here, == fails, as the stack store Integer objects: peek() returns an Integer
			// object, not an int. So == compares object references, while .equals()
			// compares numberic values. Hence when we call for push(-1024) twice it does,
			// stack.push(Integer.valueOf(-1024) twice. Autoboxing calls
			// Integer.valueOf(value). Since, -1024 is outside Java's cached Integer range,
			// each call creates a different Integer object. Think of it like this:
			// stack -> Integer(-1024) @0xA1 | minStack -> Integer(-1024) @0xB2
			// Here, we've same value, different objects. Hence, when we perform pop() based
			// on == comparision, it becomes 0xA1 = 0xB2 // false, so pop in minSack is
			// skipped, leaving -1024 behind.
			// Why .equals() works, Integer.equals() compares the stored integer value.
			// Java caches Integer objects only for values: -128 to 127.
			// Using == with Integer is dangerous - it appears to work for small numbers but
			// fails for larger (or smaller) ones.
			// Alternate: Since we're only comparing numeric values, unbox them to int:
			// using .intValue() == .intValue()
			// Also, stack.peek() == value, doesn't have the same problem, as the value here
			// is an int, Java automatically unboxes the Integer to int, so it becomes a
			// primitive comparision similar to .intValue() == value.
			// This compares values, not object refeerenes, and is safe, The problem only
			// occurs when both sides arae Integer objects.
			if (minStack.peek().intValue() == stack.peek().intValue()) { // minStack.peek().equals(stack.peek())
				minStack.pop();
			}
			stack.pop();
		}

		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return minStack.peek();
		}
	}

	// Improved 2 stacks
	// In previous approach, we pushed a number onto the min-tracker stack if and
	// only if it's <= the current minimum. The problem is that if the same number
	// is pushed repeatedly onto MinStack, and that number also happens to be the
	// current minimum. To improve this, we can put pairs onto the min-tracker
	// Stack. The 1st value of the pair would be the same as the min value and the
	// 2nd one is the number of times the minimum is repeated. We modify the push()
	// and pop() method accordingly.
	// Time complexity - O(1) for all the operations.
	// Space complexity - O(n) for all push operations in stack.
	static class MinStack2StacksArray {

		Stack<Integer> stack;
		Stack<int[]> minStack;

		public MinStack2StacksArray() {
			stack = new Stack<>();
			minStack = new Stack<>();
		}

		public void push(int value) {
			// We always push number onto the main stack.
			stack.push(value);

			// If the min stack is empty, or this number is smaller than the top of the min
			// stack, put this number with a count of 1.
			if (minStack.isEmpty() || minStack.peek()[0] > value) {
				minStack.push(new int[] { value, 1 });
				// Else if this number = waht's at top, then increment the count by 1
			} else if (minStack.peek()[0] == value) {
				minStack.peek()[1]++;
			}
		}

		public void pop() {
			if (stack.peek().intValue() == minStack.peek()[0]) {
				if (minStack.peek()[1] == 1) {
					minStack.pop();
				} else {
					minStack.peek()[1]--;
				}
			}

			stack.pop();
		}

		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return minStack.peek()[0];
		}
	}

	public static void main(String[] args) {
		System.out.println("Stacks Min Find Linear");
		MinStackBruteForce obj1 = new MinStackBruteForce();
		obj1.push(-2);
		obj1.push(0);
		obj1.push(-3);

		int param_1 = obj1.getMin();
		System.out.println("1:" + param_1);

		obj1.pop();

		int param_2 = obj1.top();
		System.out.println("2:" + param_2);

		int param_3 = obj1.getMin();
		System.out.println("3:" + param_3);

		System.out.println("Stacks Min Find Array");
		MinStackArray obj2 = new MinStackArray();
		obj2.push(-2);
		obj2.push(0);
		obj2.push(-3);

		int param_l1 = obj2.getMin();
		System.out.println("1:" + param_l1);

		obj2.pop();

		int param_l2 = obj2.top();
		System.out.println("2:" + param_l2);

		int param_l3 = obj2.getMin();
		System.out.println("3:" + param_l3);

		System.out.println("2 Stacks");
		MinStack2Stacks obj3 = new MinStack2Stacks();
		obj3.push(-2);
		obj3.push(0);
		obj3.push(-3);

		int param_s1 = obj3.getMin();
		System.out.println("1:" + param_s1);

		obj3.pop();

		int param_s2 = obj3.top();
		System.out.println("2:" + param_s2);

		int param_s3 = obj3.getMin();
		System.out.println("3:" + param_s3);

		System.out.println("2 Stacks with min tracker array");
		MinStack2StacksArray obj4 = new MinStack2StacksArray();
		obj4.push(-2);
		obj4.push(0);
		obj4.push(-3);

		int param_sa1 = obj4.getMin();
		System.out.println("1:" + param_sa1);

		obj4.pop();

		int param_sa2 = obj4.top();
		System.out.println("2:" + param_sa2);

		int param_sa3 = obj4.getMin();
		System.out.println("3:" + param_sa3);

	}

}
