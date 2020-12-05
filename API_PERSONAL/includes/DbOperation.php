<?php
 
class DbOperation
{
    private $con;
 
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $this->db = new DbConnect();
    }

	public function addPersonal($nombre, $cedula, $celular, $correo, $detalle){
	    $query="INSERT INTO `lista` (`nombre`, `cedula`, `celular`, `correo`) VALUES ('$nombre', '$cedula','$celular','$correo');";
		$stmt = $this->db->query($query);
		
		if($stmt==0)
		{
		 
			$id=$this->db->getInsert();
		 
			$query_detail="INSERT INTO `lista_detalle` (`id_detalle`, `detalle`) VALUES ('$id','$detalle');";
			$stmt = $this->db->query($query_detail); 
			$id_detalle=$this->db->getInsert();
		  
			return true;
		}
		return false; 
		
	}

	public function getPersonal(){
		$this->db->query('SELECT id, nombre, cedula, celular, correo FROM lista');
		$listapersonal = array();
		$row=$this->db->fetch();
		foreach($row as $item){
			 
			$temp = array(); 
			$temp['id'] = $item['id']; 
			$temp['nombre'] = $item['nombre']; 
			$temp['cedula'] = $item['cedula']; 
			$temp['correo'] = $item['correo'];
			$temp['celular'] = $item['celular'];
			
			  $this->db->query('SELECT id, detalle FROM lista_detalle where id_detalle='.$item['id']);
		      $detalle = array();
		      $row_detalle=$this->db->fetch();
			
			$temp['detalle'] = $row_detalle; 
			
			array_push($listapersonal, $temp);
		}
		return $listapersonal; 
	 
       
	}
	
}