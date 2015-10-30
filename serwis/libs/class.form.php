<?php

    /*
        zajebista klasa formularza
        By Michał Kabziński
        michal@kabzinski.net
        Copyrights Maretto Michał Kabziński
    */


    class formGenerator
    {
        public $view;

        private $formType; // GET or POST
        private $formName;
        private $formAction;
        private $forms;
        private $formStructure;

        public function __construct(Smarty $_view)
        {
            $this->view         = $_view;
            $this->forms   = array();
        }

        public function setFormType($_type, $_formAction = '')
        {
            $this->formType     = $_type;
            $this->formAction   = $_formAction;
        }

        public function getFormType()
        {
            return $this->formType;
        }

        public function setFormName($_name)
        {
            $this->formName = $_name;
        }

        public function getFormName()
        {
            return $this->formName;
        }

        public function setFormField($_fieldType, $_fieldName, array $_fieldParams = array(), $_fieldConfig = array())
        {
            $params = '';

            while(list($k, $v) = each($_fieldParams))
            {
                $params .= ' '.$k.'="'.$v.'"';
            }

            $field['name']      = $_fieldName;
            $field['params']    = $params;

            if ($_fieldConfig['label'])
                while(list($k, $v) = each($_fieldConfig['label']))
                    $this->forms['form'][$this->formName]['fields'][$_fieldName]['label'][$k]  = $v;

            if ($_fieldConfig['required'])
                $this->forms['form'][$this->formName]['fields'][$_fieldName]['required'] = $_fieldConfig['required'];

            if ($_fieldConfig['errorMsg'])
                $this->forms['form'][$this->formName]['fields'][$_fieldName]['errorMsg'] = $_fieldConfig['errorMsg'];

            //$this->formFields[$this->formName]['fields'][$_fieldName] = $field;

            switch($_fieldType)
            {
                case 'input':
                    $this->forms['form'][$this->formName]['fields'][$_fieldName]['html'] = '<input name="'.$_fieldName.'"'.$params." />";
                    break;

                case 'select':
                    if ($_fieldParams['placeholder'])
                    {
                        $options = '<option value="">'.$_fieldParams['placeholder'].'</option>';
                    }
                    else
                    {
                        $options = '<option value=""></option>';
                    }

                    if($_fieldConfig['options'])
                    {
                       foreach($_fieldConfig['options'] as $opt)
                       {
                           $options.='<option value="'.$opt['value'].'">'.$opt['name'].'</option>';
                       }
                    }

                    $this->forms['form'][$this->formName]['fields'][$_fieldName]['html'] = '<select name="'.$_fieldName.'"'.$params." >".$options."</select>";
                    break;

                case 'textarea':
                    break;
            }


        }

        public function check($_formName)
        {
            if (isset($_POST['formName']) && $_POST['formName']==$_formName)
            {
                $allOk = true;

                while(list($k, $v) = each($_POST))
                {
                    if ($this->forms['form'][$_formName]['fields'][$k]['required']=='true')
                    {
                        if ($v=='')
                        {
                            $this->forms['form'][$_formName]['fields'][$k]['is_error'] = 1;

                            $allOk = false;
                        }
                    }

                    if ($this->forms['form'][$_formName]['fields'][$k]['max'])
                    {
                        if (strlen($v)>$this->forms['form'][$_formName]['fields'][$k]['max'])
                        {
                            $this->forms['form'][$_formName]['fields'][$k]['is_error'] = 1;
                            $this->forms['form'][$_formName]['fields'][$k]['errorMsg'] .= '. Długość nie może być większa niż: '.$this->forms['form'][$_formName]['fields'][$k]['max'];
                            $allOk = false;
                        }

                    }

                    if ($this->forms['form'][$_formName]['fields'][$k]['min'])
                    {
                        if (strlen($v)<$this->forms['form'][$_formName]['fields'][$k]['min'])
                        {
                            $this->forms['form'][$_formName]['fields'][$k]['is_error'] = 1;
                            $this->forms['form'][$_formName]['fields'][$k]['errorMsg'] .= '. Długość musi być większa niż: '.$this->forms['form'][$_formName]['fields'][$k]['min'];
                            $allOk = false;
                        }
                    }

                    if ($this->forms['form'][$_formName]['fields'][$k]['length'])
                    {
                        if (strlen($v)!=$this->forms['form'][$_formName]['fields'][$k]['length'])
                        {
                            $this->forms['form'][$_formName]['fields'][$k]['is_error'] = 1;
                            $this->forms['form'][$_formName]['fields'][$k]['errorMsg'] .= '. Niepoprawna długość';
                            $allOk = false;
                        }
                    }


                }


                if ($allOk == true)
                    return true;
                else
                {
                    $this->view->assign('errors', 'Wystąpiły błędy w formularzu');
                    return false;
                }

            }
            else
                return false;
        }

        public function build($_formName)
        {
            //var_export($this->forms);

            $this->forms['form'][$this->formName]['fullForm'] = $this->buildFullForm($_formName);
            $this->view->assign($this->forms['form'][$this->formName]);

            $this->view->assign('formFields', $this->forms['form'][$_formName]['fields']);

        }

        private function buildFullForm($_formName)
        {
            $form = '
                <form action="" method="'.$this->formType.'" name="'.$_formName.'" id="'.$_formName.'">
                <input type="hidden" name="formName" value="'.$_formName.'" />
            ';

            while(list($k, $v) = each($this->forms['form'][$_formName]['fields']))
            {
                //echo "\n\n\n\n\n";
                //var_export($this->forms['form'][$_formName]['fields'][$k]);
                $form .= '';
            }

            $form .='</form>';

            return $form;
        }

    }

?>