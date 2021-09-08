<?php
    require_once 'userProcess.php';

    $idUser = $_GET['idUser'];
    $idRequest = $_GET['idRequest'];

    echo UserProcess::getProductsByUser($idUser,$idRequest);
?>