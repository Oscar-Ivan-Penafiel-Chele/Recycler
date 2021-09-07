<?php
    require_once 'userProcess.php';
    $apellido=$_GET["apellido"];
    echo UserProcess::getRequest($apellido);
?>