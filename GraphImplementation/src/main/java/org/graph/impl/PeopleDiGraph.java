package org.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.graph.interfaces.Node;


/**
 * Implementation of directed graph for people with nodes having a name and a set of children.
 */
public class PeopleDiGraph {
	
	public int depth = -1;
	
	public int found = 0;
	// set of nodes
	private  HashSet<Node> nodes = new HashSet<Node>();
	
	// set of edges
	private  HashSet<Edge> edges = new HashSet<Edge>();
	
	// node map for creating a map with name as the key and children set as value
	private  HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	
	/** 
	 * @param graph
	 * @param nodeId
	 * Adds a node to the graph. Node is added to the HashMap nodeMap of the graph,node is added to HashSet nodes in graph 
	 */
	public void addNode(PeopleDiGraph graph,String nodeId){
		NodeImpl nodeToAdd = new NodeImpl(nodeId);
		graph.nodes.add(nodeToAdd);
		graph.nodeMap.put(nodeId, nodeToAdd);
	}
	
	/** 
	 * @param graph
	 * @param startNode
	 * @param endNode
	 * Adding connections between nodes
	 *  */
	public void addEdges(PeopleDiGraph graph,Node startNode,Node endNode ){
		Edge edge = new Edge(startNode, endNode);
		graph.edges.add(edge);
		startNode.children().add(endNode);
		nodes.add(startNode);
	}
	
	public  Node getNode(String id){
		if (nodeMap.containsKey(id))
			return nodeMap.get(id);
		return null;
	}
	
	/* To list out all the nodes */
	public HashSet<Node> getAllNodes(){
		return nodes;
	}
	
	/* For listing out all the connections between nodes */
	public HashSet<Edge> getEdges(){
		return edges;
	}
	
	/* A function that traverses the graph and returns a map that consists of all people in the graph as keys that map to a list of all associated connections up
	to a certain depth For example, if the depth is given as 2, the list of connections will contain all 1st and 2nd level connections for each person */
	
	public Map<Node,Set<Node>> findConnections(Node node,int depth){
		Set<Node> connection = new HashSet<Node>();
		Set<Node> tempConnection = new HashSet<Node>();
		Set<Node> tempConnection2 = new HashSet<Node>();
		Map<Node,Set<Node>> nodeConnections = new HashMap<Node,Set<Node>>();
		if(null != node){
			for (int i=0;i<depth;i++){
				if(connection.isEmpty()){
					for (Node child : node.children()){
						connection.add(child);
					}
				nodeConnections.put(node, connection);
				}else{
					for(Node childConnection : connection){
							tempConnection = new HashSet<Node>();
						for(Node child: childConnection.children()){
							tempConnection.add(child);
						}
						tempConnection2.addAll(tempConnection);
						nodeConnections.put(childConnection, tempConnection);
					}		
					connection.addAll(tempConnection2);
				}
			}
			nodeConnections.put(node, connection);
		}
		return nodeConnections;	
	}
	
	/* Method to traverses the graph and return whether or not two people are connected and what level the connection is within the graph. For example if we have a simple connection path in the
	graph : John -> Suzy -> Bill -> Beth then invoking connected(John, Beth) would result in 3, since Beth is a 3rd level connection with John. */
	public int connected(Node startNode, Node endNode){
		
		depth = -1;
		
		/* connection list to store higher level start nodes when a match is not found at a lower level.*/
		List<Node> connection = new ArrayList<Node>();
		
		/* temporary list to hold the child nodes when iteration is happening on the lower level */
		List<Node> tempConnection = new ArrayList<Node>();
		
		if(startNode != null && endNode != null){
			connection.add(startNode);
			while(found != 1){
				for(Node childConnection : connection){
						if(childConnection.equals(endNode)){
							if(depth != -1){
								found = 1;			
								break;
							}
						}
						for(Node child : childConnection.children()){
							tempConnection.add(child);		
						}
				}
				depth++;
				connection = tempConnection;
				tempConnection = new ArrayList<Node>();
			}
		}
		if(found == 0){
			return -1;
		}else{
			found = 0;
			return this.depth;
		}
	}

}