/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsaii.core;

import dsai.core.Iterator;
import dsai.core.Position;

/**
 *
 * @author remcollier
 */
public abstract interface AbstractTree<T> {
    public int size();
    public boolean isEmpty();
    
    public Position<T> root();
    public void addRoot(T data);
    public boolean isRoot(Position<T> p);
    public Position<T> parent(Position<T> p);
    public Iterator<Position<T>> children(Position<T> p);
    public boolean isInternal(Position<T> p);
    public boolean isExternal(Position<T> p);
    public T replace(Position<T> p, T t);
    public Iterator<Position<T>> positions();
    public Iterator<T> elements();
}
