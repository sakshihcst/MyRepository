package Singleton;

public class DoubleCheckedLockingSIngleton {
	
	private volatile static DoubleCheckedLockingSIngleton instance;
	
	private DoubleCheckedLockingSIngleton() {}
	
	public static DoubleCheckedLockingSIngleton getInstance(){
		if(instance == null){
			synchronized(DoubleCheckedLockingSIngleton.class){
				if(instance == null){
					instance = new DoubleCheckedLockingSIngleton();
				}
			}
		}
		return instance;
	}

}
