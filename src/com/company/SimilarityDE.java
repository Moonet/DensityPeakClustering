package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Xin
 */
public class SimilarityDE {

    private double interval;

    public SimilarityDE() {

    }

    public double[][] getAdjMatrix(double[][] data) {
        //nomarlize
        List<double[]> l = normalize(data);
        double[] maxA = l.get(0);
        System.out.println(maxA[0] + " " + maxA[1] + " " + maxA[3]);

        double[] minA = l.get(1);
        System.out.println(minA[0] + " " + minA[1] + " " + minA[3]);
        int caseNum = data.length;
        int attrNum = data[0].length;


        double[][] normData = new double[caseNum][attrNum];
        for (int i = 0; i < attrNum; i++) {
            double interval = maxA[i] - minA[i];
            for (int j = 0; j < caseNum; j++) {
                normData[j][i] = (data[j][i] - minA[i]) / interval;
//                System.out.print( data[j][i] + " ");
//                System.out.print( normData[j][i] + " ");
            }
            System.out.println();
        }

        double[][] adj = new double[caseNum][caseNum];
        for (int i = 0; i < caseNum; i++) {
            for (int j = i + 1; j < caseNum; j++) {
                adj[i][j] = EDistance(normData[j], normData[i]);
                adj[j][i] = adj[i][j];
            }
            adj[i][i] = 0;
        }
        List<double[]> ml = normalize(adj);
        double[] minAdj = ml.get(1);
        double maxAdjj = 0, minAdjj = 0;
        for (int i = 0; i < minAdj.length; i++) {
//            maxAdjj = Math.max(maxAdj[i], maxAdjj);
            minAdjj = Math.min(minAdj[i], minAdjj);
        }

        interval = maxAdjj - minAdjj;

        for (int i = 0; i < caseNum; i++) {
            for (int j = 0; j < caseNum; j++) {
                adj[i][j] = (adj[i][j] - minAdjj) / interval;

                System.out.print(adj[i][j] + " ");
            }
            System.out.println();
        }


        return adj;
    }

    public List<double[]> normalize(double[][] data) {
        List<double[]> list = new ArrayList<>();
        double[] minA = new double[data[0].length];
        double[] maxA = new double[data[0].length];
        for (int i = 0; i < data[0].length; i++) {
            double max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
            for (int j = 0; j < data.length; j++) {
                max = Math.max(data[j][i], max);
                //System.out.println(data[j][i] + ": " + max);
                min = Math.min(data[j][i], min);
            }
            maxA[i] = max;
            minA[i] = min;
        }
        list.add(maxA);
        list.add(minA);
        return list;
    }

    private double EDistance(double[] p1, double[] p2) {
        double distance = 0;
        for (int i = 0; i < p1.length; i++) {
            distance += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }
        return -Math.sqrt(distance);
    }

    public double getInterval() {

        return interval;
    }
}
