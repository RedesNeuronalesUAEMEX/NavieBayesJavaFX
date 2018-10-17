package ReaderAndBayes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

/**
 *
 * @author cachubis
 */
public class Bayesiano {
    private final double[][] dataset;
    private double[] unknownData;
    private double[] classes;
    public Bayesiano(double[][] dataset){
        this.dataset=dataset;
    }
    public void setUnknownData(double... unknownData){
        this.unknownData=unknownData;
    }
    
    private double probClase(double ci){
        int nInstancesCi =0;
        int nInstances=dataset.length;
        for(double[] row :dataset){
            if(row[row.length-1]==ci){
                nInstancesCi++;
            }
        }
        double p = ((double)nInstancesCi)/((double)nInstances);
        return p; 
    }
    private double probVarGivenClass(double vi,double ci, int indexVi){
        int nInstancesCi=0;
        int nInstancesViCi=0;
        for(double[] row:dataset){
            if(row[row.length-1]==ci && row[indexVi]==vi){
                nInstancesViCi++;
                nInstancesCi++;
            }else if(row[row.length-1]==ci){
                nInstancesCi++;
            }
        }
        double prob =((double)nInstancesViCi)/((double)nInstancesCi); 
        return prob;
    }
    public double classifyGivenUnknownData(){
        ArrayList<Double> probabilities = new ArrayList<>();
        getClasses();
        
        for(double ci : classes){
            double p=1.0;
            for(int i=0;i<unknownData.length-1;i++){
                p*=probVarGivenClass(unknownData[i], ci, i);
            }
            probabilities.add(probClase(ci)*p);
        }
       
        int index=0;
        for(int i=0;i<probabilities.size()-1;i++){
            if((probabilities.get(i))<probabilities.get(i+1)){
                index= i+1;
            }
        }
        return classes[index];
    }
    private void getClasses(){
        TreeSet<Double> tree = new TreeSet<>();
        for(double row[] :dataset){
            tree.add(row[row.length-1]);
        }
        
        Object[] c=tree.toArray();
        classes = new double[c.length];
        for(int i=0;i<c.length;i++){
            classes[i]=(double)c[i];
        }
       
     }
    /*public static void main(String[] args) {
        double[][] dataTraining={
            {1,1,1},
            {2,2,1},
            {1,1,1},
            {1,1,1},
            {1,1,1},
            {2,2,2},
            {2,2,2},
            {1,1,2},
            {1,2,2},
            {2,2,2}
        };
        Bayesiano nb = new Bayesiano(dataTraining);
        for(double[] row:dataTraining){
            nb.setUnknownData(row);
            System.out.println("Datos: "+Arrays.toString(row)+" Predicción: "+nb.classifyGivenUnknownData());     
        }
        
    }*/
}
