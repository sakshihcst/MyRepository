package MathProblems;

public class PrimeNumbers {
	public static Boolean isPrime(int number){
		for(int i=2;i<=Math.sqrt(number);i++){
			if(number%i==0){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String ab[]){
		int inputNumber = 17;
		 System.out.println(isPrime(inputNumber));
	}
}
