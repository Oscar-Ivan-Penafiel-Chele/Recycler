<?php

    $db_host="localhost";
    $db_contra="";
    $db_nombre= "reciclaje";
    $db_usuario = "root";
    $tablename="usuario";
    $conexion=mysqli_connect($db_host,$db_usuario,$db_contra);

    if(mysqli_connect_errno()){
        echo "No se pudo conectar a la base de datos";
        exit();
    }
    mysqli_select_db($conexion,$db_nombre) or die("El nombre de la base de datos es incorrecta");
    mysqli_set_charset($conexion, "utf8");
    


?>