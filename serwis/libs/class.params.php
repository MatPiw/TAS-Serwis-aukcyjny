<?php

    class params
    {
        /*
         @WE: $in = parametry, string (nazwa1:val1;nazwa2:val2}
         @WY: tablica ('nazwa1'=>'val1', 'nazwa2'=>'val2')
         */
        public function getParams($in)
        {
            $params = array();
            $tmp = explode(';', $in);

            for($i=0;$i<count($tmp);$i++)
            {
                $z = explode(":", $tmp[$i]);
                $params[$z[0]] = $z[1];
            }

            return $params;
        }

        /*
         @WE: $in - tablica ('param' = 'val', 'param2'=> 'val'
         @WY: String do wrzucenia jako parametry
         */
        public function setParams($in)
        {
            while(list($k, $v) = each($in))
            {
                $out[] = $k.':'.$v;
            }

            $output = implode(';', $out);

            return $output;
        }
    }

