<?php
    require_once 'userProcess.php';

    $idProduct = $_GET['idProduct'];

    echo UserProcess::getProductById($idProduct);
?>