/**
 * This interface is used to specify how the system manipulates / manages 
 * words.
 * 
 * @author remcollier
 */
public interface Word {
    /**
     * Return a String representation of the word.
     * 
     * @return
     */
    public String getWord();
    
    /**
     * Check if the word has any characters in it.
     * 
     * @return
     */
    public boolean isEmpty();
    
    /**
     * Associates the word with a type of processor.
     * 
     * @return
     */
    public int processorMode();
    
    /**
     * Removes the last character from the word
     */
    public void delete();
}
