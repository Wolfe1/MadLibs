package cs222.bsu.edu.madlibs;

import java.util.ArrayList;

/**
 * Constructs a {@link Madlib}.
 * 
 * @author Brandon Wolfe
 * @author Steffan Byrne
 * @version 0.2 10/12/11
 * 
 */
public class MadlibBuilder {
	
	private ArrayList<String> textList;	//List of text including types
	private ArrayList<Integer> blankList;	//List of the positions of blanks in the textList

	public MadlibBuilder textList (ArrayList<String> textList){
		this.textList = textList;
		return this;
	}
	
	public MadlibBuilder blankList (ArrayList<Integer> blankList){
		this.blankList = blankList;
		return this;
	}
public Madlib build(){
	if (textList == null){
		throw new IllegalStateException("Madlib Object must have a list of String objects");
	}
	
	return new Madlib(textList, blankList);
	
}
	
}
