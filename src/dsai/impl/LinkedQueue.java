/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.impl;

import dsai.core.Queue;
import dsai.core.QueueEmptyException;

/**
 *
 * @author remcollier
 */
public class LinkedQueue<T> implements Queue<T> {
    private int size;
    private Node<T> front;
    private Node<T> rear;
    
    private class Node<T> {
        T element;
        Node next;
        
        Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }
    }
    
    public LinkedQueue() {
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T front() {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }

        return front.element;
    }

    public void enqueue(T element) {
        rear = new Node<T>(element, rear);
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        
        T temp = front.element;
        front = front.next;
        size--;
        return temp;
    }

}
