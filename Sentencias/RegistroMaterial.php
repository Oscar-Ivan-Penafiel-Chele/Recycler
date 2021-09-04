<?php
    require("Conexion.php");
    $idLocation = $_GET['idRecicladora'];
    $idUsuario = empty ($_GET['idUsuario'] ) ? NULL : $_GET['idUsuario'];
    $idMaterial = empty ($_GET['idMaterial'] ) ? NULL : $_GET['idMaterial'];
    $peso = empty ($_GET['peso'] ) ? NULL : $_GET['peso'];
    $descripcionMaterial = $_GET['descripcionReciclaje'];
    $precioTotal = empty ($_GET['precioTotal'] ) ? NULL : $_GET['precioTotal'];

   if ($idUsuario &&  $idMaterial && $peso && $precioTotal) {
       $sql ="INSERT INTO reciclado(usuario_id, material_id, id_recicladora, descripcion_reciclaje, fecha_reciclaje, peso_kilogramo, precio_total, estado) VALUES ('$idUsuario', ' $idMaterial', '$idLocation','$descripcionMaterial', CURRENT_TIMESTAMP, '$peso', '$precioTotal', 1)";
       $result = mysqli_query($conexion, $sql);
   }else {
       echo"Faltan datos";
   }

    ?>