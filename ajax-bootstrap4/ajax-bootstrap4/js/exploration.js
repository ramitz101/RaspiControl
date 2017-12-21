const urlExplorations = "https://andromia-maxroy1993.c9users.io/explorations"


$(document).ready(function(){
  let monExploNum = parseInt(parent.document.URL.substring(parent.document.URL.indexOf('=')+1, parent.document.URL.length));
  let monExploURL; 
	
  $.ajax({
           type: "GET",
           url: urlExplorations,
           headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },		  
           success: function(explorations)
           {
            let chaineExplo;
			let compteur =0;
			for(let index in explorations){ 
				let exploration = explorations[index];				
				if(compteur == monExploNum)
				{		
			// Je sais que cette solution n'est pas très bonne avec beaucoup de donnés			
					monExploURL = exploration.href;
					 $.ajax({
					   type: "GET",
					   url: monExploURL,
					   headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },		  
					   success: function(exploration)
					   {
							$("#exploNom").append(exploration.Destination);
							$("#exploDepart").append(exploration.Depart);
							$("#exploDate").append(exploration.dateExploration);
							$("#exploUnit").append(exploration.unit.name);
							
							$("#exploRuneAir").append(exploration.runes.air);
							$("#exploRuneDarkness").append(exploration.runes.darkness);
							$("#exploRuneEarth").append(exploration.runes.earth);
							$("#exploRuneEnergy").append(exploration.runes.energy);
							$("#exploRuneFire").append(exploration.runes.fire);
							$("#exploRuneLife").append(exploration.runes.life);		
							$("#exploRuneLight").append(exploration.runes.light);
							$("#exploRuneLogic").append(exploration.runes.logic);							
							$("#exploRuneMusic").append(exploration.runes.music);	
							$("#exploRuneSpace").append(exploration.runes.space);
							$("#exploRuneToxic").append(exploration.runes.toxic);
							$("#exploRuneWater").append(exploration.runes.water);
					   },
					   error: function(error)
					   {
						   window.location.href="./error.html";
					   }
					 });
				}
				compteur++;
				$("#explorations").append(chaineExplo);		
			}				
           },
		   error: function(error)
		   {
			   window.location.href="./error.html";
		   }
         });
		 
		
		 
  
  
  
});