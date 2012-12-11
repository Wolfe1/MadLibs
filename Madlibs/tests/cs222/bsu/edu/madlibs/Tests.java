package cs222.bsu.edu.madlibs;

import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class Tests {
	
	TemplateReader reader = new TemplateReader(new File("Icestorm.mtl"));
	ArrayList<String> testList = new ArrayList<String>();
	Madlib aMadlib;
	
	/*
	 * Tests the get text method of MadLib
	 */
	@Test
	public void testMadlibGetText(){
		testList.add("Hello world");
		aMadlib = new Madlib(testList, null);
		Assert.assertEquals("Hello world", aMadlib.getText(0));
	}
	
	/*
	 * Tests the setText method of MadLib
	 */
	@Test
	public void testReplace(){
		testList.add("Hello world");
		aMadlib = new Madlib(testList, null);
		aMadlib.setText(0, "hi");
		Assert.assertEquals("hi", aMadlib.getText(0));
	}
	
	/*
	 * Tests adding elements to an Array List by using the parser
	 */
	@Test
	public void templateAddingArrayElements(){
		testList.add("The Great Ice Storm");
		reader.parseDocument();
		Assert.assertEquals(testList.get(0), reader.getText().get(0));
	}
	
	/*
	 * Tests to see if the parser can accurately get more then just the title
	 */
	@Test
	public void templateAddingMore(){
		testList.add("The Great Ice Storm");
		testList.add("It was the Winter of");
		testList.add("year");
		reader.parseDocument();
		Assert.assertEquals(testList.get(2), reader.getText().get(2));
	}
	
	/*
	 * Tests that the parser is creating a correct blankList
	 */
	@Test
	public void testBlanksList(){
		ArrayList<Integer> expected = new ArrayList<Integer>();
		for(int i = 2; i< 22; i=i+2) //A blank appears in every other index in the icestorm body
		expected.add(i);
		reader.parseDocument();
		Assert.assertEquals(expected, reader.getBlanks());
	}
	
	/*
	 * Tests the builder
	 */
	@Test
	public void testBuilder(){
		MadlibBuilder madBuilder = new MadlibBuilder();
		Madlib actual = madBuilder.textList(testList).blankList(null).build();
		Assert.assertNotNull(actual);
	}
}
