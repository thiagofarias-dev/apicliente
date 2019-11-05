package com.clientes.apicliente.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Estado {

	private int id;
	private String sigla;
	private String nome;

	public Estado() {
		super();
	}

	public Estado(int id, String sigla, String nome) {
		super();
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
	}

	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Estado> ordenarEstados(List<Estado> estados) {

		List<Estado> estadosOrdenadaSPRJ = new ArrayList<Estado>();
		List<Estado> estadosOrdenadaDemais = new ArrayList<Estado>();

		for (int i=0; i<estados.size();i++) {
			Estado estado = estados.get(i); 

			if (estado.getSigla().equals("SP") || estado.getSigla().equals("RJ")) {
				estadosOrdenadaSPRJ.add(estado);
				if (estadosOrdenadaSPRJ.size()==2){

				}
			}else{
				estadosOrdenadaDemais.add(estado);
			}


		}

		Collections.sort(estadosOrdenadaSPRJ, Comparator.comparing(Estado::getSigla).reversed());
		Collections.sort(estadosOrdenadaDemais, Comparator.comparing(Estado::getSigla));
		estadosOrdenadaSPRJ.addAll(estadosOrdenadaDemais);
		return estadosOrdenadaSPRJ;
	}
}
