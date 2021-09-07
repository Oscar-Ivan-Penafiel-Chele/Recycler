<?php
    require_once 'userProcess.php';

    $idUser = $_GET['id'];

    echo UserProcess::getProductsRecycler($idUser);
?>