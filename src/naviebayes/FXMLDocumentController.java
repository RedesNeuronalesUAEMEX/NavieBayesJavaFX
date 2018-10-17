/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naviebayes;


import arffile.ArFFile;
import arffile.Bayesiano;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.DialogPane;

/**
 *
 * @author cachubis
 * El programa necesita el jar que viene en el paquete MyUtilityJar
 * que fue generado con las clases incluidas en el paquete ReaderAndBayes
 * 
 * alumnos: Daniel hernández velasco
 * José roberto sandoval Lara
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField pathFileView;
    @FXML
    private Button addFileView;
    @FXML
    private TextArea fileArffContentView;
    @FXML
    private TextArea datasetView;
    @FXML
    private TextArea info;
    
    private double[][] numericDataset;
    private ArrayList<String> trms;
    @FXML
    private Button clasify;
    @FXML
    private TextArea classifyArea;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        fileArffContentView.clear();
        datasetView.clear();
        info.clear();
        classifyArea.clear();
        ArFFile ar = new ArFFile();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("arff files", "*.arff"));
        File f =fileChooser.showOpenDialog(null);
        if(f!=null){
            clasify.setDisable(false);
            pathFileView.setText(f.getAbsolutePath());
            ar.arFFileReader(f);
            fileArffContentView.appendText(ar.getFileContains());
            numericDataset=ar.getNumericDataset();
            for(double[] row:numericDataset){
                datasetView.appendText(Arrays.toString(row)+"\n");
            }
            trms=ar.getTerms();
            for(int i=0;i<trms.size();i++){
                info.appendText(trms.get(i)+"-->"+(i+1)+"\n");
            }
        }else{
            Alert dg = new Alert(Alert.AlertType.ERROR);
            dg.setTitle("Error de archivo");
            dg.setContentText("No se ha seleccionado un archivo");
            dg.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void classifyNavieBayes(ActionEvent event) {     
        Bayesiano b = new Bayesiano(this.numericDataset);
        for(double[] row:numericDataset){
            b.setUnknownData(row);
            b.classifyGivenUnknownData();
            classifyArea.appendText("Datos: "+Arrays.toString(row)+"\t Predicción: "+b.classifyGivenUnknownData()+"\n");
        }
    }
    
}
