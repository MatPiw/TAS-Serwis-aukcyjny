<?php

    set_include_path(
        get_include_path()
        . PATH_SEPARATOR . "./appl/controllers/"
        . PATH_SEPARATOR . "./libs/"
        . PATH_SEPARATOR . "./appl_backend/controllers/"
    );

    function __autoloadControllers($class_name)
    {
        if ($class_name == 'Smarty' || $class_name == 'Smarty_Internal_Data')
        {
            return false;
        }
        else
        {
            if (file_exists(dirRoot . 'appl/controllers/' . $class_name . '.php'))
            {
                include(dirRoot . 'appl/controllers/' . $class_name . '.php');
            }
        }

        return true;
    }


    function __autoloadSerwisControllers($class_name)
    {

        if ($class_name == 'Smarty' || $class_name == 'Smarty_Internal_Data')
        {
            return false;
        }
        else
        {
            if (file_exists(dirRoot . 'appl_serwis/controllers/' . $class_name . '.php'))
            {
                include(dirRoot . 'appl_serwis/controllers/' . $class_name . '.php');
            }
        }

        return true;
    }

    function __autoloadLibs($class_name)
    {
        if ($class_name == 'Smarty' || $class_name == 'Smarty_Internal_Data')
        {
            return false;
        }
        else
        {
            if (file_exists(dirRoot . 'libs/class.' . strtolower($class_name) . '.php'))
            {
                include(dirRoot . 'libs/class.' . strtolower($class_name) . '.php');
            }
            else
            {
                if (file_exists(dirRoot . 'libs/class.' . $class_name . '.php'))
                {
                    include(dirRoot . 'libs/class.' . $class_name . '.php');
                }
            }
        }

        return true;
    }

    function __autoloadModels($class_name)
    {
        if ($class_name == 'Smarty' || $class_name == 'Smarty_Internal_Data')
        {
            return false;
        }
        else
        {
            if (file_exists(dirRoot . 'models/' . str_replace('Model', '', $class_name) . '.model.php'))
            {
                include(dirRoot . 'models/' . str_replace('Model', '', $class_name) . '.model.php');
            }
        }

        return true;
    }



    spl_autoload_register('__autoloadLibs');
    spl_autoload_register('__autoloadModels');

