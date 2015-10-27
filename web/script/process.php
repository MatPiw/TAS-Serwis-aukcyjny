< ?php
      include_once('db.php');
 
	  $login = mysql_real_escape_string( $_POST["login"] );
	  $password = mysql_real_escape_string( md5($_POST["pass"]) );
	  $fname = mysql_real_escape_string( $_POST["fname"] );
	  $lname = mysql_real_escape_string( $_POST["lname"] );
 
 
 
 //Tu uzupe³niæ o wszystkie dane :P
 
 
	  $sql = "INSERT INTO users VALUES('',
                                           '$username', 
                                           '$password', 
                                           '$fname', 
                                           '$lname')";
	  if( mysql_query($sql) )
	   echo "Inserted Successfully";
	  else
	   echo "Insertion Failed";
?>