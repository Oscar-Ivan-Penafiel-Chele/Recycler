<?php
    require_once 'userProcess.php';

    $idUser = $_GET['idUser'];
    $descripcion = $_GET['descripcion'];
    echo UserProcess::searchProducto($idUser,$descripcion);
?>