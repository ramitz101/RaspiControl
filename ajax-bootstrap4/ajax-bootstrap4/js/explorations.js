const urlExplorations = "https://andromia-maxroy1993.c9users.io/explorations"


$(document).ready(function(){
  
   $.ajax({
           type: "GET",
           url: urlExplorations,
           headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },
		   contentType: 'application/json; charset=utf-8',
           success: function(explorations)
           {
            let chaineExplo;
			let compteur =0;
			for(let index in explorations){
				let exploration = explorations[index];
				
				chaineExplo = "<section class='event'>";
				chaineExplo += "<h5 class='event-heading'><a href='exploration.html" +"?ex="+compteur+"'>"  + exploration.Destination+ "</a></h5>";		
				chaineExplo += "<p class='text-muted'>" + exploration.dateExploration+ "</p>";		
				chaineExplo += "<p>"+ "Départ: "+  exploration.Depart +"</p>";
				chaineExplo += "<p>"+"Unit trouvé: "+"<a>"+ exploration.unit.name + "</a></p>"; 
				
				chaineExplo += "</section>";
				compteur++;
				$("#explorations").append(chaineExplo);		
			}				
           },
		   error: function(error)
		   {
			  $("#explorations").append("Aucune exploration");	
		   }
         });
  
  
  
});