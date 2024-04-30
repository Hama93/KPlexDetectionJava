/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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
public class KPlexDetection {
    private static void detectKPlexRecursive(Graph g, int k, Set<String> candidate_set, List<Set<String>> listOfMaximals){
        if(g.isMaximalKPlex(candidate_set, k) && (!listOfMaximals.contains(candidate_set))){
            listOfMaximals.add(candidate_set);
        }else{
            Graph graph = g.getGraphInducedBy(candidate_set);
            if(graph.isConnected() && (!graph.isKPlex(k))){
                Set<String> next_set;
                for(String vertex: candidate_set){
                    next_set = new HashSet<>(candidate_set);
                    next_set.remove(vertex);
                    detectKPlexRecursive(g, k, next_set, listOfMaximals);
                }
            }
        }
    }
    private static List<Set<String>> detectKPlex(Graph g, int k){
        Set<String> vertices = g.getVertices();
        List<Set<String>> listOfMaximals = new ArrayList<>();
        detectKPlexRecursive(g, k, vertices, listOfMaximals);
        return listOfMaximals;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/kplexdetection/datas/example2.txt"));
            Graph g = new GraphMap();
            g.addEdges(lines);
            int kmax = g.getMaxK();
            List<Set<String>> ll;
            for(int k = kmax; k >= 2; k--){
                ll = detectKPlex(g, k);
                /*System.out.println("List for k = " + k + " : ");
                System.out.println(ll);*/
                System.out.println("Number of " + k + "-plex detected = " + ll.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}