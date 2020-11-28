<?php
 
class DbConnect
{
    //variable para almacernar el link de la BD
    private $con;
 
    //Class constructor
    function __construct()
    {
 
    }
 
    //Este metodo se conectara a la BD
    function connect()
    {
        //Incluyendo el archivo constants.php archivo para obtener las constantes de la BD
        include_once dirname(__FILE__) . '/Constants.php';
 
        //conectando a la BD MySQL
        $this->con = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
 
        //Revisando cualquier error mientras se conecta
        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
            return null;
        }
 
        //finalmente retorna el link de la coneccion
        return $this->con;
    }
 
}