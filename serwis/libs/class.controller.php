<?php

/*
    This class don't check for permissions (there is a second class controller
    on admin/libs/  that has this option enabled
 */

	abstract class controller
	{
		public $view;

        public $index;
        public $indexType;

		public function __construct(&$view, $action = 'noactionAction', $params = '', $modul = '')
		{
			$this->view = $view;

			$_SESSION['logg'][] = get_called_class()." checking for: ".$action.'Action';
			if (method_exists($this, $action.'Action'))
			{
				$_SESSION['logg'][] = 'Odpalono '.get_called_class().'->'.$action.'Action';
                $action .= 'Action';
                if (file_exists(dirRoot.'appl'.$modul.'/view/'.$_GET['controller'].'/'.$action.'.html'))
                {
				    $this->view->assign('inc_static', 'appl'.$modul.'/view/'.$_GET['controller'].'/'.$action.'.html');
                    //$this->view->assign('inc_static_module', $_GET['controller'].'/'.$action.'.html');
                }

                $this->$action($params);
			}
			else
			{
				$_SESSION['logg'][] = 'nie znaleziono '.$action.', odpalono '.get_called_class().'->defaultAction';
                $action = 'defaultAction';
                if ( method_exists($this, $action))
                    $this->$action($params);
                else
                {
                    echo "Controller ".get_called_class().' nie ma akcji default';
                }
            }
		}

        public function setIndexType($_indexType)
        {
            $this->indexType = $_indexType;
        }

        public function getIndexType()
        {
            return $this->indexType;
        }

        public function setIndex($_index)
        {
            $this->index = $_index;
        }

        public function getIndex()
        {
            return $this->index;
        }

        public function setErrorMsg($_msg)
        {
            $_SESSION['showErrorOnReload'] = $_msg;
        }

        public function setMessageMsg($_msg)
        {
            $_SESSION['showMessageOnReload'] = $_msg;
        }

	}
