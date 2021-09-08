<?php
    require_once 'mapAdminProcess.php';

    $idUser = $_GET['id'];

    echo MapAdminProcess::getLocations($idUser);
?>