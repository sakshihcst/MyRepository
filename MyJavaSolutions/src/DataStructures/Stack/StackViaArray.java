package DataStructures.Stack;

interface StackInterface{
	public void push(Object o);
	public Object pop();
	public Object peek();
	
}
public class StackViaArray implements StackInterface{

	private int top;
	private Object[] obj;
	
	public StackViaArray(){
		top = -1;
		obj = new Object[10];
	}

	@Override
	public void push(Object o){
		if(top == obj.length-1){
			System.out.println("Stack Overflow");
		}else{
			top++;
			obj[top] = o;
		}
	}
		
	@Override	
	public Object pop(){
		Object popObject = null;
		if(top==-1){
			System.out.println("Stack is empty");
		}else{
			popObject = obj[top];
			top--;
		}
		return popObject;
	}
	
	@Override
	public Object peek(){
		if(top==-1){
			System.out.println("Stack is empty");
		}else{
			return obj[top];
		}
		return null;
	}
	
	public static void main(String[] args) {
		StackViaArray s = new StackViaArray();
		for(int i = 0; i < 6; i++) {
			s.push(i);
		}
		System.out.println(s.peek());
		for(int i = 0; i < 6; i++) {
			s.pop();
		}
		System.out.println(s.peek());
		}
}
