/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsai.impl;

import dsai.core.Iterator;
import dsai.core.Vector;

/**
 *
 * @author remcollier
 */
public class VectorIterator<T> implements Iterator<T> {

    private int index;
    private Vector<T> vector;

    public VectorIterator(Vector<T> vector) {
        this.vector = vector;
        index = 0;
    }

    public boolean hasNext() {
        return index < vector.size();
    }

    public T next() {
        return vector.elemAtRank(index++);
    }
}
