package Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ComparableExample {
	public static void main(String ab[]){
		List<TCSEmployee> empList = new ArrayList<TCSEmployee>();
		empList.add(new TCSEmployee(1,"Sakshi","Gupta",1000));
		empList.add(new TCSEmployee(2,"Sandeep","Saini",5000));
		empList.add(new TCSEmployee(3,"Ankit","Gupta",6500));
		empList.add(new TCSEmployee(4,"Heena","Mahajan",500));
		empList.add(new TCSEmployee(5,"Pradeep","Sharma",780));
		Collections.sort(empList);
		Iterator<TCSEmployee> itr = empList.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next().firstName);
		}
	}
}

 class TCSEmployee implements Comparable<TCSEmployee>{
	int empId;
	String firstName;
	String lastName;
	int salary;
	
	public TCSEmployee(){
		empId = 0;
		firstName = null;
		lastName = null;
		salary = 0;
	}
	
	public TCSEmployee(int empId, String firstName, String lastName, int salary){
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	@Override
	public int compareTo(TCSEmployee o) {
		if(this.salary==o.salary){
			return 0;
		} else if(this.salary>o.salary) {
			return 1;
		}else{
			return -1;
		}
	}
}

