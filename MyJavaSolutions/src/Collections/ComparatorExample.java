package Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class Employee {
	int empId;
	String firstName;
	String lastName;
	int salary;
	
	public Employee(){
		empId = 0;
		firstName = null;
		lastName = null;
		salary = 0;
	}
	
	public Employee(int empId, String firstName, String lastName, int salary){
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}
}

class SalaryComparator implements Comparator<Employee>{

	@Override
	public int compare(Employee e1, Employee e2) {
		if(e1.salary==e2.salary){
			return 0;
		} else if(e1.salary>e2.salary) {
			return 1;
		}else{
			return -1;
		}
	}	
}

public class ComparatorExample {
	public static void main(String ab[]){
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(new Employee(1,"Sakshi","Gupta",1000));
		empList.add(new Employee(2,"Sandeep","Saini",5000));
		empList.add(new Employee(3,"Ankit","Gupta",6500));
		empList.add(new Employee(4,"Heena","Mahajan",500));
		empList.add(new Employee(5,"Pradeep","Sharma",780));
		Collections.sort(empList, new SalaryComparator());
		Iterator<Employee> itr = empList.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next().firstName);
		}
	}
}

