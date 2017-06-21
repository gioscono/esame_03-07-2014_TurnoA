package it.polito.tdp.meteo;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.meteo.bean.Model;
import it.polito.tdp.meteo.bean.Situazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class MeteoController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCalcola;

    @FXML
    private Button btnCerca;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtTemperatura;
    
    @FXML
    void doCalcola(ActionEvent event) {

    	int t = Integer.parseInt(txtTemperatura.getText());
    	model.creaGrafo();
    	Set<Integer> ris = model.getScalaTermica(t);
    	txtResult.appendText("Temperature raggiungiunte a due giorni di distanza da : "+t+"\n");
    	for(Integer i : ris){
    		txtResult.appendText(i+"\n");
    	}
    }

    @FXML
    void doCerca(ActionEvent event) {

    	String t = txtTemperatura.getText();
    	try{
    		int temp = Integer.parseInt(t);
    		Map<Situazione, Integer> ris = model.getTmedia(temp);
    		txtResult.appendText("Giorni in cui si è rilevata la T.Media specificata:\n");
    		for(Situazione s : ris.keySet()){
    			txtResult.appendText("Giorno: "+s.getData()+"T "+s.getTMedia()+"°C. Giorno prec: "+ris.get(s)+"\n");
    		}
    		
    	}catch(NumberFormatException e){
    		txtResult.appendText("Errore: inserire una temperatura valida.\n");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert txtTemperatura != null : "fx:id=\"txtTemperatura\" was not injected: check your FXML file 'Meteo.fxml'.";


    }

	public void setModel(Model model) {
		this.model = model;
		
	}

}
