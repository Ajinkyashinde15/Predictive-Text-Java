/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Queue<T> {
	// accessor methods
	public int size(); 
	public boolean isEmpty();
	public T front();

	// update methods
	public void enqueue (T element);
	public T dequeue();
}
