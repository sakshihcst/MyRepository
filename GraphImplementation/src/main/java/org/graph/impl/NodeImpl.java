package org.graph.impl;

import java.util.HashSet;
import java.util.Set;

import org.graph.interfaces.Node;

public class NodeImpl implements Node{
	 
	public String name;
	public Set<Node> children;
	
	 public NodeImpl(String nodeName){
		 name = nodeName;
		 children = new HashSet<Node>();
	 }	
	 

	 /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Overridden equals method for comparing nodes by names
	 */
	 @Override
	 public boolean equals(Object obj) {
		NodeImpl e = (NodeImpl)obj;
	    return e.name.equals(this.name);
	 }
	 
	 @Override
	 public int hashCode() {
	     int hash = 3;
	     hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
	     return hash;
	 }
	 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: "+ name);
		/*s.append(",Children:");
		for(NodeImpl i : children)
			s.append(i.name+"-");*/
		return sb.toString();
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Set<Node> children() {
		// TODO Auto-generated method stub
		return this.children;
	}
}
