package StringProblems;

import java.util.Arrays;

public class SortString {
	
	public void sortStringWithAPI(String inputString) {
		char[] charArr = inputString.toCharArray();
		Arrays.sort(charArr);
		System.out.println(String.valueOf(charArr));
	}
	
	public void sortStringWithoutAPI(String inputString) {
		char[] charArr = inputString.toCharArray();
		for(int i=0;i<charArr.length;i++){
			for(int j=0;j<charArr.length;j++){
				if(charArr[j]>charArr[i]) {
					char temp = charArr[j];
					charArr[j] = charArr[i];
					charArr[i] = temp;
				}
			}
		}
		System.out.println(String.valueOf(charArr));
	}

	public static void main(String ab[]) {
		String inputString = "gafsbycdew";
		SortString ss = new SortString();
		ss.sortStringWithAPI(inputString);
		ss.sortStringWithoutAPI(inputString);
	}
}
