package MathProblems;

public class ReverseInteger {
	
	public int reverse(int inputNumber){
		int reversedNumber = 0;
		int lastDigit;
		while(inputNumber!=0){
			lastDigit = inputNumber%10;
			inputNumber = inputNumber/10;
			reversedNumber = reversedNumber*10 + lastDigit;
		}
		return reversedNumber;
	}
	
	public static void main(String ab[]){
		int inputNumber = 1234;
		ReverseInteger ri = new ReverseInteger();
		System.out.println(ri.reverse(inputNumber));
	}
}
