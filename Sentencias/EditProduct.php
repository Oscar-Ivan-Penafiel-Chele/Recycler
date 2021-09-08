<?php
    require("Conexion.php");
    $peso=$_GET["pesoNuevo"];
    $id=$_GET["idMaterial"];
    $precio = $_GET["precio"];
    $descripcion = $_GET["descripcion"];
    $sql= "UPDATE reciclado SET peso_kilogramo = '$peso', precio_total= '$precio', descripcion_reciclaje='$descripcion' WHERE id = '$id'";
    $result = mysqli_query($conexion,$sql);
    if($result){
         echo "true";
    }else{
        echo "false";
     }
?>