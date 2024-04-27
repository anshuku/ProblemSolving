package LeetCode.Threads.P1114PrintInOrder;

import java.util.concurrent.Semaphore;

/*
* Semaphore - A Semaphore is a synchronization mechanism that uses a counter control access to shared resource.
* The acquire() method decrements the counter, and release() method increments it. When the counter is 0, 
* the acquire() method blocks until the counter is greater than 0 so that other threads blocked is re-enabled.
*/
public class PrintInOrderSemaphore {
	
	private Semaphore s1;
	private Semaphore s2;
	
	PrintInOrderSemaphore(){
		s1 = new Semaphore(0);
		s2 = new Semaphore(0);
		
	}

	public void first(Runnable printFirst) {
		printFirst.run();
		s1.release();
	}

	public void second(Runnable printSecond) throws InterruptedException {
		s1.acquire();
		printSecond.run();
		s2.release();
	}

	public void third(Runnable printThird) throws InterruptedException {
		s2.acquire();
		printThird.run();
	}

	public static void main(String[] args) {

	}

}
