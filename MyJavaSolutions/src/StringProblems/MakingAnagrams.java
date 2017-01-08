package StringProblems;

public class MakingAnagrams {
	
	public static int makeAnagram(String inputString1,String inputString2){
		int length = 0;
		if(inputString1.length()<=inputString2.length()){
			length = inputString1.length();
		}
		return 0;
	}
	public static void main(String ab[]){
		String inputString1 = "abc";
		String inputString2 = "dac";
		makeAnagram(inputString1,inputString2);
	}

}
