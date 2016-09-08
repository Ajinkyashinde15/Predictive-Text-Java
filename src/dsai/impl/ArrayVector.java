/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.impl;

import dsai.core.RankOutOfBoundsException;
import dsai.core.Vector;

/**
 *
 * @author remcollier
 */
public class ArrayVector<T> implements Vector<T> {
    private static final int CAPACITY 			= 1000;

    private T[] array;
    private int capacity;
    private int size;

    public ArrayVector() {
        this(CAPACITY);
    }

    public ArrayVector(int capacity) {
        this.capacity = capacity;
        size = 0;
        array = (T[]) new Object[capacity];
    }

    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public T elemAtRank(int rank) {
        if ((rank < 0) || (rank > size()-1)) {
            throw new RankOutOfBoundsException();
        }
        
        return array[rank];
    }

    public T replaceAtRank(int rank, T element) {
        if ((rank < 0) || (rank > size()-1)) {
            throw new RankOutOfBoundsException();
        }
        
        T temp = array[rank];
        array[rank] = element;
        return temp;
    }

    public void insertAtRank(int rank, T element) {
         if ((rank < 0) || (rank > size)) {
            throw new RankOutOfBoundsException();
        }

        if (size == capacity) {
            capacity *= 2;
            T[] temp = (T[]) new Object[capacity];
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }

        for (int i = size; i > rank; i--) {
            array[i] = array[i-1];
        }

        array[rank] = element;
        size++;
    }

    public T removeAtRank(int rank) {
        if ((rank < 0) || (rank > size()-1)) {
            throw new RankOutOfBoundsException();
        }

        T element = array[rank];
        for (int i = rank; i < (size-1); i++) {
            array[i] = array[i+1];
        }

        array[size--] = null;

        return element;
    }
}
