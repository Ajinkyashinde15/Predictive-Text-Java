/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Iterator<T> {
    public boolean hasNext();
    public T next();
}
