package DataStructures.BinaryTree;

import DataStructures.Tree.TreeNode;

public class BreadthFirstSearch {
	
	public void createBinaryTree(BinaryTree<Integer> tree){
		tree.root = new TreeNode<Integer>(1);
		tree.root.left = new TreeNode<Integer>(2);
		tree.root.right = new TreeNode<Integer>(3);
		tree.root.left.left = new TreeNode<Integer>(4);
		tree.root.left.right = new TreeNode<Integer>(5);
	}
	
	int height(TreeNode<Integer> node){
		if(node == null){
			return 0;
		} else {
			int lheight = height(node.left);
			int rheight = height(node.right);
			if(lheight>rheight){
				return lheight+1;
			}else{
				return rheight+1;
			}
		}
		
	}
	
	public void levelOrderTraversalRecursion(TreeNode<Integer> node){
		for(int i=1;i<=height(node);i++){
			printLevelNodes(node,i);
		}
	}
	
	public void printLevelNodes(TreeNode<Integer> node, int level){
		if(node == null){
			return;
		} else {
			if(level == 1) {
				System.out.println(node.data);
			} else {
				printLevelNodes(node.left,level-1);
				printLevelNodes(node.right,level-1);
			}
		}
		
	}
	
	public void levelOrderTraversalQueue(){
		
	}
	
	public static void main(String ab[]){
		BinaryTree<Integer> tree = new BinaryTree<Integer>();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.createBinaryTree(tree);
		System.out.println("Tree height is : " + bfs.height(tree.root));
		System.out.println("Level Order Traversal");
		bfs.levelOrderTraversalRecursion(tree.root);
	}

}
