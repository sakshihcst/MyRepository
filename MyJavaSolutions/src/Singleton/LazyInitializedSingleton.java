package Singleton;

public class LazyInitializedSingleton {
	
	private static LazyInitializedSingleton instance;
	
	private LazyInitializedSingleton(){}
	
	public LazyInitializedSingleton getInstance(){
		if(instance == null) {
			instance = new LazyInitializedSingleton();
		}
		return instance;
	}

}
