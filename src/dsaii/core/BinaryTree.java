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
public interface BinaryTree<T> extends AbstractTree<T> {
    public Position<T> leftChild(Position<T> p);
    public Position<T> rightChild(Position<T> p);
    public Position<T> sibling(Position<T> p);
    public void addLeftChild(Position<T> p, T data);
    public void addRightChild(Position<T> p, T data);
    public T remove(Position<T> p);
}
