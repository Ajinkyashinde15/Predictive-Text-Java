package dsaii.impl;

import dsai.core.Entry;
import dsai.core.Iterator;
import dsai.core.List;
import dsai.core.Position;
import dsai.impl.LinkedList;
import dsai.impl.ListIterator;
import dsaii.core.Map;

/**
 * This is a partial implementation of the Map ADT that uses the Seperate
 * Chaining Collision Handling Strategy and the Division compression map.
 * 
 * To change the compression map, you should subclass this class and override
 * the hashFunction() method.
 * 
 * @author remcollier
 */
public class ChainMap<K,V> implements Map<K,V> {
    public static final int DEFAULT_SIZE                        = 101;
    
    /**
     * The number of entries stored in the map
     */
    private int size;
    
    /**
     * The array of lists
     */
    private List[] A;
    
    
    /**
     * Implementation of the Entry interface for storing entries in the Map
     * 
     * @param <K> the key
     * @param <V> the value
     */
    private class ChainMapEntry<K,V> implements Entry<K,V> {
        K key;
        V value;
        
        public ChainMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K key() {
            return key;
        }
        
        public V value() {
            return value;
        }
        
        @Override
        public String toString() {
            return "{" + key + "," + value + "}";
        }
    }
    
    /**
     * Creates a new instance of ChainMap of size DEFAULT_SIZE
     */
    public ChainMap() {
        this(DEFAULT_SIZE);
    }
    
    /**
     * Creates a new instance of ChainMap of the given size
     * 
     * @param N
     */
    public ChainMap(int N) {
        A = new List[N];
        size = 0;
    }
    
    /**
     * The number of entries stored in the chain map
     * 
     * @return
     */
    public int size() {
        return size;
    }
    
    /**
     * Is the chain map empty?
     * 
     * @return true if it is, false if it isn't
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Default implementation uses hashCode() for the Hash Code Map and
     * Division Method for the Compression Map.
     * 
     * @param k the key
     * @return the corresponding hash value
     */
    protected int hashFunction(K k) {
        return Math.abs(k.hashCode()) % A.length;
    }

    /**
     * Ancillary method that is used to retrieve the position of an entry in
     * a given List (if one exists). This method is used by the get, put, and
     * remove methods.
     * 
     * @param list the list to be searched
     * @param k the key we are looking for
     * @return the position of the entry in the list or null
     */
    private Position find(List list, K k) {
        Position p = list.first();
        while (p != list.last()) {
            if (((Entry<K,V>) p.element()).key().equals(k)) return p;
            p = list.next(p);
        }
        if (((Entry<K,V>) p.element()).key().equals(k)) return p;
        return null;
    }
    
    /**
     * Put a new entry with key k and value v into the map
     * @param <K> the key
     * @param <V> the value
     * @return the previous value associated with key k (if one exists>
     */
    public V put(K k, V v) {
        int h = hashFunction(k);
        V temp = null;
        if (A[h] == null) {
            A[h] = new LinkedList();
            A[h].insertLast(new ChainMapEntry<K,V>(k, v));
        } else {
            Position P = find(A[h], k);
            if (P == null) {
                A[h].insertLast(new ChainMapEntry<K,V>(k, v));
            } else {
                try {
                    Entry<K,V> entry = (Entry<K,V>) A[h].replace(P, new ChainMapEntry(k, v));
                    temp = entry.value();
                } catch (Exception e) {}
            }
        }
        size++;
        return temp;
    }
    
    /**
     * Retrieve a value from the map
     * @param k
     * @return
     */
    public V get(K k) {
        int h = hashFunction(k);
        if (A[h] == null) return null;
        Position P = find(A[h], k);
        if (P == null) return null;
        return ((Entry<K,V>) P.element()).value();
    }
    
    /**
     * Remove the entry from the map with the given key
     * @param k
     * @return
     */
    public V remove(K k) {
        int h = hashFunction(k);
        V temp = null;
        if (A[h] != null) {
            Position P = find(A[h], k);
            if (P != null) {
                try {
                    Entry<K,V> entry = (Entry<K,V>) A[h].remove(P);
                    size--;
                    temp = entry.value();
                } catch (Exception e) {}
            }
        }
        return temp;
    }
    
    /**
     * Return an iterator of the entries stored in the map
     * 
     * @return
     */
    public Iterator<Entry<K,V>> entries() {
        List<Entry<K,V>> list = new LinkedList<Entry<K,V>>();
        for (int i=0; i < A.length; i++) {
            List temp = (List) A[i];
            if (temp != null) {
                Iterator it = new ListIterator(temp);
                while (it.hasNext()) {
                    list.insertLast((Entry<K,V>) it.next());
                }
            }
        }
        
        return new ListIterator<Entry<K,V>>(list);
    }
    
    /**
     * Return an iterator of the keys stored in the map
     * 
     * @return
     */
    public Iterator<K> keys() {
        List<K> temp = new LinkedList<K>();
        Iterator iterator = entries();
        while (iterator.hasNext()) {
            temp.insertLast(((Entry<K,V>) iterator.next()).key());
        }
        return new ListIterator<K>(temp);
    }
    
    /**
     * Return an iterator of the values stored in the map
     * 
     * @return
     */
    public Iterator<V> values() {
        List<V> temp = new LinkedList<V>();
        Iterator iterator = entries();
        while (iterator.hasNext()) {
            temp.insertLast(((Entry<K,V>) iterator.next()).value());
        }
        return new ListIterator<V>(temp);
    }
    
    @Override
    public String toString() {
        String output = "";
        for (int i=0; i<A.length; i++) {
            output += i + ": ";
            if (A[i] != null) {
                Iterator it = new ListIterator(A[i]);
                while (it.hasNext()) {
                    Entry<K,V> entry = (Entry<K,V>) it.next();
                    output += entry + " ";
                }
            }
            output += "\n";
        }
        return output;
    }
    
    /**
     * Sample program that implements the Linear Probing example given in the class
     * 
     * @param args
     */
    public static void main(String[] args) {
        Map<Integer,String> map = new ChainMap<Integer,String>(13);
        System.out.println(map);
        
        map.put(18, "A");
        System.out.println(map);
        
        map.put(44, "B");
        System.out.println(map);
        
        map.put(41, "C");
        System.out.println(map);
        
        map.put(22, "D");
        System.out.println(map);
        
        map.put(59, "E");
        System.out.println(map);
        
        map.put(32, "F");
        System.out.println(map);
        
        map.put(31, "G");
        System.out.println(map);
        
        map.put(73, "H");
        System.out.println(map);

        map.remove(59);
        System.out.println(map);
    }
}
