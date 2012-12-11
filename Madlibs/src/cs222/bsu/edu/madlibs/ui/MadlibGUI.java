package cs222.bsu.edu.madlibs.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs222.bsu.edu.madlibs.Madlib;
import cs222.bsu.edu.madlibs.MadlibBuilder;
import cs222.bsu.edu.madlibs.TemplateReader;

/**
 * 
 * The MadlibGUI allows a user to load a Madlib file. The user can then input
 * their own words for the blanks in the story and see the completed story. They
 * also have the option to save their story as an HTML file.
 * 
 * @author Brandon Wolfe
 * @author Steffan Byrne
 * @version 0.2 10/12/11
 */
public class MadlibGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private TemplateReader reader;
	private MadlibBuilder builder;
	private Madlib aMadlib;
	private ArrayList<JLabel> labelList; // List of JLabels for the blank types
	private ArrayList<JTextField> fieldList; // List of JTextFields for the
												// blanks
	private JButton finishButton; // Button for user to progress to the finished
									// story

	private JPanel inputPanel; // Panel for user word input
	private JPanel resultPanel; // Panel to display the finished Madlib
	private JPanel buttonPanel; // Panel for the Quit and Save buttons
	private CardLayout cardLayout; // Layout for the main GUI
	private JButton quitButton; // Button for the user to quit the application
	private JButton saveButton; // Button for the user to save their Madlib

	private final int TEXT_FIELD_SIZE = 4; // Size for text fields for blanks

	/**
	 * Creates a GUI for the application
	 */
	public MadlibGUI() {

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a .mtl file to begin.");

		File mtlFile = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Madlib Template Language file", "mtl");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			mtlFile = chooser.getSelectedFile();
		} else {
			System.exit(0);
		}

		cardLayout = new CardLayout();
		setLayout(cardLayout);

		reader = new TemplateReader(mtlFile);
		reader.parseDocument();

		builder = new MadlibBuilder();
		aMadlib = builder.textList(reader.getText())//
				.blankList(reader.getBlanks())//
				.build();

		buildInputPanel();
		buildResultPanel();

		add(inputPanel, "one");
		add(resultPanel, "two");
	}

	/**
	 * Utility method that builds a panel that allows a user to insert text for
	 * the various madlibs blanks.
	 */
	private void buildInputPanel() {
		inputPanel = new JPanel();

		finishButton = new JButton("Tell Me A Story");
		finishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Loop to check if all blanks are filled in
				for (int i = 0; i < labelList.size(); i++) {
					if (fieldList.get(i).getText().compareTo("") == 0) {
						JOptionPane.showMessageDialog(inputPanel,
								"Please fill in all blanks", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				finishStory();
				cardLayout.next(inputPanel.getParent());
			}

		});

		labelList = new ArrayList<JLabel>();
		fieldList = new ArrayList<JTextField>();

		// Adds the types and blanks to their respective Lists
		for (Integer i : aMadlib.getBlankPos()) {
			labelList.add(new JLabel(aMadlib.getText(i)));
			fieldList.add(new JTextField(TEXT_FIELD_SIZE));
		}

		inputPanel.setLayout(new GridLayout(labelList.size() + 1, 2, 4, 4));

		// Adds the type labels and the blanks to the inputPanel
		for (int i = 0; i < labelList.size(); i++) {
			inputPanel.add(labelList.get(i));
			inputPanel.add(fieldList.get(i));
		}

		inputPanel.add(finishButton);
	}

	/**
	 * Utility method that builds the panel that is used to display the
	 * completed madlib to the user along with options to play again or save the
	 * madlib.
	 */
	private void buildResultPanel() {
		resultPanel = new JPanel();
		resultPanel.setLayout(new BorderLayout());

		buttonPanel = new JPanel();

		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser saver = new JFileChooser();

				saver.setDialogType(JFileChooser.SAVE_DIALOG);
				int returnVal = saver.showSaveDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File saveFile = saver.getSelectedFile();
					saveFile = new File(saveFile.getAbsolutePath() + ".html");

					try {
						PrintWriter out = new PrintWriter(saveFile);
						out.print(aMadlib.toHTML());
						out.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(resultPanel,
								"Unable to locate file. Please try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		});

		buttonPanel.add(quitButton);
		buttonPanel.add(saveButton);

		resultPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Utility method that adds the user input to the story and creates a
	 * JEditorPane to display it.
	 */
	private void finishStory() {
		String fieldText;
		JEditorPane storyPane = new JEditorPane(); // Pane to display the
													// completed HTML story
		storyPane.setMargin(new Insets(5, 5, 5, 5));
		storyPane.setPreferredSize(new Dimension(230, 600));
		storyPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());

		/*
		 * Takes the current Madlib and adds HTML elements to the words the user
		 * has input to make the words stand out
		 */
		for (int i = 0; i < aMadlib.getBlankPos().size(); i++) {
			fieldText = "<b><font Color=#FF0000>" + fieldList.get(i).getText()
					+ "</font></b>";
			aMadlib.setText(aMadlib.getBlankPos().get(i), fieldText);
		}

		storyPane.setText(aMadlib.toHTML());
		resultPanel.add(storyPane, BorderLayout.CENTER);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Madlibs!");

		frame.add(new MadlibGUI());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
