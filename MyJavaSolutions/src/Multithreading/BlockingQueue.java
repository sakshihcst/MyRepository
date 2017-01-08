package Multithreading;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {
	
	private List<T> queue = new LinkedList<T>();
	private int size = 10;
	
	public BlockingQueue(int size){
	    this.size = size;
	  }

	public synchronized void enqueue(T obj) throws InterruptedException{
		while(this.size == this.queue.size()){
			wait();
		}
		if(this.queue.size() == 0){
			notifyAll();
		}
		this.queue.add(obj);
	}
	
	public synchronized Object dequeue() throws InterruptedException{
		while(this.queue.size() == 0){
			wait();
		}
		if(this.size == this.queue.size()){
			notifyAll();
		}
		return this.queue.remove(0);
	}
}
