package DataStructures.BinaryTree;

import DataStructures.Tree.TreeNode;

/*A tree normally refers to a binary tree. Each node contains a left node and right node
Unlike Arrays, Linked Lists, Stack and queues, which are linear data structures, trees are hierarchical data structures.
The topmost node is called root of the tree. It has children, parent and leaves. Trees can be used where hierarchical structures 
eg file structure.*/

public class BinaryTree<T> {
	
	public TreeNode<T> root;
	
	public BinaryTree() {
		root = null;
	}
	
	public BinaryTree(T value) {
		root = new TreeNode<T>(value);
	}

}
