
import dsai.core.Iterator;
import dsai.core.List;
import dsai.core.Position;
import dsai.impl.LinkedList;
import dsai.impl.ListIterator;

/**
 * This class implements the WordProcessor interface to provide the predictive
 * text mode of operation.  As is implied in the design, WordProcessors generate
 * objects that are instances of classes that implement the Word interface.
 * 
 * As such, this class contains an inner class, called PredictiveTextWord, that
 * implements the Word interface. A predictive text word is represented as a
 * List of integer values that represent the keystrokes entered for that word
 * so far. Additionally, this class maintains a wordNumber field which
 * represents the word (from the list of possible words, that the user has 
 * currently selected as a match for the sequence of keystrokes.
 *
 * @author remcollier
 */
public class PredictiveTextWordProcessor implements WordProcessor {
    /**
     * Implementation of a word for predictive text mode
     */
    private class PredictiveTextWord implements Word {
        /**
         * The current sequence of keystrokes
         */
        List<Integer> keystrokes;
        
        /**
         * The current word selection
         */
        int wordNumber;
        
        /**
         * The list of possible word fragments
         */
        List<String> words;
        
        /**
         * Associates the word with a type of processor (in this case, 1 means
         * PredictiveTextWordProcessor).
         * 
         * @return
         */
        public int processorMode() {
            return 1;
        }
        
        /**
         * Default Constructor
         */
        public PredictiveTextWord() {
            keystrokes = new LinkedList<Integer>();
            wordNumber = 0;
        }
        
        
        /**
         * Check if the word has any characters in it.
         * 
         * @return
         */
        public boolean isEmpty() {
            return keystrokes.isEmpty();
        }
        
        /**
         * Removes the last character from the word
         */
        public void delete() {
            keystrokes.remove(keystrokes.last());
        }

        /**
         * Add another keystroke to the sequence of keystrokes. This requires
         * that a new list of words be retrieved from the dictionary that
         * represents the new sequence of keystrokes.
         * 
         * @param keystroke
         */
        public void addKeystroke(int keystroke) {
            keystrokes.insertLast(keystroke);
            words =  dictionary.findWords(keystrokes);
            wordNumber = 0;
        }
        
        /**
         * Return a String representation of the word.  If there is not
         * match in the dictionary for the current sequence of keystrokes,
         * then create a basic word (i.e. one that is based on the basic text
         * mode, and which assumes that each key was pressed only once per
         * second).
         * 
         * @return
         */
        public String getWord() {
            if (words == null) {
                return createBasicWord();
            }
            
            if (words.isEmpty()) {
                return createBasicWord();
            }
            
            wordNumber = wordNumber % words.size();
            int count = wordNumber;
            Position<String> pos = words.first();
            String word = pos.element();
            while (count > 0) {
                pos = words.next(pos);
                word = pos.element();
                count--;
            }
            
            return word;
        }
        
        /**
         * Creates a default word if the sequence of keystrokes is not in the
         * dictionary.
         * 
         * @return
         */
        private String createBasicWord() {
            StringBuffer buf = new StringBuffer();
            Iterator<Integer> it = new ListIterator<Integer>(keystrokes);
            while (it.hasNext()) {
                int key = it.next();
                if (key == 2) buf.append("a");
                if (key == 3) buf.append("d");
                if (key == 4) buf.append("g");
                if (key == 5) buf.append("j");
                if (key == 6) buf.append("m");
                if (key == 7) buf.append("p");
                if (key == 8) buf.append("t");
                if (key == 9) buf.append("w");
            }
            return buf.toString();
        }
        
        /**
         * Choose the next word.
         */
        public void selectNextWord() {
            wordNumber++;
        }
    }

    /**
     * A reference to the dictionary
     */
    private Dictionary dictionary;
    
    /**
     * The word currently being edited
     */
    private PredictiveTextWord word;
    
    /**
     * The main class of the system
     */
    private TextSystem system;
    
    /**
     * Constructor that initialises the word procesor (it takes the system class
     * and the dictionary as arguments).
     * @param system
     * @param dictionary
     */
    public PredictiveTextWordProcessor(TextSystem system, Dictionary dictionary) {
        this.system = system;
        this.dictionary = dictionary;
    }
    
    /**
     * Handle the keystroke - if it is a *, then add the current word to
     * the dictionary; if it is another keystroke, then add the appropriate
     * character to the word.
     * 
     * @param keystroke
     */
    public void handleKeystroke(int keystroke) {
        if (keystroke == 10) {
            word.selectNextWord();
        } else {
            word.addKeystroke(keystroke);
        }
    }
    
    /**
     * Start the next word.
     */
    public void newWord() {
        word = new PredictiveTextWord();
        system.setCurrentWord(word);
    }
    
    /**
     * Set the current word
     * @param word
     */
    public void setWord(Word word) {
        if (word instanceof PredictiveTextWord) {
            this.word = (PredictiveTextWord) word;
        }
    }
}
