/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Stack<T> {
	// accessor methods
	public int size(); 
	public boolean isEmpty();
	public T top();

	// update methods
	public void push (T element);
	public T pop();
}
