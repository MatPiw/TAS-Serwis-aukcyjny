<?php
          include_once('db.php');

          $username = mysql_real_escape_string( $_POST["login"] );
          $password = mysql_real_escape_string( md5($_POST["password"]) );

          if(empty($username) || empty($password))
            echo "Login i hasło obowiązkowe";
          else {
          $sql = "SELECT count(*) FROM users WHERE(username='$username' AND password='$password')";
          $res = mysql_query($sql);
          $row = mysql_fetch_array($res);

          if( $row[0] > 0)
            echo "Logowanie poprawne";
          else
            echo "Logowanie błedne";

          }

 ?>



 /*            ajax
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
 }); */
