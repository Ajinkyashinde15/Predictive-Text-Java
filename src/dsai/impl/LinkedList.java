/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsai.impl;

import dsai.core.BoundaryViolationException;
import dsai.core.EmptyListException;
import dsai.core.InvalidPositionException;
import dsai.core.List;
import dsai.core.Position;

/**
 *
 * @author remcollier
 */
public class LinkedList<T> implements List<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private class Node<T> implements Position<T> {
        T element;
        Node next;
        Node prev;
        List<T> list;

        public Node(List<T> list, T element, Node<T> next, Node<T> prev) {
            this.list = list;
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public T element() {
            return element;
        }
    }

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Position<T> insertLast(T element) {
        Node<T> node = new Node<T>(this, element, null, last);

        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }

        last = node;
        size++;
        return node;
    }

    public Position<T> insertAfter(Position<T> p, T e) {
        Node<T> pos = validatePosition(p);

        if (pos == last) {
            return insertLast(e);
        }
        Node<T> node = new Node<T>(this, e, pos.next, pos);

        pos.next.prev = node;
        pos.next = node;

        size++;
        return node;
    }

    public T remove(Position<T> p) {
        Node<T> pos = validatePosition(p);

        if (pos == first) {
            first = first.next;
        } else {
            pos.prev.next = pos.next;
        }
        
        if (pos == last) {
            last = last.prev;
        } else {
            pos.next.prev = pos.prev;
        }
        size--;
        return pos.element;
    }

    /**
     * Updated validatePosition method
     */
    private Node<T> validatePosition(Position<T> p) {
        if (!(p instanceof Node)) {
            throw new InvalidPositionException();
        }

        Node<T> node = (Node<T>) p;
        if (node.list != this) {
            throw new InvalidPositionException();
        }

        return node;
    }

    public Position<T> first() {
        if (first == null) {
            throw new EmptyListException();
        }
        
        return first;
    }

    public Position<T> last() {
        if (first == null) {
            throw new EmptyListException();
        }
        
        return last;
    }

    public Position<T> prev(Position<T> p) {
        Node<T> pos = validatePosition(p);
        
        if (pos == first) {
            throw new BoundaryViolationException();
        }
        
        return pos.prev;
    }

    public Position<T> next(Position<T> p) {
        Node<T> pos = validatePosition(p);
        
        if (pos == last) {
            throw new BoundaryViolationException();
        }
        
        return pos.next;
    }

    public Position<T> insertFirst(T e) {
        Node<T> node = new Node<T>(this, e, first, null);

        if (last == null) {
            last = node;
        } else {
            first.prev = node;
        }

        first = node;
        size++;
        return node;
    }

    public Position<T> insertBefore(Position<T> p, T e) {
        Node<T> pos = validatePosition(p);

        if (pos == first) {
            return insertFirst(e);
        }
        
        Node<T> node = new Node<T>(this, e, pos, pos.prev);
        
        pos.prev.next = node;
        pos.prev = node;

        size++;
        return node;
    }

    public T replace(Position<T> p, T e) {
        Node<T> pos = validatePosition(p);
        
        T temp = pos.element;
        pos.element = e;
        return temp;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        if (!isEmpty()) {
            boolean first = true;
            Position<T> pos = first();
            while (!last().equals(pos)) {
                if (first) {
                    first = false;
                } else {
                    buf.append(",");
                }
                buf.append(pos.element());
                pos = next(pos);
            }

            if (first) {
                first = false;
            } else {
                buf.append(",");
            }
            buf.append(pos.element());
        }
        buf.append("]");
        return buf.toString();
    }
}
