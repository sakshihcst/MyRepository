package DataStructures.BinarySearchTree;

import DataStructures.BinaryTree.DepthFirstSearch;
import DataStructures.Tree.TreeNode;

public class BinarySearchTree {
	
	public TreeNode<Integer> insertNode(TreeNode<Integer> node, int value){
		if(node == null){
			node = new TreeNode<Integer>(value);
			return node;
		}
		if(node.data>value){
			node.left = insertNode(node.left, value);
		}else if(node.data<value){	
			node.right = insertNode(node.right, value);
		}
		return node;
	}
	
	public void deleteNode(TreeNode<Integer> node){
		
	}
	
	public void searchNode(TreeNode<Integer> node){
		
	}
	
	public boolean validateBST(){
		return false;
	}
	
	public static void main(String ab[]){
		BinarySearchTree bst = new BinarySearchTree();
		TreeNode<Integer> node = null;
		node = bst.insertNode(node,4);
		node = bst.insertNode(node,6);
		node = bst.insertNode(node,2);
		node = bst.insertNode(node,12);
		node = bst.insertNode(node,20);
		node = bst.insertNode(node,26);
		node = bst.insertNode(node,32);
		node = bst.insertNode(node,28);
		node = bst.insertNode(node,18);
		node = bst.insertNode(node,5);
		node = bst.insertNode(node,73);
		node = bst.insertNode(node,9);
		DepthFirstSearch dfs = new DepthFirstSearch();
		System.out.println("Print Binary Search Tree Elements");
		dfs.inOrderTraversalRecursion(node);
	}

}
