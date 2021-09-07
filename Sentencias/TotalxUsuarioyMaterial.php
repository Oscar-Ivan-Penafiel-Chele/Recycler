<?php
    require("Conexion.php");
    $idUsuario = $_GET["idUsuario"];


    $sql = "SELECT * from reciclado where usuario_id = $idUsuario";
    $result= mysqli_query($conexion, $sql);
    while ($r = $result ->fetch_assoc()) {
        $material[]= array_map('utf8_encode', $r); 
    }
    echo json_encode($material);
        $result->close();
?>