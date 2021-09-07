<?php
    require_once 'userProcess.php';

    $datos = array(
        'id' => $_GET['id'],
        'name' => $_GET['name'],
        'lastName' => $_GET['lastName'],
        'email' => $_GET['email'],
        'cellphone' => $_GET['cellphone']
    );

    echo UserProcess::updateUser($datos);
?>