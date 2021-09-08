<?php
    require_once 'userProcess.php';

    $idRequest = $_GET['idRequest'];

    echo UserProcess::confirmRequest($idRequest);
?>