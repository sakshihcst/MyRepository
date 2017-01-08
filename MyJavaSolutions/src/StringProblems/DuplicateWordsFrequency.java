package StringProblems;

import java.util.HashMap;
import java.util.Map;

public class DuplicateWordsFrequency {
	static void countDuplicateWords(String inputString){
		Map<String,Integer> wordCount = new HashMap<String,Integer>();
		String[] words = inputString.split(" ");
		for(String word:words){
			if(wordCount.containsKey(word)){
				wordCount.put(word,(wordCount.get(word)+1));
			}else{
				wordCount.put(word, 1);
			}
		}
		for(Map.Entry<String,Integer> entry:wordCount.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public static void main(String ab[]){
		countDuplicateWords("hello dear please say hello please listen say something say na.");
	}

}
