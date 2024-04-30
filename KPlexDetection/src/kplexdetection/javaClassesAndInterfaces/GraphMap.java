/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kplexdetection.javaClassesAndInterfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author haythem
 */
public class GraphMap implements Graph {

    private ConcurrentHashMap<String, List<String>> map;

    public GraphMap() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void addEdges(List<String> edges) {
        for (String edge : edges) {
            String[] vertices = edge.split(" ");
            if (vertices.length == 1) {
                map.put(vertices[0], new ArrayList<>());
            } else {
                addEdge(vertices[0], vertices[1]);
                addEdge(vertices[1], vertices[0]);
            }
        }
    }

    @Override
    public void addEdge(String src, String dst) {
        if (!map.containsKey(src)) {
            List<String> ls = new ArrayList<>();
            ls.add(dst);
            map.put(src, ls);
        } else {
            map.get(src).add(dst);
        }
    }

    @Override
    public Set<String> getVertices() {
        return map.keySet();
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        return map.get(vertex);
    }

    @Override
    public void addVertex(String vertex) {
        List<String> ls = new ArrayList<>();
        map.put(vertex, ls);
    }

    @Override
    public Graph getGraphInducedBy(Set<String> vertices) {
        try {
            Graph graph = new GraphMap();
            for (String vertex : vertices) {
                graph.addVertex(vertex);
            }
            Set<String> verticesSet = getVertices();
            for (String vertex : vertices) {
                if (verticesSet.contains(vertex)) {
                    List<String> neighbors = getNeighbors(vertex);
                    for (String neighbor : neighbors) {
                        if (vertices.contains(neighbor)) {
                            graph.addEdge(vertex, neighbor);
                        }
                    }
                }
            }
            return graph;
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            return new GraphMap();
        }
    }

    private void dfs(String vertex, Set<String> visited) {
        visited.add(vertex);
        for (String neighbor : map.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }

    @Override
    public boolean isConnected() {
        if (map.isEmpty()) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        String startVertex = map.keySet().iterator().next();

        dfs(startVertex, visited);

        return visited.size() == map.size();
    }

    @Override
    public boolean isKPlex(int k) {
        Set<String> vertices = getVertices();
        int n = vertices.size();
        for (String vertex : vertices) {
            if (map.get(vertex).size() < (n - k)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMaximalKPlex(Set<String> vertices, int k) {
        Graph g = getGraphInducedBy(vertices);
        if (!g.isKPlex(k)) {
            return false;
        }
        Set<String> ss = new HashSet<>(vertices);
        Set<String> verticesSet = getVertices();
        for (String vertex : verticesSet) {
            if (!vertices.contains(vertex)) {
                ss.add(vertex);
                g = getGraphInducedBy(ss);
                if (g.isKPlex(k)) {
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public int getMinimumDegree() {
        int minimum = Integer.MAX_VALUE;
        Set<String> vertices = getVertices();
        int n;
        for (String vertex : vertices) {
            n = map.get(vertex).size();
            if (minimum > n) {
                minimum = n;
            }
        }
        return minimum;
    }

    @Override
    public int getMaxK() {
        int n = getVertices().size();
        int minimum = getMinimumDegree();
        int k = n - minimum;
        return k;
    }

    @Override
    public void printGraph() {
        Set<String> vertices = getVertices();
        for (String vertex : vertices) {
            System.out.print(vertex + " : ");
            System.out.println(map.get(vertex));
        }
    }

}
