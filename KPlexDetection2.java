/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kplexdetection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kplexdetection.javaClassesAndInterfaces.Graph;
import kplexdetection.javaClassesAndInterfaces.GraphMap;

/**
 *
 * @author haythem
 */
public class KPlexDetection2 {
    
    private static void detectKPlexRecursive2(Graph g, int k, List<String> P, Set<String> C, List<Set<String>> listOfMaximals){
        Set<String> cc = new HashSet(P);
        if(g.isMaximalKPlex(cc, k)){
            listOfMaximals.add(cc);
        }else{
            if(!C.isEmpty()){
                Graph graph;
                for(String c: C){
                    cc.add(c);
                    graph = g.getGraphInducedBy(cc);
                    if(graph.isKPlex(k)){
                        detectKPlexRecursive2(g, k, new ArrayList(cc), C, listOfMaximals);
                        cc = new HashSet<>(P);
                    }
                }
            }
        }
    }
    
    private static List<Set<String>> detectKPlex2(Graph g, int k){
        List<Set<String>> listOfMaximals = new ArrayList<>();
        Set<String> candidate_set = g.getVertices();
        if(g.isKPlex(k)){
            listOfMaximals.add(candidate_set);
        }else{
            List<String> neighbors;
            for(String vertex: candidate_set){
                neighbors = g.getNeighbors(vertex);
                neighbors.add(vertex);
                detectKPlexRecursive2(g, k, neighbors, candidate_set, listOfMaximals);
            }
        }
        return listOfMaximals;
    }
    public static void main(String[] args){
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/kplexdetection/datas/example2.txt"));
            Graph g = new GraphMap();
            g.addEdges(lines);
            int kmax = g.getMaxK();
            List<Set<String>> ll;
            for (int k = kmax; k >= 2; k--) {
                long startTime = System.nanoTime();  // Start timing here
                ll = detectKPlex2(g, k);
                long endTime = System.nanoTime();  // End timing here
                double duration = (double) (endTime - startTime) / 1_000_000_000;  // Compute the duration in seconds
                //System.out.println("List of detected " + k + "-plex :" + listOfMaximals);
                System.out.println("Execution time for k = " + k + " : " + duration + " seconds");
                System.out.println("Number of " + k + "-plex detected = " + ll.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
