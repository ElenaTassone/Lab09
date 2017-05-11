package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Connessione extends DefaultWeightedEdge{

	private int codCon ;
	private Linea metro;
	private Fermata partenza ;
	private Fermata arrivo ;
	
	private double peso ;
	
	public Connessione(int codCon, Linea metro, Fermata partenza, Fermata arrivo) {
		super();
		this.codCon = codCon;
		this.metro = metro;
		this.partenza = partenza;
		this.arrivo = arrivo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codCon;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (codCon != other.codCon)
			return false;
		return true;
	}


	public int getCodCon() {
		return codCon;
	}
	public void setCodCon(int codCon) {
		this.codCon = codCon;
	}
	public Linea getMetro() {
		return metro;
	}
	public void setMetro(Linea metro) {
		this.metro = metro;
	}
	public Fermata getPartenza() {
		return partenza;
	}
	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}
	public Fermata getArrivo() {
		return arrivo;
	}
	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}
	
	
	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double calcolaPeso(){
		if(peso==0.0){
			double distanza = LatLngTool.distance(partenza.getCoords(), arrivo.getCoords(), LengthUnit.KILOMETER) ;
			peso = (distanza/metro.getVelocita()) *3600;
		}
		return peso ;
	}

	@Override
	public String toString() {
		return metro.getNomeLinea()+":" + partenza.getNome() + "-" + arrivo.getNome() +"  "+ this.calcolaPeso()+"\n";
	}

}
