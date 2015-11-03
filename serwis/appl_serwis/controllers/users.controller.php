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

    public function loginAction()
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

    public function logoutAction()
    {
        session_unset();
        $this->view->assign("message", "Zostałeś wylogowany.");
        $this->view->assign("inc_static", "users/loginAction.html");
        $this->loginAction();

    }

    private function logging()
	{
		$login=$_POST['login'];
        $password=md5($_POST['pass']);
		$file = 'http://localhost:8080/users/'.$login;
		$file_headers = @get_headers($file);
		if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
			$json = file_get_contents($file);
			$obj = json_decode($json);
            if($obj->{'login'} == $login && $password == $obj->{'hashPassword'})
            {
                $_SESSION['logged']=true;
                //$_SESSION['userId']=$obj->{'id'};
                $_SESSION['firstName']=$obj->{'firstName'};
                return true;
            }
            else
            {
                $_SESSION['logged']=false;
                return false;
            }
		}
		else {
			$_SESSION['logged']=false;
			return false;
		}

	}
}