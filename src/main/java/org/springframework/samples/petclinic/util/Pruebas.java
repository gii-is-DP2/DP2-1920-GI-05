package org.springframework.samples.petclinic.util;

public class Pruebas {

	public static void main(String[] args) {
		
		Double n = 10000.00;
		System.out.println(countIntegers(n));

	}
	
	public static int countIntegers(Double d) {
		Integer intValue = d.intValue();		
		int count = intValue.toString().length();
		
		return count;		
	}

}
