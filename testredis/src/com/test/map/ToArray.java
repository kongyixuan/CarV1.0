package com.test.map;

import java.util.LinkedList;

import org.junit.Test;

public class ToArray {

	public static void main(String[] args) {

	}

	@Test
	public void testToArray() {
		LinkedList<String> strList = new LinkedList<String>();
		strList.add("zhang.san");
		strList.add("li.si");
		strList.add("wang.wu");
		print("filled array is not large enough.");
		testToArrayHelper(strList, new String[2]);

		print("filled array is large enough.");
		testToArrayHelper(strList, new String[3]);
	}

	public void testToArrayHelper(LinkedList<String> strList,
			String[] filledArray) {
		String[] returnedArray = strList.toArray(filledArray);
		printArray("filled array:", filledArray);
		print("");
		printArray("returned array:", returnedArray);
		print("");
		if (filledArray == returnedArray)
			print("filled array is equal returned array.");
		else
			print("filled array is not equal returned array.");
	}

	static <T> void printArray(String title, T[] array) {
		print(title);
		for (T item : array) {
			if (item != null)
				print("item:" + item.toString());
			else
				print("item is null");
		}
	}

	static void print(String info) {
		System.out.println(info);
	}
}
