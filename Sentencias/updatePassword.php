<?php
    require_once 'userProcess.php';

    $id = $_GET['id'];
    $oldPassword = $_GET['oldPassword'];
    $newPassword = $_GET['newPassword'];

    echo UserProcess::verifyPassword($id,$oldPassword,$newPassword);
?>