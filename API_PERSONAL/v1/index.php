<?php 

	require_once '../includes/DbOperation.php';
	
	$response = array(); 

	// /API_PERSONAL/v1/?opt=addpersonal
	
	if(isset($_GET['opt'])){
		
		switch($_GET['opt']){
 
				// /API_PERSONAL/v1/?opt=addpersonal
				///Method POST
			case 'addpersonal':
				if(isset($_POST['nombre']) && isset($_POST['cedula'])){
					$db = new DbOperation(); 
					if($db->addPersonal($_POST['nombre'], $_POST['cedula'])){
						$response['error'] = false;
						$response['message'] = 'Personal agregado satisfactoriamente';
					}else{
						$response['error'] = true;
						$response['message'] = 'No se puede agregar al personal';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Requiere parametros';
				}
			break; 
			 
			// /API_PERSONAL/v1/?opt=getpersonal
			////Method GET
			case 'getpersonal':
				$db = new DbOperation();
				$listPersonal = $db->getPersonal();
				if(count($listPersonal)<=0){
					$response['error'] = true; 
					$response['message'] = 'No hay datos en la BD';
				}else{
					$response['error'] = false; 
					$response['personal'] = $listPersonal;
				}
			break; 
			
			default:
				$response['error'] = true;
				$response['message'] = 'No operation to perform';
			
		}
		
	}else{
		$response['error'] = false; 
		$response['message'] = 'Invalid Request';
	}
	
	echo json_encode($response);