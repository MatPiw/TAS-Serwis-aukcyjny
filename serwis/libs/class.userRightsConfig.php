<?php
    class userRightsConfig
    {
        public function parseUserRightsConfig()
        {
            $config = array();

            $handle = @fopen(dirRoot.'appl/config/user-rights-modules-actions.txt', "r");
            if ($handle)
            {
                $i = -1;
                $j = 0;
                while (($buffer = fgets($handle)) !== false)
                {
                    if ($buffer[0] == "#")
                    {
                        array_push($config,array('groupName' => substr($buffer, 1), 'config' => array()));
                        $i++;
                    }
                    else
                    {
                        if ($buffer[0] != "#" && $buffer[0] != "/" && $buffer[1] != "/" && $i != -1)
                        {
                            $bufferParts = explode("/",str_replace("\r\n",'', $buffer));
                            foreach($bufferParts as $k=>$bufferPart)
                            {
                                $bufferSmallParts = explode(";", $bufferPart);
                                if ($k == 0)
                                {
                                    $config[$i]['config'][$j]['userModulName']   = $bufferSmallParts[0];
                                    $config[$i]['config'][$j]['systemModulName'] = $bufferSmallParts[1];
                                }

                                if ($k == 1)
                                {
                                    $config[$i]['config'][$j]['userControllerName']   = $bufferSmallParts[0];
                                    $config[$i]['config'][$j]['systemControllerName'] = $bufferSmallParts[1];
                                }

                                if ($k == 2)
                                {
                                    $config[$i]['config'][$j]['userActionName']   = $bufferSmallParts[0];
                                    $config[$i]['config'][$j]['systemActionName'] = $bufferSmallParts[1];
                                }
                            }
                            $j++;
                        }
                    }
                }
                fclose($handle);
            }

            return $config;
        }
    }
