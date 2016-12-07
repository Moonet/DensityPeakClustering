package com.company;

import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * @author Xin
 */
public class DPC {
    private int k;
    private double[][] adj;
    private double dcrate;
    private double[] rho;
    private int[] sortedIndices;
    private double[] delta;
    private int[] nneigh;
    private double[] gamma;
    private int N;
    private int[] dpcidx;
    private ArrayList<ArrayList<Integer>> result;
    
    
    public DPC(int k, double[][] adj, double derate){
        this.adj = adj;
        this.k = k;
        this.dcrate = derate;
        //initilize rho
        this.rho = new double[adj.length];
    }
    
    private void calcDensities(){
        N = adj.length;
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                rho[i] += Math.exp(-Math.pow((1-adj[i][j]),2)/(2*dcrate*dcrate));
                
            }
            System.out.print(i +" : "+ rho[i]+" ");
        }
        
        sortedIndices = IntStream.range(0, rho.length)
            .boxed().sorted((i, j) -> (-Double.compare(rho[i], rho[j])))
            .mapToInt(ele -> ele).toArray();
        
        for(int i = 0; i < N; i++){
            
            System.out.print(sortedIndices[i]+" ");
        }
        
        System.out.println();
    }
    
    public ArrayList<ArrayList<Integer>> fit(){
        calcDensities();
        
//        List<double[]> ml = getMaxMin(adj);
//        double[] maxAdj = ml.get(0);
        
        delta = new double[N];
        delta[sortedIndices[0]] = 1;
        // no normorize 
//        delta[sortedIndices[0]] = maxAdj[sortedIndices[0]];
        nneigh = new int[N];
        nneigh[sortedIndices[0]] = sortedIndices[0];
        
        for(int i = 1; i < N; i++){
            // no normorize
//            double stemp = maxAdj[sortedIndices[0]];
            double stemp = 0;
            for(int j = 0; j < i; j++){
                int indexx = sortedIndices[i];
                int indexy = sortedIndices[j];
                if(adj[indexx][indexy] > stemp){
                    stemp = adj[indexx][indexy];
                    
                    delta[indexx] = 1- stemp;
                    nneigh[indexx] = indexy;
                }
            }
        }
        
        gamma = new double[N];
        for(int i = 0; i < N; i++){
            gamma[i] = rho[i]*delta[i];
            System.out.print(i +": "+ gamma[i]+" ");
        }
        
        int[] GIndices = IntStream.range(0, gamma.length)
            .boxed().sorted((i, j) -> (-Double.compare(gamma[i], gamma[j])))
            .mapToInt(ele -> ele).toArray();
        for(int i = 0; i < N; i++){
            
            System.out.print(GIndices[i]+" ");
        }
        
        dpcidx = new int[N];
        for(int i = 0; i < N; i++){
            dpcidx[i] = -1;
        }
        for(int i = 0; i < k; i++){
            dpcidx[GIndices[i]] = GIndices[i];
        }
        
        for(int i = 0; i < N; i++){
            int order = sortedIndices[i];
            if(dpcidx[order] == -1){
                dpcidx[order] = dpcidx[nneigh[order]];
                
            }
        }
        System.out.println();
        for(int i = 0; i < N; i++){
            System.out.print(i +": " + dpcidx[i] +" ");
        }
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();

        for(int i = 0; i < N; i++){
            if(!map.containsKey(dpcidx[i])){
                ArrayList<Integer> index = new ArrayList<>();
                index.add(i);
                map.put(dpcidx[i], index);
            } else {
                map.get(dpcidx[i]).add(i);
            }
        }
        result = new ArrayList<>();
        for(Integer l : map.keySet()){
            result.add(map.get(l));
        }
        return result;
    }

}
