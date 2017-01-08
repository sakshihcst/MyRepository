package org.graph.impl;

import org.graph.interfaces.Node;

public class Edge{
	
	Node startNode;
	Node endNode;
	
	public Edge(Node startNode, Node endNode){
		this.startNode = startNode;
		this.endNode = endNode;
	}
		
	@Override
    public boolean equals(Object obj) {
		Edge e = (Edge)obj;
      return e.endNode == endNode && e.startNode == startNode;
    }
	
	public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(startNode.name());
        s.append("->");
        s.append(endNode.name());	        
        return s.toString();
    }
}