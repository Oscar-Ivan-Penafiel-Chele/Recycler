<?php
    require("Conexion.php");
    $usuario=$_GET["usuario"];
    $contraseña=$_GET["contrasena"];
    $login;
    
    if(isset($usuario) && isset($contraseña)){
        $sql = "SELECT * FROM ".$tablename ." where correo = '" . $usuario . "'";

        $result = mysqli_query($conexion, $sql);
        $row=mysqli_fetch_assoc($result);
        if(mysqli_num_rows($result) != 0){
            $dbusuario=$row["correo"];
            $dbcontraseña=$row["contrasena"];
            $dbrol =$row["rol"];
            if ($dbusuario == $usuario && password_verify($contraseña,$dbcontraseña)) {
                
                if($dbrol==2){
                    $login = "Correcto";
                }elseif($dbrol==1) {
                    $login = "Admin";
                }
            }else{
                $login = "Incorrecto";    
                                  
            }
        }else {
            $login = "Incorrecto";
            //echo "No se pudo conectar";
        }
        
    }else {
        echo "Campos requeridos";
    }
    echo $login;
?>