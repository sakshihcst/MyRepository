package DataStructures.ArrayList;

// http://www.java2novice.com/java-interview-programs/arraylist-implementation/

import java.util.Arrays;

interface ArrayListInterface<T> {
	public void ensureCapacity();
	public int size();
	public void add(Object value);
	public Object get(int index);
	public void remove(int index);
	public boolean isEmpty();
	public int indexOf(Object value);
	public boolean contains(Object value);
}

public class ArrayList<T> implements ArrayListInterface<T>{

	private Object internalArray[];
	private int actualSize = 0;
	
	public ArrayList(){
		internalArray = new Object[10];
	}
	
	public void ensureCapacity(){
		internalArray = Arrays.copyOf(internalArray, internalArray.length*2);
	}
	
	public int size(){
		return actualSize;
	}
	
	public void add(Object value){
		if(actualSize>=internalArray.length/2){
			ensureCapacity();
		}
		internalArray[actualSize++] = value;
	}
	
	public Object get(int index){
		if(index < actualSize){
			return internalArray[index];
		} else{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void remove(int index){
		if(index < actualSize){
			for(int i=index;i<actualSize;i++){
				internalArray[index] = internalArray[index+1];
			}
			internalArray[actualSize] = null;
			actualSize--;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public boolean isEmpty(){
		if(actualSize == 0){
			return true;
		} else {
			return false;
		}
	}
	
	public int indexOf(Object value){
		for(int i=0;i<actualSize;i++){
			if(internalArray[i] == value){
				return i;
			}
		}
		return -1;
	}
	
	public boolean contains(Object value){
		for(int i=0;i<actualSize;i++){
			if(internalArray[i] == value){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String ab[]){
		ArrayList<String> list = new ArrayList<String>();
		System.out.println(list.isEmpty());
		list.add("Sakshi");
		list.add("Sandeep");
		list.add("Sandeep");
		list.add(null);
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		System.out.println(list);
		list.remove(2);
		System.out.println(list.isEmpty());
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}

}
