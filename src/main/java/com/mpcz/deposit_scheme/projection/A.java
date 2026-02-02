package com.mpcz.deposit_scheme.projection;

import java.util.Arrays;

public class A {

	static int[] a = {17,13,89,4,41,2,3,4,};

	 
	 
	
	 
	 public static void main(String[] args) {
		 
		 
		 
		 for(int i=0;i<a.length-1;i++) {
			if(a[i]>a[i+1]) {
				
				
				int temp= a[i];
				a[i]=a[i+1];
				a[i+1]= temp ;
				
				System.err.println(a[i]);
				i = -1;
			}
		 }
		 
		 System.err.println("==========================================");
		 for(int i=0;i<a.length;i++) {
			 
			 
			 System.err.println(a[i]);
		 }
		 
		 B b = new B("Ram",1);
		 B b1 = new B("Name",28);
		 
		 B[] obj = {b,b1};
		 
		 for(int i = 0 ; i<=obj.length-1;i++) {
			B bdb= obj[i];
			 if(bdb.getAge()>27) {
				 System.out.println(bdb);
			 }
		 }
		 
	}
	
}


class B{
	String name ;
	
	int age ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public B(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "B [name=" + name + ", age=" + age + "]";
	}
	
	
	
	
	
}
