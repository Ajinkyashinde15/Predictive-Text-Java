/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Entry<K,V> {
    public K key();
    public V value();
}
