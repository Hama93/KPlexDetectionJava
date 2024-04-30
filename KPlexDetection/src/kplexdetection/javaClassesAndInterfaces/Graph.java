/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package kplexdetection.javaClassesAndInterfaces;

import java.util.List;
import java.util.Set;

/**
 *
 * @author haythem
 */
public interface Graph {
    public void addEdges(List<String> edges);
    public void addEdge(String src, String dst);
    public Set<String> getVertices();
    public List<String> getNeighbors(String vertex);
    public void addVertex(String vertex);
    public Graph getGraphInducedBy(Set<String> vertices);
    public boolean isConnected();
    public boolean isKPlex(int k);
    public boolean isMaximalKPlex(Set<String> vertices, int k);
    public int getMinimumDegree();
    public int getMaxK();
    public void printGraph();
}
