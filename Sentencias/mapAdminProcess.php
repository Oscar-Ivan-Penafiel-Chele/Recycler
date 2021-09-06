<?php

    require_once 'Conexion.php';

    $GLOBALS['db'] = $conexion;

    class MapAdminProcess {

        static public function getLocations(){
            $sql = "SELECT * FROM recicladora_sucursal WHERE estado_direccion = '1'";

            $result = mysqli_query($GLOBALS['db'],$sql);

            while($row = mysqli_fetch_assoc($result)){
                $locations [] = $row;
            }

            echo json_encode($locations,JSON_UNESCAPED_UNICODE);
            $result->close();
        }
        
        static public function verifyLocation($datos){
            $latitud = $datos['latitud'];
            $longitud = $datos['longitud'];

            $sql = "SELECT * FROM recicladora_sucursal 
                             WHERE coord_latitud = '$latitud' && coord_longitud = '$longitud'";

            $result = mysqli_query($GLOBALS['db'],$sql);

            if(mysqli_num_rows($result)>0){
                echo "1";
             }else {
                echo MapAdminProcess::addLocation($datos);
             }
             $result->close();
        }

        public function addLocation($datos){
            $idUser = $datos['idUser'];
            $latitud = $datos['latitud'];
            $longitud = $datos['longitud'];
           
            $sql = "INSERT INTO recicladora_sucursal ( id_usuario, coord_latitud, coord_longitud , estado_direccion) 
                            VALUES ('$idUser', '$latitud', '$longitud', '1')";

            $result = mysqli_query($GLOBALS['db'],$sql);

            if($result){
                echo "true";
            }else{
                echo "false";
            }
            $result->close();
        }

        static public function deleteAddress($idAddress){
            $sql = "UPDATE recicladora_sucursal SET estado_direccion = '0' 
                            WHERE id_recicladora = '$idAddress'";

            $result = mysqli_query($GLOBALS['db'],$sql);
            if($result){
                echo "true";
            }else{
                echo "false";
            }
        }

    }

?>