package StringProblems;

public class StringEquality {
	public static void main(String ab[]){
		String s1 = new String("abc");
		String s2 = new String("abc");
		String s3 = "xyz";
		String s4 = "xyz";
		s1 = s1.intern();
		s2 = s2.intern();
		System.out.println(s1==s2);
		System.out.println(s3==s4);
	}

}
