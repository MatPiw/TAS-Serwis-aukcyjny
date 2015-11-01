<?php

    class db_inst
    {
        private $con;			// przechowuje identyfikator polaczenia z baza
        private $res;			// przechowuje identyfikator ostatniego zapytania
        public $dbQueryRet;	//
        private $numRows;		//
        private $dbQuery;		//
        private $lastInsertId;	// identyfikator ostatnio włożonej krotki
        private $config;		//

        private $db_base;
        private $db_host;
        private $db_user;
        private $db_pass;

        private $field_type;
        private $field_name;
        private $field_val;

        private $instances_count;

        private $instances;
        private $instancesF;

        public $logq;

        public function __construct()
        {

            $this -> config 	= array();
            $this -> field_type = array();
            $this -> field_name = array();
            $this -> field_val	= array();
            $this -> instances	= array();
            $this -> instancesF = array();
            $this -> lastInsertId = array();
        }

        public function get_instance()
        {

            $_SESSION['logg'][] = 'Szukam wolnej instancji DB... razem aktualnych polaczen: ['.count($this -> instancesF).']'."<br />\n".$_SESSION['nl'];
            if ( $this -> instances_count == 0 )
            {
                $this -> instances_count = 1;
                return $this -> set_new_instance( 1 );
            }
            else
            {
                for ( $i = 1; $i <= $this -> instances_count; $i++ )
                {
                    $_SESSION['logg'][] = "INST: ".$i.": ".var_export($this -> instancesF[$i], true).$_SESSION['nl'];
                    if ( $this -> instancesF[$i] == 1 )
                    {
                        $_SESSION['logg'][] = "\tJest wolna o nr: ".$i.') '.$_SESSION['nl'];
                        $this -> instancesF[$i] = -1;
                        return $i;
                    }
                }
            }
            return $this -> set_new_instance( $this -> instances_count  );
        }

        public function set_new_instance( $ile )
        {
            //$ile = count( $this -> instancesF );
            $_SESSION['logg'][] = 'Ustanawiam nowa instancje DB ['.$ile.']: <BR>'."\n".$_SESSION['nl'];

            $this -> instances[$ile ] = $this -> db_connect( $ile, dbName, dbHost, dbUser, dbPass );

            $this -> instancesF[$ile ] = -1;
            $this -> instances_count = $this -> instances_count + 1;

            return $ile;
        }

        public function db_connect( $inst, $dbBase = '', $dbHost = '', $dbUser = '', $dbPass = '' )
        {
            if ( $dbHost != '' ) $this -> db_host = $dbHost;
            if ( $dbBase != '' ) $this -> db_base = $dbBase;
            if ( $dbUser != '' ) $this -> db_user = $dbUser;
            if ( $dbPass != '' ) $this -> db_pass = $dbPass;

            $this -> instances[$inst] = mysqli_connect($this -> db_host, $this -> db_user, $this -> db_pass, $this->db_base);

            //mysqli_select_db($this -> db_base, $this -> instances[$inst] );
            //mysqli_query( 'SET NAMES UTF8', $this -> instances[$inst] );
            mysqli_query($this->instances[$inst], 'SET_NAMES UTF8');
            if ( $this -> instances[$inst] == 0 || $this -> instances[$inst] == - 1 )
                $this -> config[ 'all_ok' ] = false;

            $_SESSION['logg'][] = 'Class.db connected('.$inst.', '.$this -> db_host.'
                , '.$this -> db_user.', ***, '.$this -> db_base.')'.$_SESSION['nl'];
            $this -> instancesF[$inst] = -1;
            return $this -> instances[$inst];
        }

        public function set_option( $key, $val )
        {
            $this -> config[ $key ] = $val;
        }

        public function get_option( $key )
        {
            return $this -> config[ $key ];
        }

        public function retVar()
        {
            echo "Host: ".$this -> db_host.'<br />';
            echo "Login: ".$this -> db_user.'<br />';
            echo "Pass: ".$this -> db_pass.'<br />';
            echo "DB: ".$this -> db_base.'<br />';
            echo "CON: ".$this -> con.'<Br />';
            echo "<BR>";
        }

        public function dbQuery( $inst, $query, $last = 0 )
        {
            mysqli_query($this -> instances[$inst],  'SET NAMES UTF8' );
            $this -> logq .= $query."\n";
            $this -> dbQuery = $query;
            if ($this -> instances[$inst] == '')
            {
                return -1;
            }
            $_SESSION['logg'][] = 'Zapytanie: '.$query.' w instancji: '.$inst.$_SESSION['nl'];
            $this -> dbQueryRet[$inst] = mysqli_query($this -> instances[$inst],  $query );
            if ($last == 'LAST_INSERT_ID')
            {
                $this -> lastInsertId[$inst] = mysqli_insert_id( $this -> instances[$inst] );
            }

            if (mysqli_error($this -> instances[$inst]))
            {
                $this -> config[ 'all_ok' ] = false;
                if ( $this -> config['send_errors'] == 'true' )
                {
                    $fail = new fail();
                    $fail -> fill_data('Query: ', $query);
                    $fail -> fill_data('Error: ', mysql_error($this -> instances[$inst] ));
                    $fail -> send();
                }

                if ( $this -> config['debug'] == 'true' )
                {
                    echo "<BR><BR>Query: ".$query;
                    echo "<BR><BR>";
                    echo mysql_error($this -> instances[$inst]);
                    echo "<BR><BR>";
                }
                return -1;
            }

            return $this -> dbQueryRet[$inst];
        }

        public function dbAffectedRows( $inst )
        {
            return mysqli_affected_rows( $this -> instances[$inst] );
        }

        public function dbNumRows( $inst )
        {
            //echo $inst;

            //echo $this -> dbQuery.'<br />';
            if ( $this -> dbQueryRet[$inst] ) // zmiana mogąca robić problemy?!?
            {
                $ret = mysqli_num_rows( $this -> dbQueryRet[$inst] );
                return $ret;
            }
            else
                return -1;
        }

        public function dbRetRow( $inst )
        {
            //echo $this -> dbQuery."<br />\n";
            //var_dump(mysql_fetch_array( $this -> dbQueryRet[$inst] ));
            return mysqli_fetch_array($this -> dbQueryRet[$inst]);
        }

        // ot alias a'la symfony ;-)
        public function fetchAll($inst)
        {
            return $this->dbGetArr($inst);
        }

        public function fetchAllInc($inst)
        {
            return $this->dbGetArrInc($inst);
        }

        public function fetchOne($inst)
        {
            while($r = mysqli_fetch_array($this->dbQueryRet[$inst]))
            {
                while(list($k,$v) = each($r))
                {
                    if ( !is_int($k))
                    {
                        $tab[$k] = $v;
                    }
                }
                return $tab;
            }

            return false;
        }

        public function dbGetArr($inst)
        {
            //echo $inst;
            while($r=mysqli_fetch_array($this->dbQueryRet[$inst]))
            {
                $i=0;
                while(list($k,$v) = each($r))
                {
                    if($i == 1)
                        $key = $v;

                    if ( !is_int($k))
                    {
                        $tab[$key][$k] = $v;

                    }
                    $i++;
                }
            }

            return $tab;
        }

        public function dbGetArrInc($inst)
        {
            $lp = 0;
            while($r = mysqli_fetch_array($this->dbQueryRet[$inst]))
            {
                $i=0;

                while(list($k,$v) = each($r))
                {
                    if ($i == 1)
                        $key = $v;

                    if (!is_int($k))
                    {
                        $tab[$lp][$k] = $v;
                    }
                    $i++;
                }
                $lp++;
            }

            return $tab;
        }


        public function dbGetResult($inst)
        {
            $tab=array();
            while($r=mysqli_fetch_array($this->dbQueryRet[$inst],MYSQLI_ASSOC))
            {
                array_push($tab,$r);
            }
            return $tab;
        }

        public function dbLastInsertId( $inst )
        {
            return $this -> lastInsertId[$inst];
        }

        public function dbNumFields( $inst )
        {
            if ( $this -> dbQueryRet[$in] )
                return
                    mysqli_num_fields($this -> dbQueryRet[$inst]);
            else
                return false;
        }

        public function dbFieldName( $inst, $i )
        {
            //return mysqli_fieldname($this -> dbQueryRet[$inst], $i);
        }

        public function AddSql($inst, $s_FieldType, $s_FieldName , $s_FieldValue, $dataArr = false)
        {
            if ($s_FieldName=='')
                return false;

            if (!isset( $this -> field_type[$inst]))
            {
                $this -> field_type[$inst]	= array();
                $this -> field_name[$inst]	= array();
                $this -> field_val[$inst]	= array();
            }

            if($dataArr)
            {
                foreach($dataArr as $columnName=> $value)
                {
                    array_push( $this -> field_type[$inst], 	'TXT');
                    array_push( $this -> field_name[$inst], 	$columnName);
                    array_push( $this -> field_val[$inst], 		mysqli_real_escape_string($this -> instances[$inst], $value));
                }
            }
            else
            {
                switch ($s_FieldType)
                {
                    case "TXT":
                        array_push( $this -> field_type[$inst], 	$s_FieldType );
                        array_push( $this -> field_name[$inst], 	$s_FieldName );
                        //echo $s_FieldName.': ';
                        //echo var_export($s_FieldValue).'<br >';
                        array_push( $this -> field_val[$inst], 		mysqli_real_escape_string($this -> instances[$inst], $s_FieldValue));
                        break;
                    case "NUM":
                        if ( $s_FieldValue == "" )
                            $s_FieldValue = "0";
                        array_push( $this -> field_type[$inst], 	$s_FieldType );
                        array_push( $this -> field_name[$inst], 	$s_FieldName );
                        array_push( $this -> field_val[$inst],		str_replace(",",".",$s_FieldValue ) );
                        break;
                };
            }

        }
        public function GenerateSql( $inst, $QueryType, $s_TableName , $s_WhereSql="" )
        {
            $TmpSqlF = '';
            $TmpSqlV = '';
            $counted_fields = count( $this -> field_name[$inst] );

            switch ($QueryType)
            {
                Case "INS":
                    for ( $k = 0; $k <= $counted_fields; $k++ )
                    {
                        if (isset( $this -> field_name[$inst][$k] ) )
                        {
                            $TmpSqlF = $TmpSqlF . $this -> field_name[$inst][$k].",";

                            switch ( $this-> field_type[$inst][$k] )
                            {
                                case "TXT":
                                    $TmpSqlV .= "'" . $this -> field_val[$inst][$k]."',";
                                    break;
                                case "NUM":
                                    $TmpSqlV .= $this -> field_val[$inst][$k].",";
                                    break;
                            }
                        }
                    }
                    $this -> ClearSql( $inst );
                    return "INSERT INTO ".$s_TableName." (".substr($TmpSqlF, 0,-1).") VALUES (".substr($TmpSqlV, 0,-1).") ".$s_WhereSql;
                    break;

                case "UPD":
                    for ($k = 0; $k <= $counted_fields; $k++ )
                    {
                        if (isset( $this -> field_type[$inst][$k] ) )
                        {
                            switch ( $this -> field_type[$inst][$k] )
                            {
                                Case "TXT":
                                    $TmpSqlV .= $this -> field_name[$inst][$k]."='".$this -> field_val[$inst][$k]."',";
                                    break;
                                Case "NUM":
                                    $TmpSqlV .=  $this-> field_name[$inst][$k]."=".$this-> field_val[$inst][$k].",";
                                    break;
                            }
                        }
                    }
                    $this->ClearSql( $inst );
                    return  "UPDATE ".$s_TableName." SET ".substr($TmpSqlV, 0,  - 1)." ".$s_WhereSql;
                    break;
            }

        }

        private function ClearSql( $inst )
        {
            unset( $this-> field_type[$inst] );
            unset( $this-> field_name[$inst] );
            unset( $this-> field_val[$inst] );
            $this -> field_type[$inst]	= array();
            $this -> field_name[$inst]	= array();
            $this -> field_val[$inst]	= array();
        }

        public function dbClose( $inst )
        {
            //mysql_close($this -> instances[$inst]);
            $this -> instancesF[$inst] = 1;
            $_SESSION['logg'][] = 'Zwalniam instancje nr '.$inst.$_SESSION['nl'];

        }

        public function free_instance( $inst )
        {
            $this -> dbClose( $inst );
        }
    }

