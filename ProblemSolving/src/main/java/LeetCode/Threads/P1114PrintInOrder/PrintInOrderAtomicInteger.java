package LeetCode.Threads.P1114PrintInOrder;

import java.util.concurrent.atomic.AtomicInteger;

/*
* 
*/
public class PrintInOrderAtomicInteger {
	
	AtomicInteger oneDone = new AtomicInteger(0);
	AtomicInteger twoDone = new AtomicInteger(0);
	
	PrintInOrderAtomicInteger(){
		
	}

	public void first(Runnable printFirst) {
		printFirst.run();
		oneDone.incrementAndGet();
		//oneDone.getAndIncrement();
	}

	public void second(Runnable printSecond) {
		while(oneDone.get() != 1) {}
		printSecond.run();
		twoDone.getAndIncrement();
		//twoDone.incrementAndGet();
	}

	public void third(Runnable printThird) {
		while(twoDone.get() != 1) {}
		printThird.run();
	}

	public static void main(String[] args) {
		
	}

}
