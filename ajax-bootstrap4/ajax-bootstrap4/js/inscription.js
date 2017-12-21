const url = "https://andromia-maxroy1993.c9users.io/inscription"



$(function() {
  $('form').submit(function(e) {
      	var obj = {};
		var elements = this.querySelectorAll( "input, select, textarea" );
		for( var i = 0; i < elements.length; ++i ) {
			var element = elements[i];
			var name = element.name;
			var value = element.value;

			if( name ) {
				obj[ name ] = value;
			}
		}
		var data = JSON.stringify( obj );
		
		
		  $.ajax({
           type: "POST",
           url: url,
           data: data, // serializes the form's elements.
		   contentType: 'application/json; charset=utf-8',
           success: function(d)
           {
               window.location.href="./login.html";
           },
		   error: function(x,e)
		   {
			  window.location.href="./login.html";
		   }
         });

		e.preventDefault(); 
		 
		
		
  });
});
