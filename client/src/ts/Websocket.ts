// Création du WebSocket
export var ws = new WebSocket("ws://localhost:8025/BetterFood/websocket");

// Au démarrage
ws.onopen = function(e) {
    console.log("Connexion au websocket effectué");
};

// Erreur
ws.onerror = function(error) {
    alert("impossible de se connecter au WebSocket");
}

// Message reçu
ws.onmessage = function(event) {
    // met le string 'event' en json
    let json = JSON.parse(event.data);
    let size = Object.keys(json).length;
    // Div d'affichage
    let result = document.getElementById("parse");
    let error = document.getElementById("error");

    console.log(json);
    // Vérification si le code barre existe ou non
    if(size == 1){
      error!.innerHTML = "Le code barre n'existe pas.";
      return;
    } 
    // enlève le texte d'erreur si l'ancien code barre ne fonctionnait pas
    if (error!.innerHTML.length != 0) error!.innerHTML = "";

    // Affichage
    result!.innerHTML = 
    `<img src="${json.img}">
    <div class='ptext'>
    <h2>${json.nom}</h2> <br/>
    marque : ${json.marque} <br/>
    catégorie : ${json.categories} <br/>
    nutriscore : ${json.nutriscore.toUpperCase()} <br/>
    quantité : ${json.qte} <br/>
    ingrédients : ${json.ingredients} <br/>
    <table>
  <tr>
    <th>Tableau nutritionnel</th>
    <th>Pour 100g</th>
    <th>Par portion (${json.qte})</th>
  </tr>
  <tr>
    <td>Energie</td>
    <td>${json.nutriment["energy-kcal_100g"]} ${json.nutriment["energy-kcal_unit"]}</td>
    <td>${json.nutriment["energy-kcal_serving"]} ${json.nutriment["energy-kcal_unit"]}</td>
  </tr>
  <tr>
    <td>Matières grasses</td>
    <td>${json.nutriment["fat_100g"]} ${json.nutriment["fat_unit"]}</td>
    <td>${json.nutriment["fat_serving"]} ${json.nutriment["fat_unit"]}</td>
  </tr>
  <tr>
    <td>Dont acides gras saturés</td>
    <td>${json.nutriment["saturated-fat_100g"]} ${json.nutriment["saturated-fat_unit"]}</td>
    <td>${json.nutriment["saturated-fat_serving"]} ${json.nutriment["saturated-fat_unit"]}</td>
  </tr>
  <tr>
    <td>Glucides</td>
    <td>${json.nutriment["carbohydrates_100g"]} ${json.nutriment["carbohydrates_unit"]}</td>
    <td>${json.nutriment["carbohydrates_serving"]} ${json.nutriment["carbohydrates_unit"]}</td>
  </tr>
  <tr>
    <td>Dont sucres</td>
    <td>${json.nutriment["sugars_100g"]} ${json.nutriment["sugars_unit"]}</td>
    <td>${json.nutriment["sugars_serving"]} ${json.nutriment["sugars_unit"]}</td>
  </tr>
  <tr>
    <td>Fibres alimentaires</td>
    <td>${json.nutriment["fiber_100g"]} ${json.nutriment["fiber_unit"]}</td>
    <td>${json.nutriment["fiber_serving"]} ${json.nutriment["fiber_unit"]}</td>
  </tr>
  <tr>
    <td>Protéines</td>
    <td>${json.nutriment["proteins_100g"]} ${json.nutriment["proteins_unit"]}</td>
    <td>${json.nutriment["proteins_serving"]} ${json.nutriment["proteins_unit"]}</td>
  </tr>
  <tr>
    <td>Sel</td>
    <td>${json.nutriment["salt_100g"]} ${json.nutriment["salt_unit"]}</td>
    <td>${json.nutriment["salt_serving"]} ${json.nutriment["salt_unit"]}</td>
  </tr>
</table>
    </div>`;
    //Scroll la page vers le résultat
    document.getElementById('parse')!.scrollIntoView({ block: 'center',  behavior: 'smooth' });
};