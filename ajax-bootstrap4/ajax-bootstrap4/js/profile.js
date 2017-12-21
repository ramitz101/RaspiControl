const SERVICE_URL= "https://andromia-maxroy1993.c9users.io/explorateur"


$(document).ready(function(){
   
   $("#logOut").click(function(){
	  sessionStorage.setItem("Authorization", ""); 
   });
   
   $.ajax({
           type: "GET",
           url: SERVICE_URL,
		   headers: {"Authorization": "bearer " + sessionStorage.getItem("Authorization") },
           success: function(explorateur)
           {
			   
				$("#locationHead").append(explorateur.location);
				$("#inoxHead").append(explorateur.inox);
				$("#pseudoHead").append(explorateur.pseudonyme);
				$("#profilePerso").append(" "+explorateur.pseudonyme);
				$("#profileCourriel").append(" "+explorateur.courriel);
				
				
           },
		   error: function(error)
		   {
			  // window.location.href="./error.html";
		   }
         });
   
    
    
});