<?php
    require_once 'userProcess.php';

    $idProduct = $_GET['idProduct'];
    $rol = $_GET['rol'];

    echo UserProcess::getProductById($idProduct,$rol);
?>