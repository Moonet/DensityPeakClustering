package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class readFile {
    public double[][] read(String filename) {
        List<String[]> arr = new ArrayList<>();
        try {

            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;

            while ((str = in.readLine()) != null) {
                String[] ar=str.split(",");

                arr.add(ar);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        int M = arr.size();
        int N = arr.get(0).length - 1;
        double[][] data = new double[M][N];
        for(int i = 0; i < M; i++) {
            String[] str = arr.get(i);
            for(int j = 0; j < N; j++) {
                data[i][j] = Double.parseDouble(str[j]);
            }
        }

        return data;
    }

}
