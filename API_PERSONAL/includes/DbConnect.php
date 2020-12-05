<?php
 
class DbConnect
{
    //variable para almacernar el link de la BD
    private $link;
    private $resultId="";
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
 
        $this->link=mysqli_connect(DB_HOST,DB_USERNAME,DB_PASSWORD);
	
        mysqli_select_db($this->link,DB_NAME);		
    }
	
	public function query($string)
	{
	$this->connect();
	$this->resultId=mysqli_query($this->link,$string);

		
	}
	
		public function utf8_converter($array)
{
    array_walk_recursive($array, function(&$item, $key){
        if(!mb_detect_encoding($item, 'utf-8', true)){
                $item = utf8_encode($item);
        }
    });
 
    return $array;
}
	
	public function fetch()
	{
		$datos=array();
	
	while($row=mysqli_fetch_assoc($this->resultId))
	{
	
		$datos[]=$row;
		
	}		
	   $this->desconectar();
		return $this->utf8_converter($datos);
	}
	public function desconectar()
	{
		mysqli_close($this->link);
		
	}
	
	public function getInsert()
	{
	return	mysqli_insert_id($this->link);
		
	}
 
}