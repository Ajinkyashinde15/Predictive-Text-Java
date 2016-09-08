/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.impl;

import dsai.core.Stack;
import dsai.core.StackEmptyException;

/**
 *
 * @author remcollier
 */
public class LinkedStack<T> implements Stack<T> {
    private int size;
    private Node<T> top;
    
    private class Node<T> {
        T element;
        Node next;
        
        Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }
    }
    
    public LinkedStack() {
        size = 0;
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T top() {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        
        return top.element;
    }

    public void push(T element) {
        top = new Node<T>(element, top);
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        
        T temp = top.element;
        top = top.next;
        size--;
        
        return temp;
    }
}
