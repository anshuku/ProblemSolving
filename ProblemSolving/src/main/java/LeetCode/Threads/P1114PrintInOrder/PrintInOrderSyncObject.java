package LeetCode.Threads.P1114PrintInOrder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/*
* Synchronized object makes the thread accessing it to take the lock of the object and release it only on object.wait()
* object.wait() allows to wait and release the lock for locked object by the thread inside the synchronized block.
* Synchorized on object - In the method, a separate Object is used as a lock instead of the class itself.
* When a thread enters a synchronized block with the lock object, it acquires the lock and no other threads
* can enter any other synchronized blocks with the same lock object until the lock is released.
*/
public class PrintInOrderSyncObject extends Thread {
	
	String var = "";
	
	public void run() {
		System.out.println(var);
		
	}
	PrintInOrderSyncObject(String var) {
		this.var = var;
	}

	private Object lock;

	private boolean oneDone;
	private boolean twoDone;

	PrintInOrderSyncObject() {
		lock = new Object();
	}

	public void first(Runnable printFirst) throws InterruptedException {
		synchronized (lock) {
			printFirst.run();
			oneDone = true;
			lock.notifyAll();
		}
	}

	public void second(Runnable printSecond) throws InterruptedException {
		synchronized (lock) {
			while (!oneDone) {
				lock.wait();
			}
			printSecond.run();
			twoDone = true;
			lock.notifyAll();
		}
	}

	public void third(Runnable printThird) throws InterruptedException {
		synchronized (lock) {
			while (!twoDone) {
				lock.wait();
			}
			printThird.run();
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		PrintInOrderSyncObject threadGen = new PrintInOrderSyncObject();
//		
//		Thread t1 = new Thread(new PrintInOrderSyncObject("first"));
//		Thread t2 = new Thread(new PrintInOrderSyncObject("second"));
//		Thread t3 = new Thread(new PrintInOrderSyncObject("third"));
//		
//		t1.start();
//		t2.start();
//		t3.start();
//		
//		System.out.println();
//		
//		threadGen.first(t2);
//		threadGen.second(t3);
//		threadGen.third(t1);

        new Thread(() -> {
            try {
				threadGen.second(() -> System.out.println("second"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }).start();

        new Thread(() -> {
            try {
				threadGen.third(() -> System.out.println("third"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }).start();

        new Thread(() -> {
            try {
				threadGen.first(() -> System.out.println("first"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }).start();
		
//		Thread t1 = new Thread(new First());
//		Thread t2 = new Thread(new Second());
//		Thread t3 = new Thread(new Third());
//		
//		PrintInOrderSyncObject p = new PrintInOrderSyncObject();
//		
//		p.first(t2);
//		p.second(t3);
//		p.third(t1);
		
//		Stream.<Runnable>of(() -> p.first(t2),() -> p.second(t3),() -> p.third(t1)).parallel().forEach(Runnable::run);
		
//		CompletableFuture<Void> future1 = CompletableFuture.runAsync(()->{
//	        p.first(t3);
//		});
//		CompletableFuture<Void> future2 = CompletableFuture.runAsync(()->{
//	        try {
//				p.second(t1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		CompletableFuture<Void> future3 = CompletableFuture.runAsync(()->{
//	        try {
//				p.third(t2);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3);
//		try {
//	        future.get(); // this line waits for all to be completed
//	    } catch (InterruptedException e) {
//	        // Handle
//	    }
		
	}

}
