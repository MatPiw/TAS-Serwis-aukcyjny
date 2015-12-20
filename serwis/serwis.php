<?php

    session_start();
    date_default_timezone_set('Europe/Warsaw');

    ini_set('display_errors', '1');
    error_reporting(E_ALL^E_NOTICE);

    include('config/config.php');
    include(dirRoot . 'libs/httpful.phar');
    include(dirRoot . 'libs/class.controller.php');
    include(dirRoot . 'libs/smarty-3/libs/Smarty.class.php');
    include(dirRoot . 'libs/class.userRightsConfig.php');
    include(dirRoot . 'libs/class.sql.instances.php');
    include(dirRoot . 'libs/class.form.php');
    include(dirRoot . 'libs/class.logger.php');
    include(dirRoot . 'appl_serwis/controllers/bootstrap.controller.php');

    $_GET['controller'] = $_GET['s'];
    $_GET['action']     = $_GET['s2'];
    $_GET['params']     = $_GET['s3'];

    include('autoload.php');
    spl_autoload_register('__autoloadSerwisControllers');

    $view = new Smarty();
    $view -> debugging		= false;
    $view -> force_compile	= true;
    $view -> caching		= false;
    $view -> compile_check	= true;
    $view -> cache_lifetime	= 120;
    $view -> template_dir	= 'appl_serwis/view';
    $view -> compile_dir	= 'tmp/compiled';
    $view -> plugins_dir	= array( SMARTY_DIR . 'plugins',  'resources/plugins', 'appl_serwis/view/smartyFunctions' );

    if ($_GET['controller'] == '')
        $_GET['controller'] = 'index';

    $bootstrap = new bootstrapController($view);
    $bootstrap->init();

    if ($_SESSION['messages'])
    {
        $arr = $_SESSION['messages'];
        $this->view->assign('message', $arr);
        unset($_SESSION['messages']);
    }

    $view->assign('dirWww', dirWww);
    $view->assign('s',  $_GET['controller']);
    $view->assign('s2', $_GET['action']);
    $view->assign('email', $_SESSION['email']);


        $index = $view->fetch('index.html');

    echo $index;