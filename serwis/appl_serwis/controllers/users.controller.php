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

    public function registerAction()
    {
        if($_POST['login'])
        {
            $response=$this->registering();
            if($response == true)
            {
                $this->view->assign("message", "We sent confirmation link on your email.");
                $this->view->assign("inc_static", "users/loginAction.html");
                $this->loginAction();
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

    public function confirmAction()
    {
        $hashCode=$_GET['code'];
        $login=$_GET['login'];
        $file = 'http://localhost:8080/users/'.$login;
        $file_headers = @get_headers($file);
        if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
            $json = file_get_contents($file);
            $obj = json_decode($json);
            if($obj->{'login'} == $login && $hashCode == $obj->{'hashPassword'})
            {
                $this->view->assign("message", "YOur account has been created. You can log in now.");
                $this->view->assign("inc_static", "users/loginAction.html");
                $this->loginAction();
            }
            else
            {
                $this->view->assign("message", "A problem occoured with your confirmation link, please contact us for further instructions.");
                $this->view->assign("inc_static", "users/loginAction.html");
                $this->loginAction();
            }
        }
    }

    private function registering()
    {
        $json=array();
        $json['login']=$_POST['login'];
        $json['pass']=md5($_POST['pass']);
        $json['email']=$_POST['email'];
        $json['fname']=$_POST['fname'];
        $json['lname']=$_POST['lname'];
        $json['city']=$_POST['city'];
        $json['address']=$_POST['address'];
        $json['zipc']=$_POST['zipc'];
        $json['phone']=$_POST['phone'];

        $file = 'http://localhost:8080/register/'.json_encode($json);
        $file_headers = @get_headers($file);
        if($file_headers[0] != 'HTTP/1.1 404 Not Found')
        {
            $to = $_POST['email'];
            $subject = "Los Peneros Hermanos- Confirmation e-mail";

            $message = "
                        <html>
                        <head>
                        <title>Confirmation e-mail</title>
                        </head>
                        <body>
                        <p>Please click the following link to finish your registration in ur service!</p>
                        <table>
                        <tr>
                        <th>".dirWww."serwis/user/confirm/code:".md5($_POST['pass']).";login:".$_POST['login']."</th>
                        </tr>
                        <tr>
                        <td>Thank you for registration.</td>
                        <td>Los Peneros Hermanos Team</td>
                        </tr>
                        </table>
                        </body>
                        </html>
                        ";

            $headers = "MIME-Version: 1.0" . "\r\n";
            $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";
            $headers .= 'From: <noreply@lospeneroshermanos.com>' . "\r\n";

            mail($to,$subject,$message,$headers);
            return true;
        }
        else
        {
            return false;
        }
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