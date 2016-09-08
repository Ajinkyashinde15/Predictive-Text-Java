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
public class ArrayQueue<T> implements Queue<T> {
    public static final int CAPACITY = 1000;
    
    private int capacity;
    private T array[];
    private int front;
    private int rear;
    
    public ArrayQueue() {
        this(CAPACITY);
    }
    
    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
        front = rear = 0;
    }
    
    public int size() {
        return (capacity + rear - front) % capacity;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public T front() {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        return array[front];
    }

    public void enqueue(T element) {
        if (size() == capacity-1) {
            throw new QueueFullException();
        }
        array[rear] = element;
        rear = (rear + 1) % capacity;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        T temp = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        return temp;
    }

}
