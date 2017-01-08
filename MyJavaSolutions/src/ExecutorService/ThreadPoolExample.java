package ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample  {
	public static void main(String ab[]){
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i=0;i<100;i++){
			service.submit(new Task(i)); // can also use execute method here
		}
		service.shutdown();
	}
}

final class Task implements Runnable{
	
	private int taskID;
	
	public Task(int taskID){
		this.taskID = taskID;
	}
	
	@Override
	public void run(){
		System.out.println("Task ID " + this.taskID + " has been performed by " + Thread.currentThread().getName());
	}

	
}