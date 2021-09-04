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
            $idusuario=$row["id"];
            $dbusuario=$row["correo"];
            $dbcontraseña=$row["contrasena"];
            $dbrol =$row["rol"];
            if ($dbusuario == $usuario && password_verify($contraseña,$dbcontraseña)) {
                
                if($dbrol==2){
                    $login = "Correcto-".$idusuario;
                }elseif($dbrol==1) {
                    $login = "Admin-".$idusuario;
                }
            }else{
                $login = "Incorrecto-0";    
                                  
            }
        }else {
            $login = "Incorrecto-0";
            //echo "No se pudo conectar";
        }
        
    }else {
        echo "Campos requeridos";
    }
    echo $login;
?>