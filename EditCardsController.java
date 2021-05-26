import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EditCardsController extends CardsController {
	private JFileChooser fileInput;
	private File file;
	
	//private String[] buttonNames = {"Create", "Delete", "Previous", "Next", "Done"};
	private String[] buttonNames = {"Next", "Done"};
	private ActionListener[] buttonListeners = {new Next(), new Done()};
	
		
	public EditCardsController(JFrame f) {
		super(f);
		
		main = new MainPanel();
		bottom = new BottomPanel(buttonNames, buttonListeners);
		main.addPanel(frame);
		bottom.addPanel(frame);
		
		// Get file with deck data
		fileInput = new JFileChooser();
		fileInput.showOpenDialog(frame);
		file = fileInput.getSelectedFile();
		// Load deck
		try {
			deck = IO.loadDeck(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currentCard = deck.get(index);
		main.showCard(currentCard);
	}
	
	public class Next implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (saveCurrentCard())  {
				incrementCurrentCard();
				main.showCard(currentCard);
				main.focusFront();
			}
		}
	}
	
	public class Done implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (saveCurrentCard()) {
				try {
					IO.saveDeck(deck, file);
				} catch (IOException err) {
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
	}
	
	protected void endOfDeckAction() {
		currentCard = new Flashcard("", "");
	}
	
	
}
