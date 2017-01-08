package StringProblems;

public class ReverseAndDuplicate {
	public static void main(String ab[]){
		String inputString = "hammer";
		StringBuilder outputString = new StringBuilder();
		for(int i=inputString.length()-1;i>=0;i--){
			if(outputString.indexOf(String.valueOf(inputString.charAt(i)))==-1){
				outputString.append(String.valueOf(inputString.charAt(i)));
			}
		}
		System.out.println(outputString);
	}
}
