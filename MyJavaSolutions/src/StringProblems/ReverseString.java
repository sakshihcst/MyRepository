package StringProblems;

public class ReverseString {
	// Reverese a string without using string API.Complexity is O(n/2)
	public void reverseWithoutAPI(String inputString) {	
		char inputCharArray[] = inputString.toCharArray();
		char temp;
		for(int i=0;i<inputCharArray.length/2;i++){
			temp = inputCharArray[inputCharArray.length-i-1];
			inputCharArray[inputCharArray.length-i-1] = inputCharArray[i];
			inputCharArray[i] = temp;
		}
		System.out.println(String.valueOf(inputCharArray));
	}
	
	// There is no method in String class. StringBuilder has reverse method.
	public void reverseWithAPI(String inputString) {	
		StringBuilder sb = new StringBuilder(inputString);
		System.out.println(sb.reverse());
	}
	
public static void main(String ab[]){
	ReverseString rs = new ReverseString();
	String inputString = "sakshi gupta";
	rs.reverseWithoutAPI(inputString);
	rs.reverseWithAPI(inputString);
}
}