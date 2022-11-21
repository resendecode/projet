
package com.gabi.serveur;

/**
 *
 * @author gabriel
 */

import java.net.MalformedURLException;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;

public class ProduitApi {
	private java.lang.String barcode;
	final private java.net.URL url;
	private java.net.URLConnection connection;


	//le constructeur de l'objet json
	JsonObjectBuilder constructeur_objet = Json.createObjectBuilder();
	//la chaine de char où les informations seront mises.
	String string_json; 



	ProduitApi(java.lang.String barcode)throws Exception, MalformedURLException, IOException { //nécessaire de mettre le "throws " pour ne pas avoir d'erreur sur url
		this.barcode = barcode;
		this.url = new java.net.URL("http://world.openfoodfacts.org/api/v0/product/" + this.barcode + ".json");	
		this.connection =  url.openConnection();	
		if (connection != null) {
		parseJson();
		}
		else{
			throw new Exception("erreur de connection");
		}



	}
	public void parseJson() throws IOException{
			//La connection établie fournit un fichier que nous mettons dans une stream 'response'
			//L'objet 'parser' est crée pour itérer sur cette stream et rajouter à notre objet 'constructeur_objet'
			//toutes les informations pertinentes 
			java.io.InputStreamReader response = new java.io.InputStreamReader(connection.getInputStream());
			javax.json.stream.JsonParser parser = javax.json.Json.createParser(response);
			
			//contrôle si on arrive pas à la fin de la stream
			while (parser.hasNext()) {
				
				String key;
				String value;
				javax.json.stream.JsonParser.Event event = parser.next();
				 if (event == javax.json.stream.JsonParser.Event.KEY_NAME) {
				//pour chaque valeur de key, on l'interprete selon sa valeur
				//puis on rajoute les valeures dans objet et on 'break' le cycle pour re-iterer dans le while
					switch (parser.getString()){
						case "code":
							key = parser.getString();
							parser.next();
							value = parser.getString();
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
//Dans ce cas particulier, la 'selected_images' contient plusieures valeures non pertinentes
//On doit donc rentrer dans l'objet de la valeur pour chercher plus en détail jusqu'à trouver
//l'url de l'image dans la bonne resolution


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

			
	}
	public String build()throws IOException{
		string_json = constructeur_objet.build().toString();
		System.out.print(string_json);
		return string_json;
	}



}


