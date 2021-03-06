import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Alex Krantz
 * Due date: May 28th
 * Class description: provides the logic for the user to create a new deck of cards
 */
public class CreateCardsController extends CardsController {
	// initialize parameters for bottomPanel object
	private String[] buttonNames = {"Next", "Done"};
	private ActionListener[] buttonListeners =  {new Next(), new Done()};
	
	/**
	 * Constructor initializes feature for creating cards
	 * @param f
	 */
	public CreateCardsController(JFrame f) {
		super(f);
		// Initializes panel objects
		main = new MainPanel();
		bottom = new BottomPanel(buttonNames, buttonListeners);
		// Add panels to frame
		main.addPanel(frame);
		bottom.addPanel(frame);
	}
	
	public class Next implements ActionListener {
		@Override
		/**
		 * Save user input as new flashcard in the deck and refresh GUI
		 */
		public void actionPerformed(ActionEvent e) {
			if (main.checkInput()) { // If user input is valid
				// Create new flashcard object with input and add to deck
				deck.add(new Flashcard(main.getFrontText(), main.getBackText()));
				System.out.println("Deck size = " + deck.size());
				// Reset input fields
				main.resetText();
			}
		}
	}
	
	public class Done implements ActionListener {
		@Override
		/**
		 * Save current card in the deck. Save the deck. Return to the menu.
		 */
		public void actionPerformed(ActionEvent e) {
			// Save current card
			deck.add(new Flashcard(main.getFrontText(), main.getBackText()));
			// Save deck
			JFileChooser fileInput = new JFileChooser();
			fileInput.showSaveDialog(frame);
			File file = fileInput.getSelectedFile();
			try {
				IO.saveDeck(deck, file);
			} catch (IOException err) {
				// TODO: GUI alert message
				err.printStackTrace();
			}
			// Remove panels from pane
			main.removePanel(frame);
			bottom.removePanel(frame);
			// Load menu
			SwingUtilities.invokeLater(new Runnable() {
				public void run () {
					new MenuController(frame);
				}
			});
		}
	}
	
	/**
	 * Abstract parent method has to be implemented but it is not actually used in this class
	 */
	protected void endOfDeckAction() {
		currentCard = new Flashcard("", "");
	}
}
