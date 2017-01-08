package Multithreading;

// http://www.java67.com/2012/12/producer-consumer-problem-with-wait-and-notify-example.html
// http://javarevisited.blogspot.com/2012/02/producer-consumer-design-pattern-with.html
public class ProducerConsumerProblem {
	private String value = "";
	  private boolean hasValue = false;
	  public void produce(String value) {
	    while (hasValue) {
	      try {
	        Thread.sleep(500);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    System.out.println("Producing " + value + " as the next consumable");
	    this.value = value;
	    hasValue = true;
	  }
	  public String consume() {
	    while (!hasValue) {
	      try {
	        Thread.sleep(500);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    String value = this.value;
	    hasValue = false;
	    System.out.println("Consumed " + value);
	    return value;
	  }

}
