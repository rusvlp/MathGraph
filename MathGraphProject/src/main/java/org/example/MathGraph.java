package org.example;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MathGraph {
    private List<List<Double>> matrix = new ArrayList<>();

    public MathGraph(){};

    public MathGraph(String path, FileType ft) throws Exception{
        loadGraphFromFile(path, ft);
    }

    public void loadGraphFromFile(String path, FileType ft) throws Exception{
        // Читаем файл
        FileReader fr = new FileReader(path);
        File f = new File(path);
        char[] chars = new char[(int)f.length()];
        fr.read(chars);
        String res = new String(chars);

        if (ft == FileType.TYPE_LIST){
            res = res.replaceAll("\b", "");
            // Инициализация матрицы смежности
            int size = Integer.parseInt(res.split(" ")[0]);
            System.out.println(size);
            matrix = new ArrayList<>(size);
            for (int i = 0; i<size; i++){
                matrix.add(new ArrayList<>());
                for(int j = 0; j<size; j++){
                    matrix.get(i).add(0d);
                }
            }

            // Заполнение
            for(int i = 1; i<res.split("\n").length; i++){
                int l = res.split("\n")[i].split(" ").length;
                Double[] tmp = Arrays.stream(res.split("\n")[i].split(" "))
                        .map(Double::parseDouble)
                        .toList()
                        .toArray(new Double[l]);
                matrix.get(tmp[0].intValue()-1).remove(tmp[1].intValue()-1);
                matrix.get(tmp[0].intValue()-1).add(tmp[1].intValue()-1, tmp[2]);
                matrix.get(tmp[1].intValue()-1).remove(tmp[0].intValue()-1);
                matrix.get(tmp[1].intValue()-1).add(tmp[0].intValue()-1, tmp[2]);
            }

        } else {
            for (int i = 1; i<res.split("\n").length; i++){
                matrix.add(new ArrayList<>());
                String tmp = res.split("\n")[i];
                for (int j = 0; j<tmp.split(" ").length; j++){
                    matrix.get(i-1).add(Double.parseDouble(tmp.split(" ")[j]));
                }
            }
        }

    }

    public void addVertex(List<Double> adjacencies){
        if (adjacencies.size() > matrix.size()){
            throw new IllegalArgumentException("Adjacencies size must be less or equal than matrix size");
        }
        matrix.add(new ArrayList<>(adjacencies));
        for (int i = 0; i<matrix.size(); i++){

            if (i > adjacencies.size()-1){
                matrix.get(matrix.size()-1).add(0d);
                matrix.get(i).add(0d);
                continue;
            }
            matrix.get(i).add(adjacencies.get(i));

        }
        if (matrix.get(matrix.size()-1).size() > matrix.size()){
            matrix.get(matrix.size()-1).remove(matrix.size());
        }
    }

    public void removeVertex(int index){
        if(index > matrix.size()){
            throw new IllegalArgumentException("Vertex is not exist");
        }
        index--;
        matrix.remove(index);
        for (List<Double> lst: matrix){
            lst.remove(index);
        }
    }

    public void removeEdge(int v1, int v2){
        v1--;
        v2--;
        matrix.get(v1).remove(v2);
        matrix.get(v1).add(v2, 0d);
        matrix.get(v2).remove(v1);
        matrix.get(v2).add(v1, 0d);
    }

    public int numberOfVertices(){
        return matrix.size();
    }

    public int numberOfEdges(){
        int count = 0;
        for (int i = 0; i<matrix.size(); i++){
            for (int j = i; j<matrix.size(); j++){
                if (matrix.get(i).get(j) > 0){
                    count++;
                }
            }
        }

        return count;
    }

    public boolean isAdjacent(int v1, int v2){
        return matrix.get(v1-1).get(v2-1) > 0;
    }

    public double getWeight(int v1, int v2){
        return matrix.get(v1-1).get(v2-1);
    }

    public void save(String path){
        
    }
    public List<List<Double>> getMatrix(){
        return new ArrayList<>(matrix);
    }

}
