
package com.gabi.serveur;

/**
 *
 * @author gabriel
 */

import java.net.MalformedURLException;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.StringReader;

public class ProduitApi {
	private java.lang.String barcode;
	final private java.net.URL url;
	private java.net.URLConnection connection;
	JsonObjectBuilder constructeur_objet = Json.createObjectBuilder();
	String original;
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

			//DEBUG
			javax.json.JsonReader jsonReader = Json.createReader(response);
			original = jsonReader.readObject().toString();
			javax.json.stream.JsonParser parser = javax.json.Json.createParser(new StringReader(original));

			//DEBUG	
			
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

						//Dans la clé "owner_fields" on obtient une valeur erronée, donc on l'avance
						case "owner_fields":
							System.out.println("balise erronée trouvée");
							parser.next();
							parser.skipObject();
							break;
						//

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

//puisque l'arborescence de l'objet valeur se repete pour plusieures valeures parent
//exemple : "...display/fr/img"  et "...display/eng/img"
//il est nécessaire de 'break' le cycle à chaque 'if' pour ne pas retomber sur la mauvaise balise

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
					
					
						case "categories":
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
	//Cette méthode converti juste l'objet qu'on a obtenu dans 'parseDocument()' vers une string pour qu'elle puisse
	//être envoyée ensuite par le serveur ws
	public String build()throws IOException{
		string_json = constructeur_objet.build().toString();
		System.out.println(string_json);
		return string_json;
	}
	public void write(){
		System.out.println(original);
			
	}



}


