<?php
    require("Conexion.php");
    $id=$_GET["id"];
    $sql="SELECT material_id FROM reciclado where id = '$id'";
    $result = mysqli_query($conexion,$sql);
    while ($row = mysqli_fetch_assoc($result)) {
        $r = $row["material_id"]; 
    }
    echo $r;
?>