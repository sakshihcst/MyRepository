package DataStructures.Queue;

//http://www.sanfoundry.com/java-program-implement-queue/

interface QueueInterface{
	public void enQueue(Object obj);
	public Object deQueue();
	public boolean isEmpty();
}

public class QueueViaArray implements QueueInterface{
	private int front;
	private int rear;
	int size = 10;
	Object[] queueObject;
	
	public QueueViaArray(){
		queueObject = new Object[size];
		front = -1;
		rear = -1;
	}
	
	public boolean isEmpty(){
		return (front == -1 && rear == -1);
	}

	public void enQueue(Object obj){
		if(isEmpty()){
			front++;
			rear++;
			queueObject[rear] = obj;
		}else if((rear+1)>=size){
			System.out.println("Queue Overflow");
		}else if((rear+1)<size){
			rear++;
			queueObject[rear] = obj;
		}
	}
	
	public Object deQueue(){
		if(isEmpty()){
			System.out.println("Queue is Empty");
		}else{
			Object removedObject = queueObject[front];
			if(front == rear){
				front = -1;
				rear = -1;
			}
			else{
				front++;
			}
			return removedObject;
		}
		return null;
	}
}
