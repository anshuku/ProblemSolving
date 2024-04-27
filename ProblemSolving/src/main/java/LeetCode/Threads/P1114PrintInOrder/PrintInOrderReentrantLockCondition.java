package LeetCode.Threads.P1114PrintInOrder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* Condition Variable - A Condition variable is a synchronization mechanism that provides a way to wait for 
* certain condition to be true before proceeding. Here, the await method releases the lock and blocks the thread until
* another thread calls signal() method. The Condition class is used in conjunction with a Lock.
*/
public class PrintInOrderReentrantLockCondition {
	
	private Lock lock;
	private Condition one;
	private Condition two;
	private boolean oneDone;
	private boolean twoDone;
	
	PrintInOrderReentrantLockCondition(){
		lock = new ReentrantLock();
		one = lock.newCondition();
		two = lock.newCondition();
	}

	public void first(Runnable printFirst) {
		lock.lock();
		try {
			printFirst.run();
			oneDone = true;
			one.signal();
		} finally {
			lock.unlock();
		}
	}

	public void second(Runnable printSecond) throws InterruptedException {
		lock.lock();
		try {
			while(!oneDone) {
				one.await();
			}
			printSecond.run();
			twoDone = true;
			two.signal();
		} finally {
			lock.unlock();
		}
	}

	public void third(Runnable printThird) throws InterruptedException {
		lock.lock();
		try {
			while(!twoDone) {
				two.await();
			}
			printThird.run();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {

	}

}
