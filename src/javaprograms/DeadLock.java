package javaprograms;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*
 * Deadlock is created when two threads each waiting for a resource, which is held by the other thread.
 * 
 *  Here Thread-A acquires lock1 and is waiting to get lock2. At the same time Thread-B acquires lock2 and waiting to get lock1.
 *  This stops both the threads from doing anything else.
 * 
 */

public class DeadLock {

	private static Lock lock1 = new ReentrantLock();
	private static Lock lock2 = new ReentrantLock();

	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				Thread.currentThread().setName("Thread-A");
				lock1.lock(); 
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " got lock1 and going to get lock2");
				lock2.lock();
				System.out.println("got lock2");
			}
		}).start();

		new Thread(new Runnable() {
			public void run() { 
				Thread.currentThread().setName("Thread-B");
				lock2.lock(); 
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() +" got lock2 and going to get lock1");
				lock1.lock();
				System.out.println("got lock1");
			}

		}).start();
	}
}