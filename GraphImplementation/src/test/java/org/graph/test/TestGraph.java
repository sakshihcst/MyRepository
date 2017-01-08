package org.graph.test;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.graph.impl.PeopleDiGraph;
import org.graph.interfaces.Node;
import org.junit.Test;
import org.junit.Before;

/* Test class to implement people's digraph with atleast 3 connections with other children. This has been implemented using adjacency list. */ 
public class TestGraph {
	
	PeopleDiGraph graph = new PeopleDiGraph();
	Map<Node,Set<Node>> testConnections = new HashMap<Node,Set<Node>>();
	
	@Before
	public void addNodesEdges(){
		
		/* Adding list of people to the graph */
    	graph.addNode(graph, "Person1");
    	graph.addNode(graph, "Person2");
    	graph.addNode(graph, "Person3");
    	graph.addNode(graph, "Person4");
    	graph.addNode(graph, "Person5");
    	graph.addNode(graph, "Person6");
    	graph.addNode(graph, "Person7");
    	graph.addNode(graph, "Person8");
    	graph.addNode(graph, "Person9");
    	graph.addNode(graph, "Person10");
    	
    	/* Adding connections between nodes ensuring each node has at least 3 connection */
    	graph.addEdges(graph, graph.getNode("Person1"), graph.getNode("Person2"));
    	graph.addEdges(graph, graph.getNode("Person1"), graph.getNode("Person3"));
    	graph.addEdges(graph, graph.getNode("Person1"), graph.getNode("Person4"));
    	
    	graph.addEdges(graph, graph.getNode("Person2"), graph.getNode("Person3"));
    	graph.addEdges(graph, graph.getNode("Person2"), graph.getNode("Person4"));
    	graph.addEdges(graph, graph.getNode("Person2"), graph.getNode("Person5"));
    	
    	graph.addEdges(graph, graph.getNode("Person3"), graph.getNode("Person4"));
    	graph.addEdges(graph, graph.getNode("Person3"), graph.getNode("Person5"));
    	graph.addEdges(graph, graph.getNode("Person3"), graph.getNode("Person6"));
    
    	graph.addEdges(graph, graph.getNode("Person4"), graph.getNode("Person5"));
    	graph.addEdges(graph, graph.getNode("Person4"), graph.getNode("Person6"));
    	graph.addEdges(graph, graph.getNode("Person4"), graph.getNode("Person7"));
    	
    	graph.addEdges(graph, graph.getNode("Person5"), graph.getNode("Person6"));
    	graph.addEdges(graph, graph.getNode("Person5"), graph.getNode("Person7"));
    	graph.addEdges(graph, graph.getNode("Person5"), graph.getNode("Person8"));
    	
    	graph.addEdges(graph, graph.getNode("Person6"), graph.getNode("Person7"));
    	graph.addEdges(graph, graph.getNode("Person6"), graph.getNode("Person8"));
    	graph.addEdges(graph, graph.getNode("Person6"), graph.getNode("Person9"));
    	
    	graph.addEdges(graph, graph.getNode("Person7"), graph.getNode("Person8"));
    	graph.addEdges(graph, graph.getNode("Person7"), graph.getNode("Person9"));
    	graph.addEdges(graph, graph.getNode("Person7"), graph.getNode("Person10"));
    	
    	graph.addEdges(graph, graph.getNode("Person8"), graph.getNode("Person9"));
    	graph.addEdges(graph, graph.getNode("Person8"), graph.getNode("Person10"));
    	graph.addEdges(graph, graph.getNode("Person8"), graph.getNode("Person1"));
    	
    	graph.addEdges(graph, graph.getNode("Person9"), graph.getNode("Person10"));
    	graph.addEdges(graph, graph.getNode("Person9"), graph.getNode("Person1"));
    	graph.addEdges(graph, graph.getNode("Person9"), graph.getNode("Person2"));
    	
    	graph.addEdges(graph, graph.getNode("Person10"), graph.getNode("Person1"));
    	graph.addEdges(graph, graph.getNode("Person10"), graph.getNode("Person2"));
    	graph.addEdges(graph, graph.getNode("Person10"), graph.getNode("Person3"));		
	}
	
	@Test
    public void testPeopleCount() {
		assertTrue("People count is greater than 10",graph.getAllNodes().size()>=10);
		System.out.println("Number of people in the graph : "+ graph.getAllNodes().size()+"\n");
	}
	
	@Test
    public void testPeopleConnectionsCount() {
		HashSet<Node> allNodes = graph.getAllNodes();
		System.out.println("List of connections for each node");
		for(Node node : allNodes ){
			assertTrue("Each person's connection count is greater than 3",node.children().size()>=3);
			System.out.println(node.name()+ " : "+ node.children() + " - Count of Children : " + node.children().size());
		}
		System.out.print("\n");
	}
	
	@Test
    public void testCyclicGraph() {
		assertTrue("Graph is cyclic",graph.getAllNodes().size()> 1);
		System.out.println("Connection depth between two same nodes : " + graph.connected(graph.getNode("Person1"), graph.getNode("Person1"))+"\n");
	}
	
	/* Method call to print the returns a map that consists of all people in the graph as keys that map to a list of all associated connections up to a certain depth.*/
	@Test
	public void testConnectionMap(){
    	testConnections = graph.findConnections(graph.getNode("Person1"),2);
    	System.out.println("List of associated connections for each node upto a certain depth");
    	for (Map.Entry<Node,Set<Node>> entry : testConnections.entrySet()) {
    		System.out.println("People node : "+ entry.getKey()+ " has following associated connections : " + entry.getValue());
    	}
    	System.out.print("\n");
	}
	
	/* Method call to print whether or not two people are connected and what level (or depth) the connection is within the graph. */
	@Test
    public void testConnectionDepth() { 	
    	System.out.println("Connection depth between two connected nodes : " + graph.connected(graph.getNode("Person1"), graph.getNode("Person8"))+"\n");
    }
	
	@Test
    public void testAreNodesNotConnected() { 
		assertEquals(-1,graph.connected(graph.getNode("Person1"), graph.getNode("Person15")));
    	System.out.println("Connection depth between two unconnected nodes : " + graph.connected(graph.getNode("Person1"), graph.getNode("Person15"))+"\n");
    }
}
