<?php
    require_once 'userProcess.php';

    $idUser = $_GET['idUser'];
    $totalToRecive = $_GET['total'];
    
    echo UserProcess::generateRequest($idUser,$totalToRecive);
?>