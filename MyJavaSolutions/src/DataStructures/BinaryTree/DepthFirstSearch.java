package DataStructures.BinaryTree;

import DataStructures.Tree.TreeNode;

public class DepthFirstSearch {
	
	public void inOrderTraversal(TreeNode<Integer> node){
		
	}
	
	public void preOrderTraversal(TreeNode<Integer> node){
		
	}
	
	public void postOrderTraversal(TreeNode<Integer> node){
		
	}
	
	public void inOrderTraversalRecursion(TreeNode<Integer> node){
		if(node == null){
			return;
		}
		inOrderTraversalRecursion(node.left);
		System.out.println(node.data);
		inOrderTraversalRecursion(node.right);
	}
	
	public void preOrderTraversalRecursion(TreeNode<Integer> node){
		if(node == null){
			return;
		}
		System.out.println(node.data);
		preOrderTraversalRecursion(node.left);
		preOrderTraversalRecursion(node.right);
	}
	
	public void postOrderTraversalRecursion(TreeNode<Integer> node){
		if(node == null){
			return;
		}
		postOrderTraversalRecursion(node.left);
		postOrderTraversalRecursion(node.right);
		System.out.println(node.data);
	}
	
	public void inOrderTraversalStack(TreeNode<Integer> node){
		
	}
	
	public void preOrderTraversalStack(TreeNode<Integer> node){
		
	}
	
	public void postOrderTraversalStack(TreeNode<Integer> node){
		
	}
	
	public void createBinaryTree(BinaryTree<Integer> tree){
		tree.root = new TreeNode<Integer>(1);
		tree.root.left = new TreeNode<Integer>(2);
		tree.root.right = new TreeNode<Integer>(3);
		tree.root.left.left = new TreeNode<Integer>(4);
		tree.root.left.right = new TreeNode<Integer>(5);
	}
	
	public static void main(String ab[]){
		BinaryTree<Integer> tree = new BinaryTree<Integer>();
		DepthFirstSearch dfs = new DepthFirstSearch();
		dfs.createBinaryTree(tree);
		System.out.println("Print InOrder Traversal");
		dfs.inOrderTraversalRecursion(tree.root);
		System.out.println("Print PreOrder Traversal");
		dfs.preOrderTraversalRecursion(tree.root);
		System.out.println("Print PostOrder Traversal");
		dfs.postOrderTraversalRecursion(tree.root);
	}

}