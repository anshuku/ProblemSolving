package LeetCode.Threads.P1114PrintInOrder;

/*
* Synchronized on two object - the separate objects are used as locks to lock two different sections of the code.
* This can be useful in scenarios where a single lock is not sufficient and in cases where one want to use an object  
* to protect one variable.
*/
public class PrintInOrderSync2Objects {

	private Object lock1;
	private Object lock2;

	private boolean oneDone;
	private boolean twoDone;

	PrintInOrderSync2Objects() {
		lock1 = new Object();
		lock2 = new Object();

	}

	public void first(Runnable printFirst) {
		synchronized (lock1) {
			printFirst.run();
			oneDone = true;
			lock1.notifyAll();
		}
	}

	public void second(Runnable printSecond) throws InterruptedException {
		synchronized (lock1) {
			synchronized (lock2) {
				while (!oneDone) {
					lock1.wait();
				}
				printSecond.run();
				twoDone = true;
				lock2.notifyAll();
			}
		}
	}

	public void third(Runnable printThird) throws InterruptedException {
		synchronized (lock2) {
			while (!twoDone) {
				lock2.wait();
			}
			printThird.run();
		}
	}

	public static void main(String[] args) {

	}

}
