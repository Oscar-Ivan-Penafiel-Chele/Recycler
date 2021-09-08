<?php
    require_once 'mapAdminProcess.php';

    $id_address = $_GET['idAddress'];

    echo MapAdminProcess::deleteAddress($id_address);
?>