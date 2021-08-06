<?php
   include("Registro.php");
    $correo=$_GET["correo"];
    $contraseña=$_GET["contraseña"];

    $query = "INSERT INTO usuario (rol, nombre, apellido, telefono, correo, contrasena) VALUES ('2', '$nombre', '$apellido', '$telefono', '$correo', '$contraseña')";
    $result = mysqli_query($conexion, $query);
    $result ==true ? "Registro Exitoso" : "Fallo Registro";
   mysqli_close($conexion);
?>