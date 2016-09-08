/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsaii.impl;

import dsai.core.InvalidPositionException;
import dsai.core.Iterator;
import dsai.core.List;
import dsai.core.Position;
import dsai.impl.LinkedList;
import dsai.impl.ListIterator;
import dsaii.core.NoneEmptyTreeException;
import dsaii.core.Tree;

/**
 *
 * @author remcollier
 */
public class LinkedTree<T> implements Tree<T> {
    private int size;
    private Node<T> root;
    
    private class Node<T> implements Position<T> {
        T data;
        Node<T> parent;
        List<Node<T>> children;
        boolean deleted;
        
        public Node(Node<T> parent, T data) {
            this.parent = parent;
            this.data = data;
            this.children = new LinkedList<Node<T>>();
            deleted = false;
        }
        
        public T element() {
            return data;
        }
        
        @Override
        public String toString() {
            return "{" + data + "}";
        }
    }

    public LinkedTree() {
        size=0;
    }
    
    public LinkedTree(T data) {
        size=0;
        root = new Node<T>(null, data);
    }
    
    private Node<T> checkPosition(Position<T> position) {
        if (position instanceof Node) {
            Node<T> node = (Node<T>) position;
            if (node.deleted == false) {
                return node;
            }
        }
        
        throw new InvalidPositionException();
    }
    
    public Position<T> addChild(Position<T> p, T data) {
        Node<T> node = checkPosition(p);
        Node<T> newNode = new Node<T>(node, data);
        node.children.insertLast(newNode);
        size++;
        return newNode;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Position<T> root() {
        return root;
    }

    public void addRoot(T data) {
        if (root != null) {
            // The root node already exists, so you should use replace 
            throw new NoneEmptyTreeException();
        }
        
        root = new Node<T>(null, data);
        size = 1;
    }

    public boolean isRoot(Position<T> p) {
        return root == p;
    }

    public Position<T> parent(Position<T> p) {
        return checkPosition(p).parent;
    }

    public Iterator<Position<T>> children(Position<T> p) {
        List<Position<T>> list = new LinkedList<Position<T>>();
        ListIterator<Node<T>> it = new ListIterator<Node<T>>(checkPosition(p).children); 
        while (it.hasNext()) {
            list.insertLast(it.next());
        }
        return new ListIterator<Position<T>>(list);
    }

    public boolean isInternal(Position<T> p) {
        return checkPosition(p).children.size() > 0;
    }

    public boolean isExternal(Position<T> p) {
        return checkPosition(p).children.size() == 0;
    }

    public T replace(Position<T> p, T t) {
        Node<T> node = checkPosition(p);
        T temp = node.data;
        node.data = t;
        return temp;
    }

    public Iterator<Position<T>> positions() {
        List<Position<T>> positions = new LinkedList<Position<T>>();
        preOrderPositions(positions, root);
        return new ListIterator<Position<T>>(positions);
    }
    
    private void preOrderPositions(List<Position<T>> list, Node<T> node) {
        list.insertLast(node);
        
        ListIterator<Node<T>> it = new ListIterator<Node<T>>(node.children); 
        while (it.hasNext()) {
            preOrderPositions(list, it.next());
        }
    }

    public Iterator<T> elements() {
        List<T> elements = new LinkedList<T>();
        preOrderElements(elements, root);
        return new ListIterator<T>(elements);
    }
    
    private void preOrderElements(List<T> list, Node<T> node) {
        list.insertLast(node.data);
        
        ListIterator<Node<T>> it = new ListIterator<Node<T>>(node.children); 
        while (it.hasNext()) {
            preOrderElements(list, it.next());
        }
    }

    public T remove(Position<T> p) {
        Node<T> v = checkPosition(p);
        
        if (v.children.size() > 1) {
            // Cannot remove a node that more than 1 child
            throw new InvalidPositionException();
        }
        
        // Set deleted flag to true so that any references to this node cannot
        // be used in future tree operations
        v.deleted = true;
        
        T temp = v.data;
        if (root == v) {
            if (v.children.size() == 1) {
                Node<T> child = v.children.first().element();
                root = child;
                child.parent = null;
            } else {
                root = null;
            }
        } else {
            Node<T> parent = v.parent;
            
            // Find v in the list of children
            Position<Node<T>> pos = parent.children.first();
            while (!pos.element().equals(v)) {
                pos = parent.children.next(pos);
            }
            
            // Remove v from the list of children
            parent.children.remove(pos);

            if (v.children.size() == 1) {
                Node<T> child = v.children.first().element();
                
                // Add v's child as a child of the parent
                parent.children.insertLast(child);

                child.parent = parent;
            }
        }
        
        size--;
        return temp;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        
        addToString(output, new String(), root);
        
        return output.toString();
    }
    
    private void addToString(StringBuffer output, String tabs, Node node) {
        output.append(tabs);
        output.append(node);
        output.append("\n");
        if (node != null) {
            Iterator<Node> it = new ListIterator<Node>(node.children);
            while (it.hasNext()) {
                addToString(output, tabs+"\t", it.next());
            }
        }
    }
    
    public static void main(String[] args) {
        Tree<Integer> tree = new LinkedTree<Integer>();
        tree.addRoot(5);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        Position<Integer> pos51 = tree.addChild(tree.root(), 51);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        Position<Integer> pos52 = tree.addChild(tree.root(), 52);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        tree.addChild(tree.root(), 53);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        Position<Integer> pos54 = tree.addChild(tree.root(), 54);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        Position<Integer> pos541 = tree.addChild(pos54, 541);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        Position<Integer> pos511 = tree.addChild(pos51, 511);
        System.out.println("\n\n=======================");
        System.out.println(tree);
        tree.remove(pos52);
        System.out.println("\n\n=======================");
        System.out.println(tree);
    }
}
