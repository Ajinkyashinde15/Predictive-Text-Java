/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsaii.core;

import dsai.core.Position;

/**
 *
 * @author remcollier
 */
public interface Tree<T> extends AbstractTree<T> {
    public Position<T> addChild(Position<T> p, T data);
    public T remove(Position<T> p); 
}
