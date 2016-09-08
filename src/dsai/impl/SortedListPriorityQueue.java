/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsai.impl;

import dsai.core.Entry;
import dsai.core.Comparator;
import dsai.core.EmptyPriorityQueueException;
import dsai.core.List;
import dsai.core.Position;
import dsai.core.PriorityQueue;
import dsai.core.UncomparableException;

/**
 *
 * @author remcollier
 */
public class SortedListPriorityQueue<K,V> implements PriorityQueue<K,V> {
    private List<Entry<K,V>> list;
    private Comparator comp;

    private static class DefaultComparator implements Comparator {
        public int compare(Object a, Object b) {
            try {
                return ((Comparable) a).compareTo(b);
            } catch (ClassCastException cce) {
                throw new UncomparableException();
            }
        }
    }

    private class SimpleEntry implements Entry<K,V> {
        K key;
        V value;

        public SimpleEntry(K k, V v) {
            key = k;
            value = v;
        }

        public K key() {
            return key;
        }

        public V value() {
            return value;
        }
    }

    public SortedListPriorityQueue() {
        this(new DefaultComparator());
    }

    public SortedListPriorityQueue(Comparator c) {
        comp = c;
        list = new LinkedList<Entry<K,V>>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Entry<K,V> insert(K key, V value) {
        Entry<K,V> entry = new SimpleEntry(key, value);
        if (list.isEmpty()) {
            list.insertFirst(entry);
        } else if (comp.compare(key, list.last().element().key()) >= 0) {
            list.insertLast(entry);
        } else {
            Position<Entry<K,V>> cur = list.first();
            while (comp.compare(key, cur.element().key()) >= 0) {
                cur = list.next(cur);
            }
            list.insertBefore(cur, entry);
        }
        return entry;
    }

    public Entry<K,V> min() throws EmptyPriorityQueueException {
        if (list.isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
        return list.first().element();
    }

    public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
        if (list.isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
        Position<Entry<K,V>> posn = list.first();
        list.remove(posn);
        return posn.element();
    }
}
