package cs222.bsu.edu.madlibs;

import java.util.ArrayList;

/**
 * A Madlib consists of a string array list that is a completed madlib
 * statement. A madlib has an accessor and a modifier to change text in the list
 * 
 * The toHTML method is a modification of Paul Gestwicki's implementation
 * 
 * @author Brandon Wolfe
 * @author Steffan Byrne
 * @author Paul Gestwicki
 * @version 0.2 10/12/11
 */
public class Madlib {

	private ArrayList<String> madText; // List of Strings from the MTL file
										// including blank types.
	private ArrayList<Integer> blankPos; // List of the index positions of the
											// blanks in the madText List.

	/**
	 * The MadLib constructor creates a Madlib object that holds a list of
	 * Strings that create a madlib.
	 * 
	 * @param madText
	 *            ArrayList<String> that holds the Strings that make up a
	 *            madlib.
	 */
	public Madlib(ArrayList<String> madText, ArrayList<Integer> blankPos) {
		this.madText = madText;
		this.blankPos = blankPos;

	}

	/**
	 * The getText method accesses a Madlib object's String at the given index.
	 * 
	 * @param x
	 *            Integer that is the index where the text is located in the
	 *            Madlib's list.
	 */
	public String getText(int x) {
		return madText.get(x);

	}

	/**
	 * An ArrayList of positions that correspond to blanks in the madText
	 * ArrayList
	 * 
	 * @return the ArrayList of Integer indexes.
	 */
	public ArrayList<Integer> getBlankPos() {
		return blankPos;
	}

	/**
	 * The setText method modifies a Madlib object's String at the given index
	 * with a new String.
	 * 
	 * @param x
	 *            Integer that is the index where the text is located in the
	 *            Madlib's list.
	 * @param newS
	 *            String that replaces the String at the index x in the Madlib's
	 *            list.
	 */
	public void setText(int x, String newS) {
		madText.set(x, newS);
	}

	/**
	 * The getSize method returns the number of elements in the madText
	 * ArrayList
	 * 
	 * @return the size of the ArrayList as an Integer.
	 */
	public int getSize() {
		return madText.size();

	}

	/**
	 * The toHTML method converts the madText List to a String with HTML
	 * elements.
	 * 
	 * @return the String of the madText with HTML elements.
	 * 
	 * @author Paul Gestwicki
	 */
	public String toHTML() {
		StringBuilder builder = new StringBuilder("<html><h1>");
		builder.append(madText.get(0));
		builder.append("</h1><p>");
		for (int i = 1; i < madText.size(); i++) {
			builder.append(madText.get(i));
		}
		builder.append("</p></html>");
		return builder.toString();
	}
}
