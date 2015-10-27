$("button#submit").click( function(){

  if( $("$username").val() == "" || $("$password").val() == "" )
    $("div#ack").html("Wpisz login i hasło")

  else
    $.post( $("#logowanie").attr("action"),
            $("#logowanie :input").serializeArray(),
            function(data) {
              $("div#ack").html(data);
            });

            $("#logowanie").submit( function() {

              return false;

            })


});
