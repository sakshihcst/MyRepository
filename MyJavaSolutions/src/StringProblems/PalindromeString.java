package StringProblems;

public class PalindromeString {

	public boolean checkPalindrome(String inputString){
		char[] charArr = inputString.toCharArray();
		for(int i=0;i<charArr.length/2;i++){
			if(charArr[i]!=charArr[charArr.length-i-1]){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String ab[]){
		PalindromeString ps = new PalindromeString();
		String inputString = "saas";
		System.out.println(ps.checkPalindrome(inputString));
	}
}
