/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gabi.serveur;

/**
 *
 * @author gabriel
 */
import java.net.MalformedURLException;
import java.nio.file.Path;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser.Event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
public class ProduitApi {
	private java.lang.String barcode;
	final private java.net.URL url;
	private java.net.URLConnection connection;


	//le constructeur de l'objet json
	JsonObjectBuilder constructeur_objet = Json.createObjectBuilder();
	//la chaine de char où les informations seront mises.
	String string_json; 



	ProduitApi(java.lang.String barcode)throws MalformedURLException, IOException { //nécessaire de mettre le "throws " pour ne pas avoir d'erreur sur url
	this.barcode = barcode;
	this.url = new java.net.URL("http://world.openfoodfacts.org/api/v0/product/" + this.barcode + ".json");	
	connection =  url.openConnection();	
	stream();




	}
	public void stream() throws IOException{
		if (connection != null) {
			java.io.InputStreamReader response = new java.io.InputStreamReader(connection.getInputStream());
			javax.json.stream.JsonParser parser = javax.json.Json.createParser(response);
			// javax.json.JsonReader reader = Json.createReader(response);
			// BufferedWriter br = new BufferedWriter(new FileWriter("serveur/serverside/src/main/java/com/gabi/serverside/json/yalpro"));
			// br.write(reader.read().toString());
			// br.close();
			//écrire fichier en mémoire
			
			while (parser.hasNext()) {
				
				String key;
				String value;
				javax.json.stream.JsonParser.Event event = parser.next();
				 if (event == javax.json.stream.JsonParser.Event.KEY_NAME) {//si nous tombons sur un keyname, nous allons chercher lequel c'est pour le mettre dans notre objet
					switch (parser.getString()){
						case "code":
							key = parser.getString();
							//System.out.print(key);
							parser.next();
							value = parser.getString();
							//System.out.print(value);
							constructeur_objet.add(key, value);
							break;
						case "product_name_fr":
							key = parser.getString();
							parser.next();
							value = parser.getString();
							constructeur_objet.add("nom", value);
							break;
						case "brands":
							key = parser.getString();
							parser.next();
							value = parser.getString();
							constructeur_objet.add("marque", value);
							break;
						case "quantity":
							key = parser.getString();
							parser.next();
							value = parser.getString();
							constructeur_objet.add("qte", value);
							break;
						case "nutrition_grade_fr":
							key = parser.getString();
							parser.next();
							value = parser.getString();
							constructeur_objet.add("nutriscore", value);
							break;
						case "selected_images":

							key = parser.getString();
							parser.next();
							while(parser.hasNext()){
								event = parser.next();
								if(event == javax.json.stream.JsonParser.Event.KEY_NAME &&parser.getString().equals("front")){
									while(parser.hasNext()){
										event = parser.next();
										if(event == javax.json.stream.JsonParser.Event.KEY_NAME &&parser.getString().equals("display")){
											while(parser.hasNext()){
												event = parser.next();
												if(event == javax.json.stream.JsonParser.Event.KEY_NAME &&parser.getString().equals("fr")){
													parser.next();
													constructeur_objet.add("img",parser.getValue());
													break;
												}
											}
											break;
										}
									}
									break;
								}
							}
							break;
					
					
						case "categories_old":
							parser.next();
							constructeur_objet.add("categories", parser.getValue());
							break;
						case "ingredients_text_fr":
							parser.next();
							constructeur_objet.add("ingredients", parser.getValue());
							break;
						case "nutriments":
							parser.next();
							constructeur_objet.add("nutriment", parser.getValue());
							break;
						}
					
						
				}
			}
			//bufferdInputStream.mark(some_value);
			//bufferdInputStream.reset();
			//read it again
				//java.io.InputStreamReader response_ = new java.io.InputStreamReader(connection.getInputStream());

		}	
	}
	public String print()throws IOException{
		string_json = constructeur_objet.build().toString();
		System.out.print(string_json);

		//FileWriter file = new FileWriter("serveur/src/main/java/com/gabi/serveur/json/final.json");
        //serveur/src/main/java/com/gabi/serveur/json/final.json
        //file.write(string_json);
		//file.close();
		return string_json;
	}



}


