/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReaderAndBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cachubis
 */
public class ArFFile {

    private ArrayList<String[]> dataSet =new ArrayList<>();
    private ArrayList<String> terms;
    private String fileContains="";

    public ArrayList<String> getTerms() {
        return terms;
    }
    public ArrayList<String[]> getDataSet() {
        return dataSet;
    }

    public String getFileContains() {
        return fileContains;
    }
    
    public void arFFileReader(File f) {
        try {
            FileReader read = new FileReader(f);
            BufferedReader buf = new BufferedReader(read);
            String s = "";
            while ((s = buf.readLine()) != null) {
                if (!s.equals("")) {
                    if ((s.charAt(0) == '%') || (s.charAt(0) == '@')) {

                    } else{
                        dataSet.add(s.split(","));
                    }
                }
                fileContains+=s+"\n";
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArFFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArFFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double[][] getNumericDataset(){
        terms= new ArrayList<>();
        
        double[][] numDataSet = new double[dataSet.size()][dataSet.get(0).length];
        dataSet.forEach((row) -> {
            for(String s : row){
                if(!terms.contains(s)){
                    terms.add(s);
                }
            }
        });
        System.out.println("Términos: ");
        for (String s:terms) {
            System.out.print(s+", ");
        }
        System.out.println("\nAsociación numerica de terminos");
        for(int i=0;i<terms.size();i++){
            System.out.print((i+1)+", ");
        }
        System.out.println("");
        for(int i=0;i<dataSet.size();i++){
            String[] dat=dataSet.get(i);
            for(int j=0;j<dat.length;j++){
                
                numDataSet[i][j]=terms.indexOf(dat[j])+1;
            } 
        }
        return numDataSet;
    }
    
    /*public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro=new FileNameExtensionFilter("arff files","arff");
        fileChooser.setFileFilter(filtro);

        fileChooser.showOpenDialog(null);
        File f = fileChooser.getSelectedFile();
        ArFFile reader =new ArFFile();
        reader.arFFileReader(f);
        
        double[][] numericDataSet=reader.getNumericDataset();
        for(double[] row:numericDataSet){
            System.out.println(Arrays.toString(row));
        }
    }*/

}
