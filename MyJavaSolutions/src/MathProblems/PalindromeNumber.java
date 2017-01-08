package MathProblems;

public class PalindromeNumber {
	
	public static void main(String ab[]){
		int inputNumber = 122121;
		ReverseInteger ri = new ReverseInteger();
		int reverseNumber = ri.reverse(inputNumber);
		if(inputNumber == reverseNumber){
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
