package it.polito.tdp.meteo.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;

import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {
	
	private MeteoDAO dao;
	private List<Situazione> situazioni;
	private Pseudograph<Integer, DefaultEdge> grafo;
	
	
	public Model(){
		dao = new MeteoDAO();
		situazioni = new ArrayList<>();
	}
	
	public List<Situazione> getSituazioniTorino(){
		if(situazioni.isEmpty()){
			situazioni = dao.getAllSituazioniTorino();
		}
		return situazioni;
	}
	
	public Map<Situazione, Integer> getTmedia(int tMedia){
		
		Map<Situazione, Integer> mappaRis = new LinkedHashMap<>();
		for(int i =1; i<this.getSituazioniTorino().size(); i++){
			if(situazioni.get(i).getTMedia()==tMedia){
				mappaRis.put(situazioni.get(i),situazioni.get(i-1).getTMedia());
			}
		}
		return mappaRis;
	}
	
	public void creaGrafo(){
		
		grafo = new Pseudograph<Integer, DefaultEdge>(DefaultEdge.class);
		//AGGIUNGO VERTICI
		Graphs.addAllVertices(grafo, dao.getAllTmedie());
		System.out.println(grafo);
		
		//CREO GLI ARCHI
		for(int i = 0; i<situazioni.size()-1; i++){
			grafo.addEdge(situazioni.get(i).getTMedia(), situazioni.get(i+1).getTMedia());
		}
		
		System.out.println(grafo);
	}

	public Set<Integer> getScalaTermica(int temperatura){
		
		Set<Integer> ris = new HashSet<>();
		ris.addAll(Graphs.neighborListOf(grafo, temperatura));
		System.out.println(ris);
		
		Set<Integer> temp = new HashSet<>(ris);
		for(Integer i : temp){
			ris.addAll(Graphs.neighborListOf(grafo, i));
		}
		System.out.println(ris);
		return ris;
	}
}
