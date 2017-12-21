const SERVICE_URLUNITS="https://andromia-maxroy1993.c9users.io/units";


$(document).ready(function(){
   
    $.ajax({
           type: "GET",
           url: SERVICE_URLUNITS,
		   headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },
           success: function(units)
           {
			let chaineUnit;
			let compteur = 0;
			for(let index in units){
				let unit = units[index];
				let image = "<img id='unitsImg' src='"+unit.imageURL+"' alt='unit' />";
				// Sert a aller chercher le uuid d'une unit
				let uuid = unit.href.substring(unit.href.indexOf('/')+10, unit.href.length);
				uuid = uuid.substring(uuid.indexOf('/')+1, uuid.length);
				uuid = uuid.substring(uuid.indexOf('/')+1, uuid.length);
				
				chaineUnit = "<tr>";
				chaineUnit += "<td>" + compteur+ "</td>";		
				chaineUnit += "<td>" + image+ "</td>";		
				chaineUnit += "<td>"+ unit.name +"</td>";
				chaineUnit += "<td><a href='unit.html?"+uuid+"'>Voir</a></td>";             
				chaineUnit += "</tr>";
				compteur++;
				$("#unitsTable tbody").append(chaineUnit);
			}
           },
		   error: function(error)
		   {
			   // window.location.href="./error.html";
		   }
         });
   
   
   
    
    
});