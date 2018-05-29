import java.util.ArrayList;

public abstract class TrieMapBase<T> {
	
	/**
	 * inserts the value with the given key to this trie
	 */
	public abstract void insert(String key, T value);
	
	/**
	 * deletes the value with the given key
	 * @param 	key
	 * @return 	true, if an item with the given key exists;
	 * 			false, otherwise
	 */
	public abstract boolean delete(String key);
	
	/**
	 * looks for the value with the given key
	 * @param 	key
	 * @return	value of the item, if given key exists;
	 * 			null, otherwise
	 */
	public abstract T search(String key);
	
	/**
	 * returns the number of nodes (not just words) in this trie
	 * @return
	 */
	public abstract int nodeCount();
	
	/**
	 * returns all values added to this Trie in a breadth-first order
	 */
	public abstract ArrayList<T> returnBFSOrder();
	

}
