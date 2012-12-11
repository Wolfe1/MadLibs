package cs222.bsu.edu.madlibs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

/**
 * A TemplateReader takes a MTL file and parses the document into a list of
 * objects(Text and Elements that can then be disected into an array list of
 * strings. The class also creates a list called blanks that collects the
 * indexes of blanks in the text list. Acessors for retrieving the lists are
 * provided in the class.
 * 
 * @author Brandon Wolfe
 * @author Steffan Byrne
 * @version 0.2 10/12/11
 */
public class TemplateReader {

	private Document doc; // The MTL document being parsed
	private Element root; // The beginning element of the doc
	private SAXBuilder builder; // New SaxBuilder using SAX parser
	private ArrayList<String> text; // List for doc's contents
	private ArrayList<Integer> blanks; // List of indexes for content list's
										// blanks
	private File aFile;					// The MTL file

	/**
	 * Constructs a TemplateReader given a File
	 * 
	 * @param aFile
	 *            an mtl file containing a madlibs story
	 */
	public TemplateReader(File aFile) {
		if (aFile.getName().endsWith(".mtl")) {
			this.aFile = aFile;
		} else {
			throw new IllegalArgumentException(
					"Input file must be a .mtl file.");
		}
	}

	/**
	 * Parses the mtl file into an ArrayList of strings and creates an ArrayList
	 * of ints that reference blanks in the string list
	 */
	public void parseDocument() {
		text = new ArrayList<String>();

		builder = new SAXBuilder(false);
		try {
			doc = builder.build(aFile);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		root = doc.getRootElement();

		Element title = root.getChild("title");
		text.add(title.getText());

		parseBody();
	}

	/**
	 * Utility method that carries out the parsing of the document body
	 */
	private void parseBody() {

		Element body = root.getChild("body");
		blanks = new ArrayList<Integer>();

		for (Object obj : body.getContent()) {

			if (obj instanceof Text) {
				text.add(((Text) obj).getText());

			} else if (obj instanceof Element) {

				text.add(((Element) obj).getChildText("type"));
				blanks.add(text.size() - 1);
			}
		}
	}

	/**
	 * Returns an ArrayList containing the parsed document as Strings.
	 */
	public ArrayList<String> getText() {
		return text;
	}

	/**
	 * Returns an ArrayList that contains references to the blanks in the the
	 * ArrayList of parsed text.
	 * 
	 */
	public ArrayList<Integer> getBlanks() {
		return blanks;
	}
}
