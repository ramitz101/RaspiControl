const SERVICE_URLUNIT="https://andromia-maxroy1993.c9users.io/units/";


$(document).ready(function(){
   
   let uuid = parent.document.URL.substring(parent.document.URL.indexOf('?')+1, parent.document.URL.length);
    let url = SERVICE_URLUNIT + uuid;
	$.ajax({
           type: "GET",
           url: url,
		   headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },
           success: function(unit)
           {
			
										
				let image = "<img id='unitsImg' src='"+unit.imageURL+"' alt='"+unit.name+"' />";
				chaineUnit = "<tr><td colspan='3'>"+unit.name+"</td> </tr>" 
				chaineUnit += "<tr>";		
				chaineUnit += "<td rowspan='5'>" + image+ "</td>";	
				chaineUnit += "<td><img src='./img/hearts_green.png' atl='hearts_green' /></td>";
				chaineUnit += "<td><strong>"+unit.life+"</strong></td>";
				chaineUnit += "</tr>";
				chaineUnit += "<tr>";
				chaineUnit += "<td><img src='./img/cycle.png' alt='cycle' /></td>";
				chaineUnit += "<td><strong>"+unit.speed+"</strong></td>";
				chaineUnit += "</tr>";
				chaineUnit += "<tr>";
				for(let weapons in unit.runes.weapons){
					chaineUnit += "<td><img src='./img/weapons/"+weapons+".png' alt='"+weapons+"' /></td>";
				}	
				chaineUnit += "</tr>";
				chaineUnit += "<tr>";
				let compteur;
				for(let ability in unit.runes.abilities){
					if(compteur === 2)
					{
						chaineUnit += "</tr>";
						chaineUnit += "<tr>";
					}
					chaineUnit += "<td><img src='./img/runes/"+ability+".png' alt='"+ability+"' /></td>";
					compteur++;					
				}
				chaineUnit += "</tr>";
				
				$("#unitTable").append(chaineUnit);
			
           },
		   error: function(error)
		   {
			    $("#unitTable").append("Aucune unit");
		   }
         });
   
   
   
    
    
});