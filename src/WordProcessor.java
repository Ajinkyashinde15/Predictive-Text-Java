/**
 * A word processor represents the implementation of a mode of use in the
 * mobile phone text editor. As can be seen, implementations of this interface
 * must implement three methods.
 * 
 * @author remcollier
 */
public interface WordProcessor {
    /**
     * Handle the keystroke - if it is a *, then add the current word to
     * the dictionary; if it is another keystroke, then add the appropriate
     * character to the word.
     * 
     * @param keystroke
     */
    public void handleKeystroke(int keystroke);
    
    /**
     * Start the next word.
     */
    public void newWord();
    
    /**
     * Set the current word
     * @param word
     */
    public void setWord(Word word);
}
