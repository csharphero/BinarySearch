
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @author Bisheswor Devkota 
 * @param <Key> Comparable Key
 * @param <Value> Value 
 * @since 3/15/2016
 * @version 1.0
 */
public class BinarySearchTreeST<Key extends Comparable, Value>
implements OrderedSymbolTable<Key, Value>
{
	//root of the tree
	private Node root;
	//to Keep track of the size
	private int size;

	/**
	 * Node representation in a tree
	 *will keep track of key,value,left,right nodes
	 */
	private class Node{
		private Key key;
		private Value value;
		private Node leftChild;
		private Node rightChild;
		public Node(Key key, Value value)
		{
			this.key = key;
			this.value = value;
			this.leftChild= null;
			this.rightChild = null;	
		}
	}

	/**
	 * Initialize the size to 0 and root to null
	 * 
	 */
	public BinarySearchTreeST() {
		size=0;
		root=null;

	}
	/**
	 * Gets the number of elements currently in the symbol table.
	 * @return size
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Determines if there are no elements in the  symbol table.
	 * @return true if no elements, false otherwise
	 */
	public boolean isEmpty()
	{
		return root==null;
	}
	/**
	 * Inserts the value into the table using specified key.  Overwrites
	 * any previous value with specified value.
	 * @param key
	 * @param value
	 * @throws NullPointerException if the key or value is null
	 */
	public void put( Key key, Value value )
	{
		if(key==null || value==null){
			throw new NullPointerException("The item cannot be null");
		}
		//If root is null add root
		if(root == null)
		{
			root  = new Node(key, value);
			size++;
		}
		//if less than comparing node  add to left 
		//if bigger than comparing node add to the right
		else
		{
			Node currentNode = root;

			while(currentNode!= null)
			{       	        		
				if( currentNode.key.compareTo(key)  <  0)
				{ 		
					if(currentNode.rightChild == null)
					{
						currentNode.rightChild = new Node(key, value);
						size++;
						break;
					}
					else
						currentNode = currentNode.rightChild;

				}
				else  if( currentNode.key.compareTo(key) > 0)
				{
					if(currentNode.leftChild == null)
					{
						currentNode.leftChild = new Node(key, value);
						size++;
						break;
					}
					else
						currentNode = currentNode.leftChild;
				}
				else
				{
					currentNode.value = value;
					break;
				}

			}
		}
	}
	/**
	 * Finds Value for the given Key.
	 * @param key
	 * @return value that key maps to or null if not found
	 * @throws NullPointerException if the key is null
	 */
	public Value get( Key key )
	{
		if(key==null){
			throw new NullPointerException("The item cannot be null");
		}

//start from the root
		Node start = root;

		while (start != null) {
			// if less go left
			// if more go right
			int cmp = key.compareTo(start.key);
			if (cmp < 0) {
				// go left
				if (start.leftChild == null) {
					return null;
				}
				start = start.leftChild;
			} else if (cmp > 0) {
				// go right
				if (start.rightChild == null) {
					return null;
				}
				start = start.rightChild;
			} else {
				return start.value;
			}
		}
		return null;

	}

	/**
	 * Iterable that enumerates (in sorted order) each key in the table.
	 * @return iterator
	 */
	public Iterable<Key> keys()
	{
		DynamicArray<Key> list = new DynamicArray<Key>();
		keys( this.root, list );
		return list;
	}
	/**Recursive function to go right and left on the tree and add items to the list
	 * @param root
	 * @param list
	 */
	private void keys(Node root, DynamicArray<Key> list) { 

		if(root == null) 
			return;
		keys(root.leftChild, list);
		list.add(root.key);
		keys(root.rightChild, list);
	} 
	/**
	 * Finds and returns minimum key
	 * @return smallest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key min() throws NoSuchElementException
	{
		if(isEmpty()){
			throw new NoSuchElementException("Symbol table is  Empty");
		}
		//start at the root
		Node start=root;
		while(start !=null)
		{
			if(start.leftChild == null)
				return start.key;
			start= start.leftChild;
		}
		return null;

	}

	/**
	 * Finds and returns maximum key
	 * @return largest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key max() throws NoSuchElementException
	{
		if(isEmpty()){
			throw new NoSuchElementException("Symbol table is  Empty");
		}
		Node start=root;
		while(start !=null)
		{
			if(start.rightChild == null)
				return start.key;
			start= start.rightChild;
		}
		return null;
	}
	/**
	 * Removes the minimum key from the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMax( ) throws NoSuchElementException
	{
		if(isEmpty()){
			throw new NoSuchElementException("Symbol table is  Empty");
		}
		root = deleteMax(root);
		size--;

	}
	/**Recursive method calls to remove max item from the tree
	 * @param x
	 * @return
	 */
	private Node deleteMax(Node x) {
		if (x.rightChild == null){
			return x.leftChild;
		}
		x.rightChild = deleteMax(x.rightChild);
		return x;
	}
	/**
	 * Removes the maximum key from the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin( ) throws NoSuchElementException
	{
		if(isEmpty()){
			throw new NoSuchElementException("Symbol table is  Empty");
		}
		root = deleteMin(root);
		size--;

	}
	/**Recursive method calls to delete min
	 * @param x
	 * @return Node 
	 */
	private Node deleteMin(Node x) {
		if (x.leftChild == null){
			return x.rightChild;
		}
		x.leftChild = deleteMin(x.leftChild);
		return x;
	}


	//--------------------- DO NOT MODIFY BELOW THIS -----------------------//

	@Override
	public String toString()
	{
		// Uses the iterator to build String
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		buf.append("[");
		for (Key key : this.keys())
		{
			Value item = this.get(key);
			if( first ) first = false;
			else buf.append( ", " );
			buf.append( key );
			buf.append( "->" );
			buf.append( item.toString() );
		}
		buf.append("]");
		return buf.toString();
	}

	/**
	 * Unit tests the ST data type.
	 * @param args 
	 */
	public static void main(String[] args)
	{
		Stdio.open( args[0] );
		BinarySearchTreeST<Integer,String> st = new BinarySearchTreeST<Integer,String>();
		while( Stdio.hasNext() )
		{
			String method = Stdio.readString();
			if( method.equalsIgnoreCase("insert") )
			{
				int key    = Stdio.readInt();
				String val = Stdio.readString();
				st.put( key, val );
				Stdio.println( "insert="+st.toString() );
			}
			else if( method.equalsIgnoreCase("deleteMin") )
			{
				st.deleteMin();
				Stdio.println( "deleteMin" );
			}
			else if( method.equalsIgnoreCase("deleteMax") )
			{
				st.deleteMax();
				Stdio.println( "deleteMax" );
			}
			else if( method.equalsIgnoreCase("size") )
			{
				Stdio.println( "size="+st.size() );
			}
			else if( method.equalsIgnoreCase("min") )
			{
				Stdio.println( "min="+st.min() );
			}
			else if( method.equalsIgnoreCase("max") )
			{
				Stdio.println( "max="+st.max() );
			}
			else if( method.equalsIgnoreCase("isEmpty") )
			{
				Stdio.println( "isEmpty?="+st.isEmpty() );
			}
		}
		Stdio.println( "Final symbol table=" +st.toString() );
		Stdio.close();
	}
}

