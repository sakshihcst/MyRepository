package StringProblems;

public class SwapStrings {
	
	public static void main(String ab[]){
		String s1 = "Sakshi";
		String s2 = "Gupta";
		s1 = s1 + s2;
		s2 = s1.substring(0, s1.length()-s2.length());
		s1 = s1.substring(s2.length());
		System.out.println(s1);
		System.out.println(s2);
	}

}
