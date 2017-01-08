package DataStructures.Tree;

public class TreeNode<T> {
	public T data;
	public TreeNode<T> left;
	public TreeNode<T> right;
	
	public TreeNode(T value) {
		data = value;
		left = null;
		right = null;
	}
	
}
