<?php

    require("Conexion.php");
    $usuario = $_GET["usuario"];
    $sql="SELECT * FROM usuario where correo = '$usuario'";
    $resultado= mysqli_query($conexion,$sql);  
    while ($row = $resultado->fetch_array()) {
        $reciclador[]=array_map('utf8_encode', $row); 
    }

    echo json_encode($reciclador);
    $resultado->close();



?>