<?php

	class configModel extends db_inst
	{
		public function isBlocked()
		{
			return $this->getVariable('blokada_validationQuest');
		}

        public function getConfig()
        {
            $inst = $this->get_instance();
            $q = 'SELECT nazwa, wartosc FROM config';
            $this->dbQuery($inst, $q);
            while($r = $this->dbRetRow($inst))
            {
                $t[$r['nazwa']] = $r['wartosc'];
            }
            $this->free_instance($inst);

            return $t;
        }

		public function getVariable($_vName)
		{
			$inst = $this->get_instance();
            $q = 'SELECT wartosc FROM config WHERE nazwa = \''.$_vName.'\'';
            $this->dbQuery($inst, $q);
            list($wartosc) = $this->dbRetrow($inst);
            $this->free_instance($inst);

            return $wartosc;
		}

        public function setVariable($_vName, $_vValue)
        {
            $inst = $this->get_instance();

            $this->free_instance($inst);
        }
	}