<?php
    require_once 'userProcess.php';

    $idUser = $_GET['idUser'];
    
    echo UserProcess::updateStateRequest($idUser);
?>