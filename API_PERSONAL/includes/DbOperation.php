<?php
 
class DbOperation
{
    private $con;
 
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
		
    }

	public function addPersonal($nombre, $cedula){
		$stmt = $this->con->prepare("INSERT INTO `lista` (`id`, `nombre`, `cedula`) VALUES (NULL, ?, ?);");
		$stmt->bind_param("ss", $nombre, $cedula);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	public function getPersonal(){
		$stmt = $this->con->prepare("SELECT id, nombre, cedula FROM lista");
		 
		$stmt->execute();
		$stmt->bind_result($id, $nombre, $cedula);
		$listapersonal = array();
		
		while($stmt->fetch()){
			$temp = array(); 
			$temp['id'] = $id; 
			$temp['nombre'] = $nombre; 
			$temp['cedula'] = $cedula; 
			array_push($listapersonal, $temp);
		}
		return $listapersonal; 
	}
}

