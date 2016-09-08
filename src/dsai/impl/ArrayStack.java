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
public class ArrayStack<T> implements Stack<T> {

    public static final int CAPACITY = 1000;
    private int capacity;
    private T array[];
    private int top = -1;

    /**
     * Initialize the stack with default capacity
     */
    public ArrayStack() {
        this(CAPACITY);
    }

    /**
     * Initialize the stack with a given capacity
     * @param capacity the capacity of the stack
     */
    public ArrayStack(int cap) {
        capacity = cap;
        array = (T[]) new Object[capacity];
    }

    /**
     * get the stack size
     * @return the size of the stack
     */
    public int size() {
        return (top + 1);
    }

    /**
     * Checks whether or not the stack is empty
     * @return true iff  the stack is empty
     */
    public boolean isEmpty() {
        return (top < 0);
    }

    /**
     * Push a new element on the stack
     * @param obj the element to be pushed onto the stack
     */
    public void push(T obj) {
        if (size() == capacity) {
            throw new StackFullException();
        }
        array[++top] = obj;
    }

    /**
     * Get the element that is currently on top of the stack
     * return the top element
     */
    public T top() {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        return array[top];
    }

    /**
     * Remove and return the top element on the stack
     * @return the top element
     */
    public T pop() {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        T elem = array[top];
        array[top--] = null;
        return elem;
    }
}
