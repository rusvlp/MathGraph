package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("MathGraph example");
        String path = "C:\\Users\\Vlad\\Desktop\\MathGraphJava\\example.txt";
        MathGraph mg = new MathGraph(path, FileType.TYPE_MATRIX);
        System.out.println(mg.getMatrix());
        mg.addVertex(new ArrayList<>(){
            {
                this.add(1d);
             //   this.add(0d);
                this.add(2d);
                this.add(22d);
            }
        });

        System.out.println(mg.getMatrix());
        mg.removeVertex(1);
        System.out.println(mg.getMatrix());
        mg.removeEdge(3, 1);
        System.out.println(mg.getMatrix());
        System.out.println(mg.numberOfEdges());
        System.out.println(mg.isAdjacent(1, 2));
        System.out.println(mg.getWeight(1, 2));
    }

}