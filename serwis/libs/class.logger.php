<?php

    class logger extends db_inst
    {

        public function addLog($_type, $_date, $_msg, $_status)
        {
            $date = date('Y-m-d H:i:s', time());
            $dateM = date('m', time());
            $dateY = date('Y', time());

            if ($this->logForMonthExists($dateY, $dateM))
            {

            }

            $inst = $this->get_instance();
            $this->AddSql($inst, 'TXT', 'type',     $_type);
            $this->AddSql($inst, 'TXT', 'msg_date', date('Y-m-d H:i:s', time()));
            $this->AddSql($inst, 'TXT', 'msg',      $_msg);
            $this->AddSql($inst, 'TXT', 'status',   $_type);

            $q = $this->GenerateSql($inst, 'INS', 'logger_'.$dateY.'_'.$dateM);

            $this->dbQuery($inst, $q, 'LAST_INSERT_ID');
            $id = $this->dbLastInsertId($inst);
            $this->free_instance($inst);

            if ($id)
                return $id;
            else
                return false;
        }

        private function logForMonthExists($_year, $_month)
        {
            $inst = $this->get_instance();
            $q = 'SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA LIKE \'.dbName.\'';
            $this->dbQuery($inst, $q);
            while($r = $this->dbRetRow($inst))
            {
                $t[$r['TABLE_NAME']] = 1;
            }

            if (!$t['logger_'.$_year.'_'.$_month])
            {
                return $this->createTable($_year, $_month);
            }
            else
                return true;
        }

        private function createTable($_year, $_month)
        {
            $inst = $this->get_instance();
            $q = '
            CREATE TABLE `logger_'.$_year.'_'.$_month.'` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(32) NOT NULL DEFAULT \'0\' COLLATE \'utf8_polish_ci\',
	`msg_date` TIMESTAMP NULL DEFAULT NULL,
	`msg` TEXT NULL COLLATE \'utf8_polish_ci\',
	`status` VARCHAR(32) NULL DEFAULT NULL COLLATE \'utf8_polish_ci\',
	`user_id` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE=\'utf8_polish_ci\'
ENGINE=MyISAM
            ';
            $this->dbQuery($inst, $q);

            return true;
        }
    }