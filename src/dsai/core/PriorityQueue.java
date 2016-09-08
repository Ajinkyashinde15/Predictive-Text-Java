/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface PriorityQueue<K, V> {
    public int size();
    public boolean isEmpty();
    public Entry<K,V> min();
    public Entry<K,V> insert(K key, V value);
    public Entry<K,V> removeMin();

}
