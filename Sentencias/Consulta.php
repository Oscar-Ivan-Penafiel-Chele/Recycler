<?php
    require("Conexion.php");
    $id = $_GET["id"];
    $sql="SELECT * FROM usuario where id = '$id'";
    $resultado= mysqli_query($conexion,$sql);  
    while ($row = mysqli_fetch_array($resultado)) {
        //$reciclador[] = array_map("utf8_encode", $row); 
        $reciclador[] = $row; 
    }

    echo json_encode($reciclador,JSON_UNESCAPED_UNICODE);
    $resultado->close();
?>