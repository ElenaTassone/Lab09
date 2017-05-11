package it.polito.tdp.metrodeparis.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private WeightedGraph <Fermata, Connessione> grafo ;
//	private UndirectedGraph <Fermata, DefaultWeightedEdge> grafo ;
	private MetroDAO dao ;
	private List<Connessione> connessioni ;
	private List<Fermata> fermate ;
	private Map<Fermata, Fermata> map ;
	private double durata;
	
	public Model(){
//		this.grafo = new  WeightedMultigraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
	//	this.grafo = new  WeightedMultigraph<Fermata, Connessione>(Connessione.class) ;
		dao = new MetroDAO () ;
		
	}

	
	public void creaGrafo(){
		
		connessioni = new ArrayList<Connessione> (dao.getAllConnessioni()) ;
		this.grafo = new  WeightedMultigraph<Fermata, Connessione>(Connessione.class) ;
		Graphs.addAllVertices(grafo, this.getFermate()) ;
		
		for(Connessione c : connessioni){
			if(!grafo.containsEdge(c)){
				if(!c.getPartenza().equals(c.getArrivo())){
					if(grafo.vertexSet().contains(c.getPartenza()) && grafo.vertexSet().contains(c.getArrivo())){
				grafo.addEdge(c.getArrivo(), c.getPartenza(), c) ;
				grafo.setEdgeWeight(c, c.calcolaPeso());
				}
			}
		}
	}
			
	}
	
	
	
	public List<Fermata> getFermate (){
		if(fermate!=null)
			return fermate;
		else{
			
			this.fermate = new ArrayList<Fermata> () ;
			fermate = dao.getAllFermate() ;
			return fermate ;
		}
			
	}

	public List<Fermata> calcolaCamminoMinimo(Fermata partenza, Fermata arrivo){
		DijkstraShortestPath<Fermata, Connessione> dsp = new DijkstraShortestPath<Fermata, Connessione> (grafo, partenza, arrivo) ; 
		List <Connessione> edges= new ArrayList<Connessione> (dsp.findPathBetween(grafo, partenza, arrivo)) ;
		
		map = new LinkedHashMap<Fermata, Fermata> () ;
		map.put(null, partenza) ;
		
		for(int i = 0 ; i < edges.size() ; i ++){
			boolean aggiunto = false ;
			Connessione c = edges.get(i) ;
			if(map.containsValue(c.getPartenza()) && aggiunto == false){
				map.put(c.getPartenza(), c.getArrivo());
				aggiunto = true ;
				}
			if(map.containsValue(c.getArrivo()) && aggiunto == false){
				map.put(c.getArrivo(), c.getPartenza()) ;
				aggiunto = true ;
				}
		}
		List<Fermata> lista = new ArrayList<Fermata> (map.values());
		
		durata = (dsp.getPathLength() + 30*lista.size());
		return lista ;
	}
	

	public double getDurata() {
		return durata;
	}

	public WeightedGraph <Fermata, Connessione>getGrafo() {
		if(grafo==null)
			this.creaGrafo();
		return grafo;
	}
 
}
