import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import java.util.*;
/**
 * WordNet3
 */
public class WordNet3 {
    Digraph dg;
    Hashtable<String, ArrayList<Integer>> hts = new Hashtable<>();
    // Digraph dg;
    String[] nouns;
    Integer[] id;
    
    private void parseSynsets(String filename) {
        File file = new File("D:\\MSIT\\ADS - 2\\ADS2_2019501051\\wordnet\\"+ filename + ".txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] temp;
                temp = line.split(",");
                nouns = temp[1].split(" ");
                ArrayList al;
                for (String string : nouns) {
                    if ( hts.containsKey(string) ) {
                        al = hts.get(string);
                    } else {
                        al = new ArrayList<>();
                        hts.put(string, al);
                    }
                    al.add(temp[0]);
                }
            }
        }
        catch (IOException e) {
            System.out.println("file not found");
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound");
        }
    }

    private void parseHypernyms(String filename) {

        Hashtable<String, ArrayList<Integer>> hth = new Hashtable<>();
        File file = new File("D:\\MSIT\\ADS - 2\\ADS2_2019501051\\wordnet\\"+ filename + ".txt");
        ArrayList<Integer> al;
        int[] id;
        String[] hypernyms;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] temp;
                temp = line.split(",",2);
                // System.out.println(Arrays.toString(temp));
                if (temp.length != 1) {
                    hypernyms = temp[1].split(",");
                    // System.out.println(Arrays.toString(hypernyms));
                    int in = Integer.parseInt(temp[0]);
                    for (String i : hypernyms) {
                        if (hth.containsKey(in)) {
                            al = hth.get(in);
                        } else {
                            al = new ArrayList<>();
                            hth.put(i,al);
                        }
                        al.add(Integer.parseInt(i));
                        dg.addEdge(Integer.parseInt(temp[0]),Integer.parseInt(i));
                    } 
                }
            } 
        }catch (IOException e) {
            System.out.println("file not found");
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound");
        }
    }
    

    public static void main(String[] args) {
        Digraph dig;
        In in = new In(args[0]);
        WordNet3 wn = new WordNet3();
        // wn.parseSynsets(args[0]);
        // wn.dg = new Digraph(wn.hts.size());
        // wn.parseHypernyms(args[1]);
        // In in = new In(args[0]);
        Digraph dg = new Digraph(in);
        SAP sap = new SAP(dg);
        // System.out.println(wn.dg.V());
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            sap.bfs(dg, v, w);
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            System.out.println("length = " + length + " , " + "ancestor = " + ancestor);
        }
    }
}