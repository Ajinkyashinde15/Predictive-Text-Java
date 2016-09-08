/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsai.impl;

import dsai.core.Position;
import dsai.core.Iterator;
import dsai.core.List;

/**
 *
 * @author remcollier
 */
public class ListIterator<T> implements Iterator<T> {

    private Position<T> position;
    private List<T> list;

    public ListIterator(List<T> list) {
        this.list = list;
    }

    public boolean hasNext() {
        if (position == null) {
            return !list.isEmpty();
        } else {
            try {
                list.next(position);
            } catch (RuntimeException e) {
                return false;
            }
        }
        return true;
    }

    public T next() {
        if (position == null) {
            position = list.first();
        } else {
            position = list.next(position);
        }
        return position.element();
    }
}
