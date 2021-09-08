<?php
    require("Conexion.php");

    $GLOBALS['db'] = $conexion;

    class UserProcess{
        static public function updateUser($datos){
            $id = $datos['id'];
            $nombre = $datos['name'];
            $apellido = $datos['lastName'];
            $email = $datos['email'];
            $phone = $datos['cellphone'];

            $sql = "UPDATE usuario SET 
                            nombre = '$nombre',
                            apellido= '$apellido',
					        telefono= '$phone',
					        correo= '$email'
					        WHERE id= '$id'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                echo "true";
            }else{
                echo "false";
            }
        }   


        public function verifyPassword($id,$oldPassword,$newPassword){
            $sql = "SELECT * FROM usuario WHERE id = '$id'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $userPassword = mysqli_fetch_array($result);
            if(!password_verify($oldPassword,$userPassword['contrasena'])){
                    echo "false";
                    exit();
            }
           UserProcess::updatePassword($id,$newPassword);  
        }


        static public function updatePassword($id,$newPassword){
            $password = password_hash($newPassword,PASSWORD_DEFAULT);
            $sql = "UPDATE usuario SET contrasena = '$password' WHERE id = '$id'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                echo "true";
            }else{
                echo "false";
            }
        }
        

        static public function updateStateRequest($idUser){
            $sql = "UPDATE usuario 
                    SET fecha_peticion = CURRENT_TIMESTAMP , estado_peticion = '1' WHERE id = '$idUser'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                $sql1 = "UPDATE reciclado 
                         SET estado = '0' WHERE usuario_id = '$idUser'";
                $result1 =  mysqli_query($GLOBALS['db'],$sql1);
                if($result1){
                   echo "true";     
                }else{
                    echo "false";
                }
            }else{
                echo "false";
            }
        }


        static public function generateRequest($idUser,$totalToRecive){
            $sql = "INSERT INTO peticion ( id_usuario, total_recibir, fecha_peticion , estado_peticion) 
                           VALUES ('$idUser', '$totalToRecive', CURRENT_TIMESTAMP, '1') ";
            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                UserProcess::updateIdPeticionProduct($idUser);
            }else{
                echo "false";
            }
            $result->close();
        }

        static public function updateIdPeticionProduct($id){
            $sql = "SELECT MAX(id_peticion) AS id_peticion FROM peticion WHERE id_usuario = '$id'";
            $result = mysqli_query($GLOBALS['db'],$sql);

            $row = mysqli_fetch_array($result);
            $id_peticion = $row['id_peticion'];

            if($result){
                $sqlUpdate ="UPDATE reciclado 
                             SET id_peticion = '$id_peticion' WHERE  estado = '1'";

                $result1 = mysqli_query($GLOBALS['db'],$sqlUpdate);

                if($result1){
                    $sql2 = "UPDATE reciclado 
                                    SET estado = '2' WHERE usuario_id = '$id'";
                    $result2 = mysqli_query($GLOBALS['db'],$sql2);
                    if($result2){
                        echo "true";
                    }else{
                        echo "false";
                    }
                }else{
                    echo "false";
                }
            }else{
                echo "false";
            }
          
        }

        static public function getAllRequest(){
            $sql = "SELECT * FROM peticion request
                            INNER JOIN usuario usuario
                            ON request.id_usuario = usuario.id
                            WHERE estado_peticion = '1'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $amountRow = $result -> num_rows;
            if($amountRow <= 0){
                exit();
            }
            while($row = mysqli_fetch_assoc($result)){
                $allRequest [] = $row;
            }
            echo json_encode($allRequest,JSON_UNESCAPED_UNICODE);
        }


        static public function confirmRequest($idRequest){
            $sql = "UPDATE peticion 
                            SET estado_peticion = '0' WHERE id_peticion = '$idRequest'";

            $sql1 = "UPDATE reciclado
                            SET estado = '0' WHERE id_peticion = '$idRequest'";

            $result = mysqli_query($GLOBALS['db'],$sql);
            $result1 = mysqli_query($GLOBALS['db'],$sql1);
            if($result && $result1){
                echo "true";
            }else{
                echo "false";
            }
            $result->close();
        }


        static public function getProductsRecycler($idUser){
            $sql = "SELECT * FROM reciclado WHERE estado = '1' && usuario_id='$idUser'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            while($row = mysqli_fetch_assoc($result)){
                $productsRecycler [] = $row;
            }
            echo json_encode($productsRecycler,JSON_UNESCAPED_UNICODE);
        }


        static public function searchProducto($idUser,$descripcion){
            $sql = "SELECT * FROM reciclado WHERE estado = '1' && usuario_id='$idUser' && descripcion_reciclaje LIKE '%$descripcion%'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            while($row = mysqli_fetch_assoc($result)){
                $productsRecycler [] = $row;
            }
            echo json_encode($productsRecycler,JSON_UNESCAPED_UNICODE);
        }


        static public function getRequest($apellido){
            $sql = "SELECT * FROM usuario WHERE estado_peticion = '1' && apellido LIKE '%$apellido%'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $amountRow = $result -> num_rows;
            if($amountRow <= 0){
                exit();
            }
            while($row = mysqli_fetch_assoc($result)){
                $allRequest [] = $row;
            }
            echo json_encode($allRequest,JSON_UNESCAPED_UNICODE);
        }

        
        static public function getAllRequestForUser($idUser){
            $sql = "SELECT * FROM reciclado recycler
                            INNER JOIN usuario user
                            ON user.id = recycler.usuario_id
                            WHERE recycler.estado = '1' && user.estado_peticion = '1' && recycler.usuario_id='$idUser'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $amountRow = $result -> num_rows;
            if($amountRow <= 0){
                exit();
            }
            while($row = mysqli_fetch_assoc($result)){
                $allRequest [] = $row;
            }
            echo json_encode($allRequest,JSON_UNESCAPED_UNICODE);
        }


        static public function getProductById($idProduct,$rol){
            if($rol == 1){
                $estado = 2;
            }else if($rol == 2){
                $estado = 1;
            }

            $sql = "SELECT * FROM reciclado
                             WHERE id = '$idProduct' && estado = '$estado'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $amountRow = $result -> num_rows;
            if($amountRow <= 0){
                exit();
            }
            while($row = mysqli_fetch_assoc($result)){
                $product [] = $row;
            }
            echo json_encode($product,JSON_UNESCAPED_UNICODE);
        }
        
        
        static public function getProductsByUser($idUser, $idRequest){
            $sql = "SELECT * FROM reciclado
                            WHERE usuario_id = '$idUser' && estado = '2' && id_peticion = '$idRequest'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            $amountRow = $result -> num_rows;
            if($amountRow <= 0){
                exit();
            }
            while($row = mysqli_fetch_assoc($result)){
                $product [] = $row;
            }
            echo json_encode($product,JSON_UNESCAPED_UNICODE);
        }


        static public function deleteProduct($idProduct){
            $sql = "DELETE FROM reciclado
                        WHERE id = '$idProduct'";
            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                echo "true";
            }else{
                echo "false";
            }
        }
        


    }   
?>