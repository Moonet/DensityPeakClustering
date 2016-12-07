package com.company;

public class Main {
    // use iris data as a test case
    public static void main(String[] args) {
	    // write your code here
        readFile rd = new readFile();
        String filename = "/iris.data.txt";
        double[][] data = rd.read(filename);
        SimilarityDE sde = new SimilarityDE();
        double[][] adj = sde.getAdjMatrix(data);
        double derate = 0.05 * sde.getInterval();
        DPC dpc = new DPC(3,adj,derate);
        dpc.fit();

    }
}
