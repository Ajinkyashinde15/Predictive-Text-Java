/**
 * This class implements the WordProcessor interface to provide the basic text
 * mode of operation.  As is implied in the design, WordProcessors generate
 * objects that are instances of classes that implement the Word interface.
 * 
 * As such, this class contains an inner class, called BasicTextWord, that
 * implements the Word interface. A basic text word is represented as a
 * StringBuffer that contains the characters the the user has entered so far.
 * 
 * @author remcollier
 */
public class BasicTextWordProcessor implements WordProcessor {
    /**
     * Implementation of a word for basic text mode
     */
    private class BasicTextWord implements Word {
        /**
         * The contents of the word
         */
        StringBuffer content;
        
        public BasicTextWord() {
            content = new StringBuffer();
        }
        
        /**
         * Check if the word has any characters in it.
         * 
         * @return
         */
        public boolean isEmpty() {
            return content.length() == 0;
        }
        
        /**
         * Removes the last character from the word
         */
        public void delete() {
            content.setLength(content.length()-1);
        }
        
        /**
         * Return a String representation of the word
         * 
         * @return
         */
        public String getWord() {
            return content.toString();
        }
        
        /**
         * Associates the word with a type of processor (in this case, 0 means
         * BasicTextWordProcessor).
         * 
         * @return
         */
        public int processorMode() {
            return 0;
        }
        
    }
    
    /**
     * This is the amount of time (in milliseconds) that must pass for two
     * keystrokes (for the same key) to be considered different characters.
     * 
     * Basically, if you press key 2 twice in less than a second, then you
     * will get a 'b', whereas if you press 2 twice, but with more than a
     * second gap inbetween the keystrokes, then you will get 'aa'.
     * 
     * This time currently represents 1 second.
     */
    private static final long KEY_STEP_TIME                     = 1000;
    
    /**
     * This array stores a map between keys and the characters that they
     * can be used to select. The key is used as an index into the first
     * array (the data structure is a 2 dimensional array), and the cycle
     * is used to index int the second array (i.e. if you have pressed the
     * key once, you get the first character, twice for the second character,
     * and so on).
     * As can be seen, key 0 has a cycle of 1 (there is only one option, a
     * space ' '), whereas key 2 has a cycle of 3 (you can choose 'a', 'b', or
     * 'c'). Key 1 does not have a character because it is the clear option.
     */
    private static char[][] characters = new char[][] { {' '}, {},
                                                        {'a', 'b', 'c'},
                                                        {'d', 'e', 'f'},
                                                        {'g', 'h', 'i'},
                                                        {'j', 'k', 'l'}, 
                                                        {'m', 'n', 'o'},
                                                        {'p', 'q', 'r', 's'},
                                                        {'t','u','v'},
                                                        {'w','x','y','z'} };
    
    /**
     * This is a reference to the TextSystem class, which is the main classs
     * for the implementation.
     */
    private TextSystem system;
    
    /**
     * The current word that is being edited
     */
    private BasicTextWord word;
    
    /**
     * The current cycle for the keystroke (i.e. the number of times the
     * current key has been pressed in succession for a single character).
     */
    private int keystrokeCycle;
    
    /**
     * The last key to be pressed
     */
    private int lastKeystroke;
    
    /**
     * The time (in milliseconds) that the last key was pressed
     */
    private long lastKeystrokeTime;
    
    /**
     * Constructor that takes a TextSystem instance as a parameter and 
     * initialises the BasicTextWordProcessor.
     * @param system
     */
    public BasicTextWordProcessor(TextSystem system) {
        this.system = system;        
        keystrokeCycle = 0;
        lastKeystrokeTime = -1;
        lastKeystroke = -1;
     }
   
    /**
     * Handle the keystroke - if it is a *, then add the current word to
     * the dictionary; if it is another keystroke, then add the appropriate
     * character to the word.
     * 
     * @param keystroke
     */
    public void handleKeystroke(int keystroke) {
        long currentKeystrokeTime = System.currentTimeMillis();
        
        if (keystroke == 10) {
            system.addToDictionary(word.getWord());
        } else if (keystroke != lastKeystroke) {
            keystrokeCycle = 0;
            word.content.append(characters[keystroke][keystrokeCycle]);
        } else {
            if ((currentKeystrokeTime - lastKeystrokeTime) > KEY_STEP_TIME) {
                keystrokeCycle = 0;
                word.content.append(characters[keystroke][keystrokeCycle]);
            } else {
                keystrokeCycle = (keystrokeCycle + 1) % characters[keystroke].length;
                word.content.setCharAt(word.content.length()-1, characters[keystroke][keystrokeCycle]);
            }
        }
        lastKeystrokeTime = currentKeystrokeTime;
        lastKeystroke = keystroke;
    }
    
    /**
     * Start the next word.
     */
    public void newWord() {
        word = new BasicTextWord();
        system.setCurrentWord(word);
    }
    
    /**
     * Set the current word
     * @param word
     */
    public void setWord(Word word) {
        if (word instanceof BasicTextWord) {
            this.word = (BasicTextWord) word;
        }
    }
}
