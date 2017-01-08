package Searching;

//Input is sorted array. Complexity is O(log n)
public class BinarySearch {
	
	public void searchByRecursion(int[] sortedArray,int startIndex, int endIndex, int searchNumber){
		if(endIndex>startIndex){
			int mid = (startIndex+endIndex)/2;
			if(sortedArray[mid] == searchNumber){
				System.out.println("Found By Recursion");
				return;
			}else if(searchNumber > sortedArray[mid]){
				searchByRecursion(sortedArray,mid+1, endIndex, searchNumber);
			} else{
				searchByRecursion(sortedArray,0, mid-1, searchNumber);
			}
		}else{
			System.out.println("Not found  By Recursion");
		}
	}
	
	public void searchByIteration(int[] sortedArray,int startIndex, int endIndex, int searchNumber){
		while(endIndex>startIndex){
			int mid = (startIndex+endIndex)/2;
			if(sortedArray[mid] == searchNumber){
				System.out.println("Found By Iteration");
				return;
			}else if(searchNumber > sortedArray[mid]){
				startIndex = mid+1;
			} else{
				endIndex = mid-1;
			}
		}
		System.out.println("Not found By Iteration");
	}
	
	public static void main(String ab[]){
		int[] sortedArray = new int[]{3,4,7,9,12,34,56,78,99};
		int searchNumber = 8;
		int length = sortedArray.length;
		BinarySearch bs = new BinarySearch();
		bs.searchByRecursion(sortedArray,0, length-1, searchNumber);
		bs.searchByIteration(sortedArray,0, length-1, searchNumber);
	}

}
