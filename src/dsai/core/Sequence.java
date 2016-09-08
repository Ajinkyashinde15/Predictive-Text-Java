/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dsai.core;

/**
 *
 * @author remcollier
 */
public interface Sequence extends Vector, List {
    public Position atRank(int rank);
    public int rankOf(Position position);
}
