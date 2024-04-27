package LeetCode.Threads.P1114PrintInOrder;

/*
* Synchronized method makes the thread accessing it to take the lock of entire object and release it only on wait()
* wait() allows to release the lock for entire object for another threads to get the access inside synchronized method.
* notifyAll() ensures that other threads waiting to be back again
*
* wait(long timeout, int nanos)throws InterruptedException	- causes the current thread to wait 
* (for the specified milliseconds and nanoseconds), 
* until another thread notifies (invokes notify() or notifyAll() method).
* void notify() wakes up single thread, waiting on this object's monitor.
* void notifyAll() wakes up all the threads, waiting on this object's monitor.
*
* Synchronized Method - The synchronized keyword is used on the method themselves to lock the entire object.
* When a thread enters a synchronized method, it acquires the lock on the object and no other threads
* can enter any other synchronized method on the same object until the lock is released.
*/
public class PrintInOrderSyncMethod {

	private boolean oneDone;
	private boolean twoDone;

	PrintInOrderSyncMethod() {
	}

	public synchronized void first(Runnable printFirst) {
		printFirst.run();
		oneDone = true;
		notifyAll();
	}

	public synchronized void second(Runnable printSecond) throws InterruptedException {
		while (!oneDone) {
			wait();
		}
		printSecond.run();
		twoDone = true;
		notifyAll();
	}

	public synchronized void third(Runnable printThird) throws InterruptedException {
		while (!twoDone) {
			wait();
		}
		printThird.run();
	}

	public static void main(String[] args) {

	}

}
