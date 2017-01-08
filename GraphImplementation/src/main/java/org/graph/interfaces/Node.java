package org.graph.interfaces;

import java.util.Set;

/**
 * Interface for nodes of directed graph
 */
public interface Node {
	public String name();
	public Set<Node> children();
}
