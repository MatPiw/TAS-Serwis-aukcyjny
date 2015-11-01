<?php

class usersController extends controller
{
    public $view;
    public $params;


    public function __construct(Smarty $_view, $_action, $_params)
    {
        $this->view = $_view;
        $this->params = new params();


        parent::__construct($_view, $_action, $_params, '_serwis');
    }

    public function loginAction($_pars)
    {
        
		if($_POST['login'])
		{
			$response=$this->logging();
			if($response == true)
			{
				$this->view->assign("message", "Zostałeś zalogowany.");
			}
			else
			{
				$this->view->assign("message", "Niepoprawne dane logowania.");
			}
		}
    }

    private function logging()
	{
		$login=$_POST['login'];
		$pass=$_POST['pass'];
		
		$json = file_get_contents('127.0.01/login/'.$login.'/'.$pass.'/');
		$obj = json_decode($json);
		if($json['response'] == true)
		{
			$_SESSION['logged']=true;
			return true;
		}
		else
		{
			$_SESSION['logged']=false;
			return false;
		}
	}
}