import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Constraint;
import com.google.common.collect.Constraints;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.common.primitives.Booleans;

public class GuavaExperiments {
	
	static void tables() {
		Table<Integer, String, String> table = HashBasedTable.create();
		table.put(1, "a", "1a");
		table.put(1, "b", "1b");
		table.put(2, "a", "2b");
		table.put(2, "b", "2b");
		System.out.println("Table is " + table.toString());
		
		Table transposedTable = Tables.transpose(table);
		System.out.println("Transposed table is " + transposedTable);	
		
		if ( table.containsRow(3) ) {
			System.out.println("contains row 3");
		} else {
			System.out.println("no row 3");
		}
		System.out.println(table.get(2,"a"));
		
		

	}
	
	public static void shouldConstraintCollection() {
		Constraint<String> loginMustStartWithR = new Constraint<String>() {
		    @Override
		    public String checkElement(String user) {
		        Preconditions.checkNotNull(user);
		        
		        if(!user.startsWith("r")) {
		            throw new IllegalArgumentException("GTFO, you are not Rrrrrrrrr");
		        }

		        return user;
		    }
		}; 
	 List<String> users = Collections.singletonList("rambo");
	 System.out.println("users = " + users.toString());
	 Collection<String> usersThatStartWithR = Constraints.constrainedCollection(users, loginMustStartWithR);
	 usersThatStartWithR.add("commando");
	 System.out.println("users = " + users.toString());

	}

	static public void charExpts() {
		String input = "This invoice has an id of 192/10/10";
		CharMatcher charMatcher1 = CharMatcher.DIGIT.or(CharMatcher.is('/'));
		String output = charMatcher1.retainFrom(input);
		
		input = "DO NOT scream at me!";
		CharMatcher charMatcher2 = CharMatcher.JAVA_LOWER_CASE.or(CharMatcher.WHITESPACE).negate();
		output = charMatcher1.retainFrom(input);
		
	    input = "DO NOT scream at me!";
		CharMatcher charMatcher = CharMatcher.inRange('m', 's').or(CharMatcher.is('a').or(CharMatcher.WHITESPACE));
	    output = charMatcher.retainFrom(input);
	}
	
	public static void checks(Long id,
			String firstName, String lastName, String login) {
		shouldConstraintCollection();
		Long tid = Preconditions.checkNotNull(id);
		String tfirstName = Preconditions.checkNotNull(firstName);
		String tlastName = Preconditions.checkNotNull(lastName);
		String tlogin = Preconditions.checkNotNull(login);

		Preconditions.checkArgument(firstName.length() > 0);
		Preconditions.checkArgument(lastName.length() > 0);
		Preconditions.checkArgument(login.length() > 0);
	}
	
	public static void main(String[] args) {
		String input12 =
		        "A  1  1  1  1\n" +
		        "B  1  2  2  2\n" +
		        "C  1  2  3  3\n" +
		        "D  1  2  5  3\n" +
		        "E  3  2  5  4\n" +
		        "F  3  3  7  5\n" +
		        "G  3  3  7  5\n" +
		        "H  3  3  9  7";
		Iterable<String> splitted12 = Splitter.fixedLength(3).trimResults().split(input12);
		for (String s12 : splitted12 ) {
			System.out.print(s12 + ",");
		}
		
		String input0 = "Some very stupid data with ids of invoices like 121432, 3436534 and 8989898 inside";
		Iterable<String> splitted = Splitter.on(" ").split(input0);
		for (String s0 : splitted ) {
			System.out.println(s0);
		}
		
		String input1 = "Some very stupid data with ids of invoces like 123231/fv/10/2010, 123231/fv/10/2010 and 123231/fv/10/2010";
	    splitted = Splitter.on(CharMatcher.DIGIT.negate())
		                                    .trimResults()
		                                    .omitEmptyStrings()
		                                    .split(input1);
		for (String s1 : splitted ) {
			System.out.println(s1);
}
		try {
			  List<String> lines = Resources.readLines( new URL("http://adnan.appspot.com"), Charsets.UTF_8);
			  for ( String u : lines ) {
			    System.out.println(u);
			  }
		  } catch (Exception e) {
	        e.printStackTrace();
		  }
		
		tables();
	  checks(123L, "adnan", "aziz", "xxx");
	  System.out.println("Hello World!");
	  System.out.println("- 3 % 4 = " + (0-3)%4);
	  System.out.println("Long.MIN_VALUE:" + Long.MIN_VALUE);
	  System.out.println("Long.MIN_VALUE + 1:" + (Long.MIN_VALUE + 1));
	  System.out.println("abs Long.MIN_VALUE:" + Math.abs(Long.MIN_VALUE));
	  System.out.println("abs Long.MIN_VALUE + 1:" + Math.abs((Long.MIN_VALUE + 1)));
	  Random rnd = new Random();
	  int N = 8;
	  int M = 1000;
	  int low = 0;
	  for ( int i = 0 ; i < M; i++ ) {
	    System.out.print(Math.abs(rnd.nextInt()) % N + " ");
	  }
	  String s = "hello world";
	  String t = "hello world";
	  String input = "adnan123-456 xyx 9";
	  Preconditions.checkState(s.equals(t), "s not equals t!");
	  String seriesId = CharMatcher.DIGIT.or(CharMatcher.is('-')).retainFrom(input);
	  System.out.println(seriesId);
	  String[] episodesOnDisk = {"foo", "bar", " widget", "", " adnan"};
	  String joiner = Joiner.on(",").join(episodesOnDisk);
	  System.out.println(joiner);
	  Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
	  Iterable<String> splitArray = splitter.split(joiner);
	  for ( Iterator<String> u = splitArray.iterator(); u.hasNext();  ) {
		  System.out.println(u.next() + (u.hasNext() ? ", " : ""));
	  } 
	  
	  try {
	    String c = Files.toString(new File("/tmp/test.txt"), Charsets.UTF_8);
	    System.out.println("tofile: " + c);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  
	  
	  
	  List<Boolean> bl = Booleans.asList(true,true,false);
	  System.out.println(bl);
	}
}
