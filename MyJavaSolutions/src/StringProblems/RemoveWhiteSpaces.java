package StringProblems;

public class RemoveWhiteSpaces {
	
	public void removeWhiteSpacesWithAPI(String inputString){
		System.out.println(inputString.replaceAll("\\s", ""));
	}
	
	public void removeWhiteSpacesWithoutAPI(String inputString){
		StringBuilder sb = new StringBuilder();
		char charArr[] = inputString.toCharArray();
		for(int i=0;i<charArr.length;i++){
			if(charArr[i]!=' ' && charArr[i]!='\t'){
				sb.append(charArr[i]);
			}
		}
		System.out.println(sb.toString());
	}
	
	public static void main(String ab[]){
		String inputString = "This is 	a program to  remove  any number of white spaces.";
		RemoveWhiteSpaces rw = new RemoveWhiteSpaces();
		rw.removeWhiteSpacesWithAPI(inputString);
		rw.removeWhiteSpacesWithoutAPI(inputString);
	}
}
