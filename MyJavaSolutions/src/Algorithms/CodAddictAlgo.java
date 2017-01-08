package Algorithms;

import java.util.HashSet;
import java.util.Set;

// Find a pair of elements from an array whose sum equals a given number
public class CodAddictAlgo {

	public void findSum(int[] arr, int sum){
		Set<Integer> set = new HashSet<Integer>();
		if(arr!=null && arr.length>0){
			for(int item : arr){
				if(set.contains(sum-item)){
					System.out.println("The numbers with sum " + sum + " is : "+item+" , "+ (sum-item));
				}else{
					set.add(item);
				}
			}
		}
	}
	
	public static void main(String ab[]){
		CodAddictAlgo ca = new CodAddictAlgo();
		int[] arr = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
		int sum = 12;
		ca.findSum(arr, sum);
	}
}
