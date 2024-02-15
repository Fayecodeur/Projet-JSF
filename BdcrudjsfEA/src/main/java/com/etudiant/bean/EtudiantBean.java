package com.etudiant.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named

public class EtudiantBean {
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiant;
	private boolean set = false;
	private static int etuId;
	
	public EtudiantBean() {
		etudiant = new Etudiant();
	}
	
	
	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsfEA","root","");
			return con;
		} catch ( ClassNotFoundException e) {
			e.printStackTrace();
			Connection con = null;
			return con;
		}catch ( SQLException e) {
			e.printStackTrace();
			Connection con = null;
			return con;
		}
		
	}
	

	
	public List<Etudiant> getAllStudents(){
		listeEtudiant = new ArrayList<Etudiant>();
		String req = "select * from etudiant";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				Etudiant e = new Etudiant();
				e.setId(res.getInt("id"));
				e.setNom(res.getString("nom"));
				e.setPrenom(res.getString("prenom"));
				e.setDatenaiss(res.getDate("datenaiss"));
				listeEtudiant.add(e);
				
			}
			return listeEtudiant;
		} catch (SQLException e) {
			e.printStackTrace();
			return listeEtudiant;
		}
		
	}
	
	public void addStudent() {
		String req = "INSERT INTO etudiant (nom, prenom, datenaiss) VALUES (?, ?, ?)";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, new java.sql.Date(etudiant.getDatenaiss().getTime()));
			ps.execute();
			
			getAllStudents();
			etudiant = new Etudiant();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void deleteStudent(Etudiant etd) {
		String req = "DELETE FROM etudiant WHERE id = ?";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ps.setInt(1, etd.getId());
			ps.execute();			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void display(Etudiant etd) {
		etuId = etd.getId();
		etudiant = etd;
		set = true;
		
		
	}
	
	public void setStudent() {
		String req = "UPDATE  etudiant SET nom = ?, prenom = ?, datenaiss = ? WHERE id = ?";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, new java.sql.Date(etudiant.getDatenaiss().getTime()));
			ps.setInt(4, etuId);
	
			
			System.out.println(ps);
			ps.executeUpdate();	
			
			getAllStudents();
			etudiant = new Etudiant();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the listeEtudiant
	 */
	public List<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}

	/**
	 * @param listeEtudiant the listeEtudiant to set
	 */
	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}

	/**
	 * @return the pisteEtudiant
	 */
	public List<Etudiant> getPisteEtudiant() {
		return getAllStudents();
	}


	/**
	 * @return the set
	 */
	public boolean isSet() {
		return set;
	}


	/**
	 * @param set the set to set
	 */
	public void setSet(boolean set) {
		this.set = set;
	}


	/**
	 * @return the etuId
	 */
	public int getEtuId() {
		return etuId;
	}


	/**
	 * @param etuId the etuId to set
	 */
	public void setEtuId(int etuId) {
		EtudiantBean.etuId = etuId;
	}

	
	

	

	

}
