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

    public function addOfferAction()
    {

      $userLogin=$_SESSION['userLogin'];
      $profileObject= $this->getUserInfo($userLogin);
      $this->view->assign('profileData', $profileObject);

        if($_POST['logino']){
              $response=$this->newOffer();
			  $this->view->assign("msg2", $response);
              if($response != 'HTTP/1.1 404 Not Found')
              {
                  $this->view->assign("message", "Dodałeś aukcje.");
                  $this->view->assign("inc_static", "users/addOfferAction.html");
				/*$this->view->assign("inc_static", "users/viewUserAction.html");*/
                  $this->viewUserAction('login:'.$_SESSION['userLogin']);
              }
              else
              {
                  $this->view->assign("message", "Niepoprawne dane.");
                  $this->view->assign("inc_static", "users/addOfferAction.html");
                  $this->addOfferAction();
              }
        }


    }

    public function viewOfferAction($_pars)
    {
        $params=$this->params->getParams($_pars);
        $offerId=$params['id'];
        $file = 'http://localhost:8080/offers/'.$offerId;
        $file_headers = @get_headers($file);
        if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
            $json = file_get_contents($file);
            $obj = json_decode($json);

            $this->view->assign("offer", $obj);

        }

    }

    private function newOffer()
    {

        $json=array();
        $json['title']=$_POST['title'];
        $json['description']=$_POST['description'];
        $json['picture_path']=$_POST['picturePath'];
        $json['owner_id']=$_POST['logino'];
        $json['buy_now_price']=$_POST['buyNowPrice'];
		$json['minimal_price']=$_POST['minPrice'];
		$json['weight']=$_POST['weight'];
		$json['size']=$_POST['size'];
		$json['shipment']=$_POST['shipment'];
		$json['category']=$_POST['category'];
		
		
        $uri= 'http://localhost:8080/offers/';
        $sendJson=json_encode($json);
        $response = \Httpful\Request::post($uri)
            ->sendsJson()
            ->body($sendJson)
            ->send();
        return $response;

    }


    public function viewUserAction($_pars)
    {
        $params=$this->params->getParams($_pars);
        $userLogin=$params['login'];
        $profileObject= $this->getUserInfo($userLogin);
        $this->view->assign('profileData', $profileObject);
        $this->view->assign('id', $userLogin);
    }

    public function updateUserAction($_pars)
    {
        $params=$this->params->getParams($_pars);
        $userLogin=$params['login'];
        $profileObject= $this->getUserInfo($userLogin);
        $this->view->assign('profileData', $profileObject);
        $this->view->assign('id', $userLogin);

        if($_POST['loginu'])
        {
            $response=$this->updatingData();
            if($response == true)
            {
                $this->view->assign("message", "Zaktualizowałeś dane");
                $this->view->assign("inc_static", "users/viewUserAction.html");
                $this->viewUserAction('login:'.$_SESSION['userLogin']);
            }
            else
            {
              $this->view->assign("message", "Wystąpił Błąd");
              $this->view->assign("inc_static", "users/viewUserAction.html");
                $this->viewUserAction('login:'.$_SESSION['userLogin']);
            }
        }

    }

    public function sendBidAction()
    {
        $price=$_POST['price'];
        $bidderId=$_SESSION['userId'];
        $offerId=$_POST['offerId'];

        if($bidderId != '' && $bidderId != NULL && isset($bidderId))
        {
            $send=$this->bidder($bidderId, $offerId, $price);
            if($send == true)
            {
                $this->view->assign("message", "Wystąpił Błąd");
            }
            else
            {
                $this->view->assign("message", "Wystąpił Błąd");
            }


        }
        $this->view->assign("inc_static", "users/viewOfferAction.html");
        $this->viewOfferAction('id:'.$offerId);
    }

    public function searchAction()
    {
        $keyword=$_POST['keywords'];

        $file = 'http://localhost:8080/offers?keyword='.$keyword;
        $file_headers = @get_headers($file);
        if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
            $json = file_get_contents($file);
            $obj = json_decode($json);
            $nextArray=array();
            for($i=0; $i<count($obj); $i++)
            {
                array_push($nextArray, $obj[$i]);
            }
            $this->view->assign("offers", $nextArray);
        }

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
        if($_POST['loginr'])
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
                $this->view->assign("message", "Your account has been created. You can log in now.");
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


    private function bidder($bidderId, $offerId, $price)
    {
        $json=array();
        $json['bidderId']=$bidderId;
        $json['offerId']=$offerId;
        $json['price']=$price;

        $uri= 'http://localhost:8080/bids/';
        $sendJson=json_encode($json);
        $response = \Httpful\Request::post($uri)
            ->sendsJson()
            ->body($sendJson)
            ->send();

        return $response;
    }
    private function getUserInfo($_login)
    {
        $file = 'http://localhost:8080/users/'.$_login;
        $file_headers = @get_headers($file);
        if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
            $json = file_get_contents($file);
            $obj = json_decode($json);
            return $obj;
        }

    }
    private function updatingData()
    {
      if($_POST['loginu']){
        $json=array();

        $json['firstName']=$_POST['fname'];
        $json['lastName']=$_POST['lname'];
        $json['login']=$_POST['loginu'];
        $json['email']=$_POST['email'];
        $json['city']=$_POST['city'];
        $json['address']=$_POST['address'];
        $json['zipCode']=$_POST['zipc'];
        $json['phone']=$_POST['phone'];

        $uri= 'http://localhost:8080/users/'.$_POST['loginu'];
        $sendJson=json_encode($json);
        $response = \Httpful\Request::put($uri)
            ->sendsJson()                               // tell it we're sending (Content-Type) JSON...
            //->authenticateWith('username', 'password')  // authenticate with basic auth...
            ->body($sendJson)             // attach a body/payload...
            ->send();


      }

    }



    private function registering()
    {
        $json=array();
        $json['firstName']=$_POST['fname'];
        $json['lastName']=$_POST['lname'];
        $json['permissions']=1;
        $json['login']=$_POST['loginr'];
        $json['hashPassword']=md5($_POST['pass']);
        $json['email']=$_POST['email'];
        $json['confirmed']=0;
        $json['city']=$_POST['city'];
        $json['address']=$_POST['address'];
        $json['zipCode']=$_POST['zipc'];
        $json['phone']=$_POST['phone'];
        $json['createdAt']=date('Y-m-d', time());

        $uri= 'http://localhost:8080/users/';
        $sendJson=json_encode($json);
        $response = \Httpful\Request::post($uri)
            ->sendsJson()
            ->body($sendJson)
            ->send();

        if($response != 'HTTP/1.1 404 Not Found')
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
                $_SESSION['userLogin']=$obj->{'login'};
                $_SESSION['firstName']=$obj->{'firstName'};
                $_SESSION['userId']=$obj->{'id'};
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
