<?php
   include("Registro.php");

   function buscarRepetido($usuario, $contraseña, $conexion,$tablename){
      $sql="SELECT * From ". $tablename ." where correo ='$usuario'";
      $resultado=mysqli_query($conexion, $sql);
      if(mysqli_num_rows($resultado)>0){
         return 1;
      }else {
         echo mysqli_num_rows($resultado);
         return 0;
      }

   }

    $correo=$_GET["correo"];
    $contraseña= password_hash($_GET["contrasena"], PASSWORD_DEFAULT);
    

   if(isset($nombre) && isset($apellido) && isset($telefono) && isset($correo) && isset($contraseña)){
      if(buscarRepetido($correo,$contraseña,$conexion, $tablename) == 1){
         echo "usuario ya existe, coloque otro nombre";

      }else{
         $query = "INSERT INTO ". $tablename ."(rol, nombre, apellido, telefono, correo, contrasena) VALUES ('2', '$nombre', '$apellido', '$telefono', '$correo', '$contraseña')";
         $result = mysqli_query($conexion, $query);
         $result ? "Registro Exitoso" : "Fallo Registro";
      }
      
   }else {
      echo("Faltan campos");
   }
    
   mysqli_close($conexion);

   
?>