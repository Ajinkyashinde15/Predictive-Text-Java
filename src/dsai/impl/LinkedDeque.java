/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.impl;

import dsai.core.Deque;
import dsai.core.DequeEmptyException;

/**
 *
 * @author remcollier
 */
public class LinkedDeque<E> implements Deque<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;
    
    private class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;
        
        public Node(E element, Node next, Node prev) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
    
    public LinkedDeque() {
        first = new Node<E>(null, null, null);
        last = new Node<E>(null, first, null);
        first.next = last;
        size = 0;
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) {
            throw new DequeEmptyException();
        }
        
        return first.next.element;
    }

    public E last() {
        if (isEmpty()) {
            throw new DequeEmptyException();
        }
        
        return last.prev.element;
    }

    public void insertFirst(E element) {
        Node<E> node = new Node<E>(element, first.next, first);
        first.next.prev = node;
        first.next = node;
        size++;
    }

    public void insertLast(E element) {
        Node<E> node = new Node<E>(element, last, last.prev);
        last.prev.next = node;
        last.prev = node;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new DequeEmptyException();
        }
        
        E temp = first.next.element;
        first.next.next.prev = first;
        first.next = first.next.next;
        size--;
        return temp;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new DequeEmptyException();
        }
        
        E temp = last.prev.element;
        last.prev.prev.next = last;
        last.prev = last.prev.prev;
        size--;
        return temp;
    }
}
