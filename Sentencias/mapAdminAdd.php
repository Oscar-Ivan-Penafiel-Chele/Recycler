<?php

    require_once 'mapAdminProcess.php';

    $datos = array(
        'idUser' => $_GET['idUser'],
        'latitud' => $_GET['latitud'],
        'longitud' => $_GET['longitud'],
    );

    echo MapAdminProcess::verifyLocation($datos);
?>