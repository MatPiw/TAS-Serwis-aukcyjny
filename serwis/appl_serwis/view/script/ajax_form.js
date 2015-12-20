$(function() {
    $("button#submit").click(function() {
      var login = $('#login').val();
      var password = $('#password').val();
      var dataString = 'login='+ login + '&password=' + password;

      $.ajax({
        type: 'POST',
        url: 'skrypt.php',
        data: dataString,
        success: function(data) {
            if( data == '0' )
              $("div#ack").html("Wpisz login i hasło");
            else
              window.location = window.location;
            }
        });
    });
});
