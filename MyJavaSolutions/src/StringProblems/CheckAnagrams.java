package StringProblems;

import java.util.Arrays;

public class CheckAnagrams {
	
	public static boolean checkAnagram(String a, String b){
		if(a.length()!=b.length() || a.length()==0){
			return false;
		} else {
			char char_a[] = a.toLowerCase().toCharArray();
			Arrays.sort(char_a);
			char char_b[] = b.toLowerCase().toCharArray();
			Arrays.sort(char_b);
			System.out.println(String.valueOf(char_a));
			System.out.println(String.valueOf(char_b));
			if(String.valueOf(char_a).equalsIgnoreCase(String.valueOf(char_b))){
				return true;
			}
		}
		return false;
	}

	public static void main(String ab[]){
//		String inputString = "cinema";
//		String anagramString = "iceman";
		String inputString = "cinemaa";
		String anagramString = "iiceman";
		Boolean isAnagram = false;
		isAnagram = checkAnagram(inputString,anagramString);
		System.out.println(isAnagram);
	}
}
