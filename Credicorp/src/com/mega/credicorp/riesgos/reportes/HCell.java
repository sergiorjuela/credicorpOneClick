package com.mega.credicorp.riesgos.reportes;

import java.util.ArrayList;

/*
 * La clase celda define la estructura de una celda de un mapa de calor
 * la cual se compone de dos principales atributos color y valuecontexts, 
 * el primero de ellos almacena el string hexadecimal qu contiene la 
 * caracteristica de color de la celda y sel segundo e un arraylist 
 * que almacena el numero de valores por contexto que se mostraran en la 
 * cedal es decir el numero total contenido en la celda.  
 */

public class HCell {

	// Definición de atributos
	private String color;
	private ArrayList<String> valueContexts;

	// El constructor inicializa los dos atributos
	public HCell() {
		this.color = "";
		this.valueContexts = new ArrayList<String>();
	}

	// Metosos de set y get de los atributos

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ArrayList<String> getValueContexts() {
		return this.valueContexts;
	}

	public void setValueContexts(ArrayList<String> valueContexts) {
		this.valueContexts = valueContexts;
	}
}
