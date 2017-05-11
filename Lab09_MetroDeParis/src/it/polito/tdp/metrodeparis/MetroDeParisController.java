package it.polito.tdp.metrodeparis;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.UndirectedGraph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	Model model ; 
	private UndirectedGraph <Fermata, Connessione> grafo ;
	List <Fermata> fermate ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> cmbArrivo;

    
    @FXML
    private ComboBox<Fermata> cmbPartenza;

    @FXML
    private Button btnCalcolaPercorso;
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Fermata partenza = cmbPartenza.getValue() ;
    	Fermata arrivo = cmbArrivo.getValue() ;
    	if(partenza!= null && arrivo!=null){
    		List<Fermata> result = model.calcolaCamminoMinimo(partenza, arrivo) ;
    		txtResult.clear() ;
    		for(Fermata r : result){
    			txtResult.appendText(r.toString()+"\n");
    		}
    		
    		txtResult.appendText("\n Tempo percorso in minuti:" +(Math.round(model.getDurata()/60)));
    	}
    	else{
    		txtResult.setText("Errore");
    	}

    }

    
    @FXML
    void initialize() {
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }


    

	public void setModel(Model model) {
		this.model= model;
		this.model.creaGrafo();
		fermate = this.model.getFermate() ;
		cmbPartenza.getItems().addAll(fermate) ;
		cmbArrivo.getItems().addAll(fermate) ;
	}


	
	
}