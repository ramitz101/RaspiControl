const URLRUNES="https://andromia-maxroy1993.c9users.io/runes";


$(document).ready(function(){
   
   
    $.ajax({
           type: "GET",
           url: URLRUNES,
		   headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },
           success: function(runes)
           {
			   
               $("#runeAirQte").append(runes["air"]);
			   $("#runeDarknessQte").append(runes["darkness"]);
			   $("#runeEarthQte").append(runes["earth"]);
			   $("#runeEnergyQte").append(runes["energy"]);
			   $("#runeFireQte").append(runes["fire"]);
			   $("#runeLifeQte").append(runes["life"]);
			   $("#runeLightQte").append(runes["light"]);
			   $("#runeLogicQte").append(runes["logic"]);
			   $("#runeMusicQte").append(runes["music"]);
			   $("#runeSpaceQte").append(runes["space"]);
			   $("#runeToxicQte").append(runes["toxic"]);
			   $("#runeWaterQte").append(runes["water"]);
           },
		   error: function(error)
		   {
			    //window.location.href="./error.html";
		   }
         });
   
    
    
});