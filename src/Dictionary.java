
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import dsai.core.Entry;
import dsai.core.Iterator;
import dsai.core.List;
import dsai.core.Position;
import dsai.impl.LinkedList;
import dsai.impl.ListIterator;
import dsai.impl.SortedListPriorityQueue;
import dsaii.core.Map;
import dsaii.core.Tree;
import dsaii.impl.ChainMap;
import dsaii.impl.LinkedTree;

/**
 * This class implements the dictionary component of the predictive text
 * module of our mobile phone demonstrator. Underlying the implementation
 * is a tree data structure in which each node contains:
 * 
 * <ul>
 *   <li> an integer value representing the current keystroke
 *   <li> a list of strings that contains the word fragments that correspond to
 *        the sequence of keystrokes in the path from the root node to that
 *        node.
 * </ul>
 * 
 * This node data is modelled through the inner Keystroke class.
 * 
 * @author remcollier
 */
public class Dictionary {
    /**
     * This class represents the contents of each node in the tree-based
     * implementation of the dictionary. Each node basically represents
     * a single keystroke, this class associates that keystroke with a list
     * of words the sequence of keystrokes corresponding to the path from the
     * root node to this node.
     */
    public class Keystroke {
        int key;
        private List<String> words;
    
        /**
         * Constructor for the Keystroke class, that takes an integer (the
         * keystroke) as a parameter.
         * 
         * @param key
         */
        public Keystroke(int key) {
            this.key = key;
            words = new LinkedList<String>();
        }
    
        /**
         * Add another word to this node (this means that the word is a
         * potential word for the combination of keystrokes that matches
         * the path from the root node to this node).
         * 
         * @param word
         */
        public void addWord(String word) {
            words.insertLast(word);		//insertLast is a Linked list method to insert an element at last position
        }
    
        /**
         * Return the list of words that is associated with this
         * keystroke. The current implementation does not impose any
         * ordering on the list (it is built based on the order in
         * which words are inserted into the node). In part B of the
         * assignment, you will need to modify this method to return
         * an ordered list of words.
         * 
         * @return a list of words
         */
        public List<String> getWords() {
        	Map<String, Integer> map = new ChainMap<String, Integer>();
        	Position<String> currWord = this.words.first();
        	while(true)
        	{
        		map.put(currWord.element(), 0);
        		if(currWord == this.words.last())		//Searching for second last word
        			break;
        		else
        			currWord = this.words.next(currWord);
        	}
        	
        	Position<Dictionary.Keystroke> currentPosition = null;
        	Iterator<Position<Dictionary.Keystroke>> treePositions = getTree().positions();	//find position
        	while(treePositions.hasNext())		//rotate until tree's  next position is NULL
        	{
        		currentPosition = treePositions.next();
        		if(currentPosition.element().equals(this))	//if current poisition is equal then it breaks the loop
        			break;
        	}
        	if(tree.parent(currentPosition) != tree.root())
        	{
        		Iterator<Position<Dictionary.Keystroke>> children = getTree().children(currentPosition);
	        	while(children.hasNext())
	        	{
	        		Position<Dictionary.Keystroke> child = children.next();
	        		List<String> childWords = child.element().words;
	        		Position<String> position = childWords.first();
	        		while(true)
	        		{
	        			map.put(position.element().substring(0, position.element().length()-1), map.get(position.element().substring(0, position.element().length()-1))+1);
	        			if(position == childWords.last())
	            			break;
	            		else
	            			position = childWords.next(position);
	        		}
	        	}
	        	Iterator<Entry<String,Integer>> mapItr = map.entries();
	        	SortedListPriorityQueue<Integer, String> sortedList = new SortedListPriorityQueue<Integer, String>();
	        	while(mapItr.hasNext())
	        	{
	        		Entry<String,Integer> entry = mapItr.next();
	        		sortedList.insert(entry.value(), entry.key());
	        	}
	        	List<String> newWords = new LinkedList<String>();
	        	while(true)
	        	{
	        		if(sortedList.isEmpty())
	        			break;
	        		else
	        		{	
	        			Entry<Integer,String> sortedentry = sortedList.removeMin();
	        			newWords.insertFirst(sortedentry.value());
	        		}
	        	}
	        	words = newWords;
        	}
        	return words;
        }
    
        /**
         * Generate a string representation of the node data for outputing
         * of the state of the tree during testing.
         * 
         * @return
         */
        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append(key);
            buf.append(":");
            Iterator<String> it = new ListIterator<String>(words);
            while(it.hasNext()) {
                buf.append(" ");
                buf.append(it.next());
            }
            return buf.toString();
        }
    }

    /**
     * This map associates characters with keystrokes and is used by the
     * insertion algorithm to work out how to add words to the tree.
     */
    private static Map<Character, Integer> characterMap;
    
    /**
     * Initialization block for the characterMap
     */
    static {
        characterMap = new ChainMap<Character, Integer>();
        characterMap.put('a',2);
        characterMap.put('b',2);
        characterMap.put('c',2);
        characterMap.put('d',3);
        characterMap.put('e',3);
        characterMap.put('f',3);
        characterMap.put('g',4);
        characterMap.put('h',4);
        characterMap.put('i',4);
        characterMap.put('j',5);
        characterMap.put('k',5);
        characterMap.put('l',5);
        characterMap.put('m',6);
        characterMap.put('n',6);
        characterMap.put('o',6);
        characterMap.put('p',7);
        characterMap.put('q',7);
        characterMap.put('r',7);
        characterMap.put('s',7);
        characterMap.put('t',8);
        characterMap.put('u',8);
        characterMap.put('v',8);
        characterMap.put('w',9);
        characterMap.put('x',9);
        characterMap.put('y',9);
        characterMap.put('z',9);
    }
    
    /**
     * The tree
     */
    private Tree<Keystroke> tree;
    
    /**
     * Default Constructor that creates an empty dictionary.
     */
    public Dictionary() {
        tree = new LinkedTree<Keystroke>();
        tree.addRoot(new Keystroke(-1));
    }
    
    public Tree<Keystroke> getTree() 
    {
    	return tree;
    }
    
    /**
     * Load the specified dictionary file. Each word in the file must
     * be inserted into the dictionary.
     * 
     * @param filename the dictionary file to be loaded
     */
    public void load(String filename) {
    	BufferedReader in = null;
    	try {
    		in = new BufferedReader(new FileReader(filename));
    	}
    	catch (FileNotFoundException ex) {
    		System.out.println("No Such File: " + filename);
    	}
    	try {
    		while(true){
    			String line = in.readLine(); // reads a line from the file or returns
    										// null if there is no more to read
    			if(line != null)
    				insert(line);
    			else
    				break;
    		}
    	}
    	catch (IOException ioe) {
    		System.out.println("Error reading from: " + filename);
    	}
    	try {
    		in.close(); // closes the file
    	}
    	catch (IOException ioe) {
    		System.out.println("Error closing: " + filename);
    	}
    }
    
    /**
     * Insert the word into the dictionary. This algorithm loops through the
     * characters in the word, and uses the character map to work out what
     * keystroke should be used to select that character (e.g. a,b,c would
     * be selected by pressing the 2 key).
     * 
     * For each sequence of keystrokes, the substring that corresponds to that
     * sequence is stored at the corresponding node so that 
     * 
     * @param word
     */
    public void insert(String word) {
    	char[] charArray = word.toCharArray();
		Position<Keystroke> currentNode = tree.root();		//find current/last posion of tree node
		String wordToAdd = "";
    	for(int i = 0; i < charArray.length; i++)
    	{
    		int keystroke = characterMap.get(charArray[i]);		//access index element of tree node
    		wordToAdd = wordToAdd + charArray[i];
    		boolean added = false;
    		boolean hasChildren = false;
    		Iterator<Position<Keystroke>> children = tree.children(currentNode);
    		while(children.hasNext() && !added)					//check for child and last node
    		{
    			hasChildren = true;
    			currentNode = children.next();
    			Keystroke currKeystroke = currentNode.element();
    			if(currKeystroke.key == keystroke)				
    			{
        			boolean isPresent = false;
    				List<String> words = currKeystroke.getWords();
    				Position<String> currPosition = words.first();  //get link list first element 
    				Position<String> lastPosition = words.last();	 //get link list last element 
    				while(true)
    				{
    					if(currPosition.element().equalsIgnoreCase(wordToAdd))		 //if current and new element is same 
    					{
    						isPresent = true;
    						added = true;
    						break;
    					}
    					if(currPosition == lastPosition)
    						break;
    					else
    						currPosition = words.next(currPosition);
    				}
    				if(!isPresent)
    				{
    					currKeystroke.addWord(wordToAdd);
    					added = true;
    				}
    			}
    		}
    		if(!hasChildren)
    		{
    			Keystroke newKeystroke= new Keystroke(keystroke);
    			newKeystroke.addWord(wordToAdd);
    			currentNode = tree.addChild(currentNode, newKeystroke);
    		}
    		else if(hasChildren && !added)
    		{
    			currentNode = tree.parent(currentNode);
    			Keystroke newKeystroke= new Keystroke(keystroke);
    			newKeystroke.addWord(wordToAdd);
    			currentNode = tree.addChild(currentNode, newKeystroke);
    		}
    	}
    }
    
    /**
     * Output the state of the dictionary (via delegation to the underlying
     * tree implementation).
     * 
     * @return
     */
    @Override
    public String toString() {
        return tree.toString();
    }
    
    /**
     * Find the list of words that corresponds to the given sequence of
     * keystrokes.
     * 
     * @param keystrokes a sequence of keystrokes
     * 
     * @return a list of words
     */
    public List<String> findWords(List<Integer> keystrokes) {   
    	List<String> predictedWords = new LinkedList<String>();		//create new link list for finding predictedWords
    	Position<Keystroke> currentNode = tree.root();				//access root of the tree
		Position<Integer> currKeystroke = keystrokes.first();		//access first position link list
    	for(int i=0; i<keystrokes.size(); i++)
    	{
    		Iterator<Position<Keystroke>> children = tree.children(currentNode);	//access currentnode child
    		boolean hasChildren = false;
			boolean keyPresent = false;
    		while(children.hasNext())				//iterate until childern is nulls 
    		{
    			hasChildren = true;
    			currentNode = children.next();		
    			if(currentNode.element().key == currKeystroke.element())
    			{
    				keyPresent = true;
    				if(i == keystrokes.size()-1)
    					predictedWords = currentNode.element().getWords();
    				break;
    			}
    		}
    		if(!hasChildren)
    			return null;
    		else if(hasChildren && !keyPresent)
    			return null;
    		if(currKeystroke != keystrokes.last())
    			currKeystroke = keystrokes.next(currKeystroke);
    	}

        /**
         * The default null response will cause the invoking method to
         * create a default word based on the sequence of keystrokes (i.e.
         * the same behaviour as if there was not entry in the dictionary
         * for the sequence of keystrokes).
         */
        return predictedWords;	//return predictedWords
    }
}
