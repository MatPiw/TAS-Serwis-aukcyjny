<?php

    class bootstrapController
    {
        private $view; // Smarty object


        public function __construct($_view)
        {
            $this->view = $_view;

        }

        public function init()
        {
            if ($_SESSION['messages'])
            {
                $arr = $_SESSION['messages'];
                $this->view->assign( 'message', $arr);
                unset($_SESSION['messages']);
            }

            // szukamy, czy zdefiniowany url ma by� odpalony z innym controlerem, akcj� i parametrami
            // np strona.pl/p/ksiazka-kucharska/ otwiera kontroler: produkt, akcj� poka� i parametr labelId=ksiazka-kucharska
            //$changeControllerAction  = $this->routingModel->getCAforUrl($_GET['controller']);
            if ($changeControllerAction != false)
            {
                $_GET['controller'] = $changeControllerAction['controller'];
                $_GET['action']     = $changeControllerAction['action'];
                $_GET['params']     = $changeControllerAction['params'];
            }

            if (isset($_GET['controller']))
            {
                if (file_exists(dirRoot.'appl_serwis/controllers/'.$_GET['controller'].'.controller.php'))
                {
                    $kontroler2load = $_GET['controller'].'Controller';
                    $kont = new ${kontroler2load}($this->view, $_GET['action'], $_GET['params'], $this->activeLanguage);
                    $this->indexType = $kont->getIndexType();
                    $this->index = $kont->getIndex();

                }
                else
                {
                    /*                    $static = new staticPagesController($this->view, $_GET['action'], $_GET['params']);
                                        $this->indexType = $static->getIndexType();
                                        $this->index = $static->getIndex();*/
                }
            }

            if($_GET['controller'] == 'index')
            {
                $this->processAuctions();
            }
        }

        private function processAuctions()
        {
            $file = 'http://localhost:8080/offers';
            $file_headers = @get_headers($file);
            if($file_headers[0] != 'HTTP/1.1 404 Not Found') {
                $json = file_get_contents($file);
                $obj = json_decode($json);

                $sliderArray=array();
                for($i=0; $i<3; $i++)
                {
                    array_push($sliderArray, $obj[$i]);
                }

                $nextArray=array();

                for($i=3; $i<9; $i++)
                {
                    array_push($nextArray, $obj[$i]);
                }
                $this->view->assign("offers", $nextArray);
                $this->view->assign("slider", $sliderArray);

            }
            $this->view->assign('inc_static', dirRoot.'appl_serwis/view/startAction.html');
        }

    }