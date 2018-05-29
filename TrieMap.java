import java.util.*;

public class TrieMap<T> extends TrieMapBase<T> {
	public Node<T> root;
	public int nodeNumber;
	
	public TrieMap(int alphabetSize) {
		Node.alphabetSize=alphabetSize;
		root = new Node<T>(null);
		nodeNumber++;
	}

	
	/**
	 * Returns 	true if key appears in text as a substring;
	 * 			false, otherwise
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static boolean containsSubstr(String text, String key) {
		Scanner textReader = new Scanner(text);
		TrieMap<Boolean> textMap = new TrieMap<Boolean>(26);
		
		while(textReader.hasNext()) {
			String word = textReader.next();
			for(int i=0,n=word.length();i<n;i++) {
				String word2=word.substring(i, word.length());
				for(int j=0,l=word2.length();j<l;j++) {
					textMap.insert(word2.substring(0,j+1),true);
				}
			}
		}
		
		Boolean check = textMap.search(key);
		if(check != null) {
			return check.booleanValue();
		}else {
			return false;
		}
	}
	
	/**
	 * Returns how many times the word in the parameter appears in the book.
	 * Each word in book is separated by a white space. 
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static int wordCount(String book, String word) {		
		Scanner bookReader = new Scanner(book);
		TrieMap<Integer> wordNumbers = new TrieMap<Integer>(26);
		
		while(bookReader.hasNext()) {
			String key = bookReader.next();
			Integer value = wordNumbers.search(key);
			if(value == null) {
				wordNumbers.insert(key, 1);
			}else {
				wordNumbers.insert(key, value.intValue()+1);
			}
		}
		
		Integer wordCount = wordNumbers.search(word);
		if(wordCount == null) {
			return 0;
		}else {
			return wordCount.intValue();
		}

		
	}
	
	/**
	 * Returns the array of unique words in the book given as parameter.
	 * Each word in book is separated by a white space.
	 *  
	 * Use Trie data structure to solve the problem
	 */
	public static String[] uniqueWords(String book) {
		ArrayList<String> unique = new ArrayList<String>();
		Scanner bookReader = new Scanner(book);
		TrieMap<Integer> wordNumbers = new TrieMap<Integer>(26);
		
		while(bookReader.hasNext()) {
			String key = bookReader.next();
			Integer value = wordNumbers.search(key);
			if(value == null) {
				wordNumbers.insert(key, 1);
				unique.add(key);
			}
		}
		
		return unique.toArray(new String[unique.size()]);
	}
	
	/**
	 * Recommends word completions based on the user history.
	 * 
	 * Among all the strings in the user history, the method takes 
	 * those that start with a given incomplete word S, 
	 * sort the words according to their frequencies (how many 
	 * times they are written), and recommend the 3 most frequently written ones.
	 * 
	 * @param userHistory 
	 * 			the words written previously by the user
	 * 
	 * @param incompleteWords 
	 * 			the list of strings to be autocompleted
	 * @return 
	 * 			a Sx3 array that contains the recommendations
	 * 			for each word to be autocompleted.
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static String[][] autoComplete(String[] userHistory, String[] incompleteWords){
		String[][] autoComp = new String[incompleteWords.length][3];
		TrieMap<Integer> historyMap = new TrieMap<Integer>(26);
		for(int i=0, n=userHistory.length; i<n; i++) {
			Integer number = historyMap.search(userHistory[i]);
			if(number == null ) {
				historyMap.insert(userHistory[i], 1);
			}else {
				historyMap.insert(userHistory[i], number.intValue()+1);
			}
		}
		
		for(int i=0, l=incompleteWords.length; i<l; i++) {
			String word = incompleteWords[i];
			//traverse to the node that has the key word
			Node<Integer> tmp = historyMap.root;
			for(int k=0, g=word.length(); k<g; k++) {
				int index = (int)(word.charAt(k) - 'a');
				tmp=tmp.children[index];
			}
			tmp.key = word;
			ArrayList<Node<Integer>> leafs = TrieMap.returnBfsNodes(tmp);
			Collections.sort(leafs);
			for(int c=0; c<3; c++) {
				if(leafs.size()  <=  c) {
					autoComp[i][c] = null;
				}else {
					autoComp[i][c] = leafs.get(c).key;
				}
			}
		}

		return autoComp;
	}
	


	@Override
	public void insert(String key, T value) {
		Node<T> tmp = root;
		for(int i=0, length=key.length(); i<length;i++) {
			int index = key.charAt(i) - 'a';
			if(tmp.children[index] != null) {
				tmp = tmp.children[index];
			}else {
				tmp.children[index] = new Node<T>(null);
				tmp = tmp.children[index];
				nodeNumber++;
			}
		}
		tmp.value=value;
	}

	@Override
	public boolean delete(String key) {
		Node<T> tmp = root;
		for(int i=0, length=key.length(); i<length; i++) {
			int index = key.charAt(i) - 'a';
			if(tmp.children[index] == null) {
				return false;
			}else {
				if(i != length -1) {
					tmp = tmp.children[index];
				}else {
					tmp.children[index].value = null;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public T search(String key) {
		Node<T> tmp = root;
		for(int i=0, n=key.length(); i<n;i++) {
			int index= key.charAt(i) - 'a';
			if(tmp.children[index] == null) {
				return null;
			}else {
				tmp = tmp.children[index];
			}
		}
		if(tmp.value == null) {
			return null;
		}else {
			return tmp.value;
		}
	}

	@Override
	public int nodeCount() {
		
		return nodeNumber;
	}

	@Override
	public ArrayList<T> returnBFSOrder() {
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		ArrayList<T> bfs = new ArrayList<T>();
		
		
		if(root==null) {
			return null;
		}
		
		queue.add(root);
		
		while(!queue.isEmpty()) {
			Node<T> tmp = queue.remove();
			if(tmp.value != null) {
				bfs.add(tmp.value);
			}
			for(int i=0;i<Node.alphabetSize;i++) {
				if(tmp.children[i] != null) {
					queue.add(tmp.children[i]);
				}
			}
		}
		
		return bfs;
	}
	
	//returns nodes of the triemap in the bfs-order as an arraylist
	public static ArrayList<Node<Integer>> returnBfsNodes(Node<Integer> myNode) {
		Queue<Node<Integer>> queue = new LinkedList<Node<Integer>>();
		ArrayList<Node<Integer>> bfs = new ArrayList<Node<Integer>>();
		if(myNode==null) {
			return null;
		}
		queue.add(myNode);
		while(!queue.isEmpty()) {
			Node<Integer> tmp = queue.remove();
			if(tmp.value != null) {
				bfs.add(tmp);
			}
			for(int i=0;i<Node.alphabetSize;i++) {
				if(tmp.children[i] != null) {
					tmp.children[i].key = tmp.key +(char)('a'+i);
					queue.add(tmp.children[i]);
				}
			}
		}
		return bfs;
	}
	
}
