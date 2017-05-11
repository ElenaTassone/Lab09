package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery() ;
			
			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
	public List<Connessione> getAllConnessioni(){

		// cotrollaremaiuscole o minunscoole
		
		final String sql = "SELECT fp.coordX as xp, fp.coordY as yp, fp.id_fermata as idp, fp.nome as np, "
				+ "fa.coordX as xa, fa.coordY as ya, fa.id_fermata as ida, fa.nome as na, "
				+ "linea.id_linea, linea.nome, linea.velocita, linea.intervallo, linea.colore, connessione.id_connessione "
				+ "FROM fermata fp, fermata fa, connessione, linea "
				+ "WHERE connessione.id_linea = linea.id_linea "
				+ "AND connessione.id_stazP = fp.id_fermata "
				+ "AND connessione.id_stazA = fa.id_fermata ";
		
		List<Connessione> connessioni = new ArrayList<Connessione>() ;

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery() ;
			
			while (rs.next()) {
				LatLng coordsP = new LatLng(rs.getDouble("xp"), rs.getDouble("yp")) ;
				Fermata p = new Fermata(rs.getInt("idp"), rs.getString("np"), coordsP ) ;
				
				LatLng coordsA = new LatLng(rs.getDouble("xa"), rs.getDouble("ya")) ;
				Fermata a = new Fermata(rs.getInt("ida"), rs.getString("na"), coordsA ) ;
				 
				//forse linea.id_linea
				Linea l = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"), 
						rs.getDouble("intervallo"), rs.getString("colore"));
				
				Connessione c = new Connessione(rs.getInt("id_connessione"), l, p, a);
			
				
				connessioni.add(c) ;
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
			}
		
		return connessioni ;
	}
}
