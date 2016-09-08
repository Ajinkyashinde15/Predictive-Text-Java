import dsai.core.List;
import dsai.impl.LinkedList;

/**
 * This class implements the main method for the application. You should
 * run this class. You will need to modify this file to load the dictionary
 * as is instructed in question A4.
 * 
 * @author remcollier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               Dictionary dictionary = new Dictionary();
                dictionary.load("dictionary.txt");
                dictionary.insert("fin");
                System.out.println(dictionary);
                List<Integer> keystrokes = new LinkedList<Integer>();
                keystrokes.insertLast(2);
                keystrokes.insertLast(4);
                keystrokes.insertLast(3);
                System.out.println(dictionary.findWords(keystrokes));
                new Keyboard(new TextSystem(dictionary)).setVisible(true);
            }
        });
    }

}
