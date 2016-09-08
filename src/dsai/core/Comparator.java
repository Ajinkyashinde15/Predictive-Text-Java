/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Comparator {
    public int compare(Object a, Object b) throws UncomparableException;

}
