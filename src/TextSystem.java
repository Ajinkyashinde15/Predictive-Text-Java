
import dsai.core.Iterator;
import dsai.core.List;

import dsai.impl.LinkedList;
import dsai.impl.ListIterator;

/**
 * The main class. All interface events result in calls to this class, and no
 * other part of the system is externally accessible.
 * 
 * @author remcollier
 */
public class TextSystem {
    /**
     * Number of word processor modules loaded (fixed at 2 for this implementation).
     */
    private static final int PROCESSOR_MODES                        = 2;
    
    /**
     * String labels for each mode.
     */
    private static String[] modes = new String[] {"ALPHA", "PREDICTIVE"};
    
    /**
     * The dictionary
     */
    private Dictionary dictionary;
    
    /**
     * An array of word processors
     */
    private WordProcessor[] processors;
    
    /**
     * The current processing mode (0 or 1 currently)
     */
    private int processorMode;
    
    /**
     * The list of words that makes up the current text message
     */
    private List<Word> words;
    
    /**
     * The word currently being edited
     */
    private Word currentWord;
    
    /**
     * Constructor that takes a dictionary as an argument
     * @param dictionary
     */
    public TextSystem(Dictionary dictionary) {
        this.dictionary = dictionary;
        
        // Initialise the word processor array
        processors = new WordProcessor[PROCESSOR_MODES];
        processors[0] = new BasicTextWordProcessor(this);
        processors[1] = new PredictiveTextWordProcessor(this, dictionary);
        
        // Default mode is basic text mode
        processorMode = 0;
        
        // Create the first word and initialise the word list.
        processors[processorMode].newWord();
        words = new LinkedList<Word>();
    }
    
    /**
     * Set the current word (used by WordProcessor implementations
     * @param word
     */
    public void setCurrentWord(Word word) {
        currentWord = word;
    }
    
    /**
     * Write the current word to the word list and create a new word
     */
    public void storeCurrentWord() {
        if (!currentWord.isEmpty()) {
            words.insertLast(currentWord);
        }
    }
    
    /**
     * Get a string representation of the text so far
     * @return
     */
    public String getText() {
        StringBuffer buf = new StringBuffer();
        
        Iterator<Word> it = new ListIterator<Word>(words);
        while (it.hasNext()) {
            buf.append(it.next().getWord());
            buf.append(" ");
        }
        
        buf.append(currentWord.getWord());
        return buf.toString();
    }
    
    /**
     * Handle a user command (from the interface).
     * 
     * @param keystroke
     */
    public void handleKeystroke(int keystroke) {
        switch (keystroke) {
            case 0:
                // space pressed
                storeCurrentWord();
                processors[processorMode].newWord();
                break;
            case 1:
                // clear pressed
                if (currentWord.isEmpty()) {
                    if (!words.isEmpty()) {
                        currentWord = words.last().element();
                        words.remove(words.last());
                        processorMode = currentWord.processorMode();
                        processors[processorMode].setWord(currentWord);
                    }
                } else {
                    currentWord.delete();
                }
                break;
            case 11:
                // hash pressed (change usage mode)
                processorMode = (processorMode + 1) % PROCESSOR_MODES;
                storeCurrentWord();
                processors[processorMode].newWord();
                break;
            default:
                // not a special case, so delegate the current word processor
                // to handle the keystroke
                processors[processorMode].handleKeystroke(keystroke);
        }
        
    }

    /**
     * Return the current processor mode (used by the interface).
     * @return
     */
    public String getMode() {
        return modes[processorMode];
    }

    /**
     * Insert a new word into the dictionary
     */
    public void addToDictionary(String word) {
        dictionary.insert(word);
    }
}
