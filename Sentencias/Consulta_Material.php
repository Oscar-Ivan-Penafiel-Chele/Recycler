<?php

    $id_material =  empty ($_GET['idMaterial'] ) ? NULL : $_GET['idMaterial'];

    if($id_material){
        require("Conexion.php");
       
        $sql ="SELECT * from material where id = $id_material";
        
        $result = mysqli_query($conexion, $sql);
        while ($r = $result ->fetch_assoc()) {
        $material[]= array_map('utf8_encode', $r); ;

        }
        echo json_encode($material);
        $result->close();

    }else {
        print("No han llegado parametros");
    }


?>