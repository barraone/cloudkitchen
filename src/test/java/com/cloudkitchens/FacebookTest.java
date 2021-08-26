package com.cloudkitchens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Test;

public class FacebookTest {

	public Set<Character> validate(String alphabet) {

		final Set<Character> sc1 = new HashSet<>();
		for (int ii=0; ii<alphabet.length(); ++ii) {
			sc1.add(alphabet.charAt(ii));
		}
		return sc1;
	}
	
	public String shortestSubstring(String input, Set<Character> sc1) {
		  
		  final ConcurrentNavigableMap<Character,NavigableSet<Integer>> map = new ConcurrentSkipListMap<>();
		  for (int ii=0; ii<input.length(); ++ii) {
		    final char cc = input.charAt(ii);
		    NavigableSet<Integer> xx = new TreeSet<>();
		    xx.add(ii);
		    if ((xx = map.putIfAbsent(cc,xx)) != null) {
		      xx.add(ii);
		    }
		  }      
		        // map = { a -> [0,1], b->[4], c->[2,3,5], d->[6,7,8] }
		  final Set<Character> ccc = new HashSet<>();
		  for (int ii=0; ii<input.length(); ++ii) {
		    ccc.add(input.charAt(ii));
		  }
		  final Set<Character> ccc3 = new HashSet<>(sc1);
		  ccc3.removeAll(ccc);
		  if (!ccc3.isEmpty()) {
		    return "";
		  }
		  ccc.retainAll(sc1);

		        // map = { a -> [0,1], b->[4], c->[2,3,5] }
		  final ConcurrentNavigableMap<Character,NavigableSet<Integer>> map2 = new ConcurrentSkipListMap<>();
		  for (char cc : ccc ) {
			  NavigableSet<Integer> xxx = map.get(cc);
			  map2.put(cc,xxx);
		  }
		  
		  List<SortedSet<Integer>> permutation = new ArrayList<>();
		  for (NavigableSet<Integer> ints : map2.values()) {
			  final List<SortedSet<Integer>> perm = permutation;
			  permutation = new ArrayList<>();
			  if (perm.isEmpty()) {
				  for (int ii : ints) {
					  final SortedSet<Integer> ss1 = new TreeSet<>();
					  ss1.add(ii);
					  permutation.add(ss1);
				  }
			  } else {
				  for (SortedSet<Integer> ss1 : perm) {
					  for (int ii : ints) {
						  final SortedSet<Integer> ss2 = new TreeSet<>(ss1);
						  ss2.add(ii);
						  permutation.add(ss2);
					  }
				  }
			  }
		  }
		  
		  String rc = input;
		  for (SortedSet<Integer> ss1 : permutation) {
			  String str = input.substring(ss1.first(), 1+ss1.last());
			  if (str.length() < rc.length()) {
				  rc = str;
			  }
		  }
		  
		  return rc;

	}
	
	public String shortestSubstring1(String input, String alphabet) {
		  
		  final ConcurrentNavigableMap<Character,List<Integer>> map = new ConcurrentSkipListMap<>();
		  for (int ii=0; ii<input.length(); ++ii) {
		    final char cc = input.charAt(ii);
		    List<Integer> xx = new ArrayList<>();
		    xx.add(ii);
		    if ((xx = map.putIfAbsent(cc,xx)) != null) {
		      xx.add(ii);
		    }
		  }      
		        // map = { a -> [0,1], b->[4], c->[2,3,5], d->[6,7,8] }
		  final Set<Character> ccc = new HashSet<>();
		  for (int ii=0; ii<input.length(); ++ii) {
		    ccc.add(input.charAt(ii));
		  }
		  final Set<Character> ccc2 = new HashSet<>();
		  for (int ii=0; ii<alphabet.length(); ++ii) {
		    ccc2.add(alphabet.charAt(ii));
		  }
		  final Set<Character> ccc3 = new HashSet<>(ccc2);
		  ccc3.removeAll(ccc);
		  if (!ccc3.isEmpty()) {
		    return "";
		  }
		  ccc.retainAll(ccc2);

		        // map = { a -> [0,1], b->[4], c->[2,3,5], d->[6,7,8] }
		  final ConcurrentNavigableMap<Character,List<Integer>> map2 = new ConcurrentSkipListMap<>();
		  for (char cc : ccc ) {
		    List<Integer> xxx = map.get(cc);
		    map2.put(cc,xxx);
		  }
		  
		  List<SortedSet<Integer>> permutation = new ArrayList<>();
		  for (List<Integer> ints : map2.values()) {
			  final List<SortedSet<Integer>> perm = permutation;
			  permutation = new ArrayList<>();
			  if (perm.isEmpty()) {
				  for (int ii : ints) {
					  final SortedSet<Integer> ss1 = new TreeSet<>();
					  ss1.add(ii);
					  permutation.add(ss1);
				  }
			  } else {
				  for (SortedSet<Integer> ss1 : perm) {
					  for (int ii : ints) {
						  final SortedSet<Integer> ss2 = new TreeSet<>(ss1);
						  ss2.add(ii);
						  permutation.add(ss2);
					  }
				  }
			  }
		  }
		  
		  String rc = input;
		  for (SortedSet<Integer> ss1 : permutation) {
			  String str = input.substring(ss1.first(), 1+ss1.last());
			  if (str.length() < rc.length()) {
				  rc = str;
			  }
		  }
		  
		  return rc;

	}

	@Test
	public void test0() throws Exception {
		
		final String str = this.shortestSubstring("aaccbc", validate("abc"));
		System.out.println(str);

	}

}
