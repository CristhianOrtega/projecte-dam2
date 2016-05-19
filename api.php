


<?php

###################################################################################################################################################################
###################################################################################################################################################################
###################################################################################################################################################################
#############################################    ---      Script API.PHP LIBROS VIDAL  ---         ################################################################
###################################################################################################################################################################
###################################################################################################################################################################
###################################################################################################################################################################
#                                                                                                                                                                 #
#                                                                                                                                                                 #
# -- 7/4/16 -- COC --                                                                                                                                             #
###################################################################################################################################################################


header("Access-Control-Allow-Origin: *");


if (isset($_POST['action'])) {


	$action = $_POST['action'];
	$servername = "localhost";
	$username = "dbo624947598";
	$password = "David@Cristian";
	$db = new mysqli($servername, $username, $password, "db624947598");


	// --- Login -----------------------------------------------------------------
	if ($action == 'login') {

		 $email = $_POST['email'];
	     $password = md5($_POST['password']);
	     $sql = "SELECT * FROM Usuaris WHERE EMAIL = '$email' AND PASSWORD = '$password'";

	    
		if ($result = $db->query($sql)) {
			$rows =  array();
			$i = 0;
			
			while($row = $result->fetch_assoc()) {

	    		$rows[] = $row;

			}

			$var = "1";

		}else{
			$var = "0";
		}

		$json = json_encode($rows);
		echo $var.$json;


	}


	// --- Register --------------------------------------------------------------
	if ($action == 'register') {

	    $password = $_POST['password'];
	    $nom = $_POST['nom'];
	    $cognoms = $_POST['cognoms'];
	    $email = $_POST['email'];
	    $password = md5($_POST['password']);
	    
	    $sql = "INSERT INTO Usuaris (NOM,COGNOMS,EMAIL,PASSWORD) VALUES ('$nom','$cognoms','$email','$password')";
	    
	    if(!$result = $db->query($sql)){
    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";
    	}else{
    		echo "true";
    	}

	}


	// --- new product ------------------------------------------------------------
	if ($action == 'new_product') {


		// inserir producte
		$titol = $_POST['titol'];
		$id_user = $_POST['id_user'];
		$descripcio = $_POST['descripcio'];
		$preu = $_POST['preu'];
		$peticio = $_POST['peticio'];
		$venta = $_POST['venta'];
		$intercanvi = $_POST['intercanvi'];
		$regid = $_POST['regid'];
		$categoria = $_POST['categoria'];


		if($venta == 'true') $venta = 1;
		else $venta = 0;

		if($intercanvi == 'true') $intercanvi = 1;
		else $intercanvi = 0;

		if($peticio == 'true') $peticio = 1;
		else $peticio = 0;


		$sql = "INSERT INTO Productes (TITOL,DESCRIPCIO,PETICIO,PREU,VENTA,INTERCANVI,ID_USUARI,REGID,CATEGORIA) 
		VALUES ('$titol','$descripcio','$peticio','$preu','$venta','$intercanvi','$id_user','$regid','$categoria')";
	    
	    //echo $sql;

	    if(!$result = $db->query($sql)){

    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";

    	}else{
 
 			// si envia la imatge.
	    	if (isset($_POST['imatge'])){

				// mirar la id del producte
				$sql = "SELECT ID FROM Productes WHERE TITOL = '$titol'";
				$result = $db->query($sql);
 
			    while($row = $result->fetch_assoc()) {
        			$id_product = $row["ID"];
   			 	}

				// crear carpeta per al usuari si no existeix.
				$carpeta = 'images/'.$id_user.'/';

				if (!file_exists($carpeta)) {
				    mkdir($carpeta, 0777, true);
				}
				
				// convertir de string a jpegS
				$string_image = $_POST['imatge'];

				$nom = $id_product.'_imageProduct.jpeg';

				$rutaImatge = "http://programacion.cocinassobreruedas.com/".base64_decode_image($string_image, $nom, $carpeta);

				$sql = "INSERT INTO Imatges (ID_PRODUCTE,RUTA) VALUES ('$id_product','$rutaImatge')";

				 if(!$result = $db->query($sql)){
		    		die('There was an error running the query [' . $db->error . ']');
		    		echo "false";
		    	}else{
		    		echo "true";
		    	}
	    	}
		}
	}


	// --- return all products ------------------------------------------------------
	if ($action == 'return_all_products') {

		$sql = "SELECT * FROM Productes ORDER BY ID DESC";

		if ($result = $db->query($sql)) {
			$rows =  array();
			$resultat =  array();
			while($row = $result->fetch_assoc()) {
	    		// $rows[] = $row;
	    		$rows['ID'] = $row['ID'];
	    		$rows['TITOL'] = $row['TITOL'];
	    		$rows['DESCRIPCIO'] = $row['DESCRIPCIO'];
	    		$rows['CATEGORIA'] = $row['CATEGORIA'];
	    		$rows['PREU'] = $row['PREU'];
	    		$rows['INTERCANVI'] = $row['INTERCANVI'];
	    		$rows['VENTA'] = $row['VENTA'];
	    		$rows['PETICIO'] = $row['PETICIO'];
	    		$rows['VENUT'] = $row['VENUT'];
	    		$rows['ID_USUARI'] = $row['ID_USUARI'];
	    		$rows['REGID'] = $row['REGID'];
	    		if(isset($row['ID'])){
	    			$rows['ID_IMAGE'] = getImageProduct($row['ID']);
	    		}
	    		$resultat[] = $rows;
			}
		}

		$json = json_encode($resultat);
		echo $json;
	}

	// --- return all products by id ------------------------------------------------------
	if ($action == 'return_all_products_byid') {

		$id_user = $_POST['id_user'];

		$sql = "SELECT * FROM Productes WHERE ID_USUARI='$id_user'";

		if ($result = $db->query($sql)) {
			$rows =  array();
			$resultat =  array();
			while($row = $result->fetch_assoc()) {
	    		// $rows[] = $row;
	    		$rows['ID'] = $row['ID'];
	    		$rows['TITOL'] = $row['TITOL'];
	    		$rows['DESCRIPCIO'] = $row['DESCRIPCIO'];
	    		$rows['CATEGORIA'] = $row['CATEGORIA'];
	    		$rows['PREU'] = $row['PREU'];
	    		$rows['INTERCANVI'] = $row['INTERCANVI'];
	    		$rows['VENTA'] = $row['VENTA'];
	    		$rows['PETICIO'] = $row['PETICIO'];
	    		$rows['VENUT'] = $row['VENUT'];
	    		$rows['ID_USUARI'] = $row['ID_USUARI'];
	    		$rows['REGID'] = $row['REGID'];
	    		if(isset($row['ID'])){
	    			$rows['ID_IMAGE'] = getImageProduct($row['ID']);
	    		}
	    		$resultat[] = $rows;
			}
		}

		$json = json_encode($resultat);
		echo $json;
	}


	// --- return one product -------------------------------------------------------
	if ($action == 'return_one_product') {

		$id_product = $_POST['id_product'];

		$sql = "SELECT * FROM Productes WHERE ID = '$id_product'";

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;
	}


	// --- return image product ------------------------------------------------------
	if ($action == 'return_image_product') {

		$id_product = $_POST['id_product'];

		$sql = "SELECT * FROM Imatges WHERE ID_PRODUCTE = '$id_product'";

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;
	}


	// --- get_user_image_byregid ---------------------------------------------------------
	if ($action == 'get_user_image_byregid') {

		$regid = $_POST['regid'];

		$sql = "SELECT IMAGEPERFIL FROM Usuaris WHERE REGID='$regid'";

		echo $sql;

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;	

	}


	// --- modify_user_image ------------------------------------------------------
	if ($action == 'modify_user_image') {

		$imatge = $_POST['imatge'];
		$id_user = $_POST['id_user'];

		// crear carpeta per al usuari si no existeix.
		$carpeta = 'images/'.$id_user.'/';

		if (!file_exists($carpeta)) {
		    mkdir($carpeta, 0777, true);
		}
		
		// convertir de string a jpeg
		$string_image = $_POST['imatge'];

		$nom = $id_user.'_imageUser.jpeg';

		$rutaImatge = "http://programacion.cocinassobreruedas.com/".base64_decode_image($string_image, $nom, $carpeta);



		$sql = "UPDATE Usuaris SET IMAGEPERFIL='$rutaImatge',STRINGIMAGE='$string_image' WHERE ID='$id_user'";

	    if(!$result = $db->query($sql)){
    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";
    	}else{
    		echo "true";
    	}
    		

	}


	// --- get_user_image ---------------------------------------------------------
	if ($action == 'get_user_image') {

		$id_user = $_POST['id_user'];

		$sql = "SELECT IMAGEPERFIL FROM Usuaris WHERE ID='$id_user'";

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;	

	}


	// --- get_regId ---------------------------------------------------------
	if ($action == 'get_regid') {

		$email = $_POST['email'];

		$sql = "SELECT REGID FROM Usuaris WHERE EMAIL='$email'";

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;	

	}


	// --- save_regId ---------------------------------------------------------
	if ($action == 'save_regid') {

		$email = $_POST['email'];
		$regid = $_POST['regid'];

		$sql = "UPDATE Usuaris SET REGID='$regid' WHERE EMAIL='$email'";

		if(!$result = $db->query($sql)){
    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";
    	}else{
    		echo "true";
    	}

	}

	// --- update_regId ---------------------------------------------------------
	if ($action == 'modify_regid') {

		$id = $_POST['id'];
		$regid = $_POST['regid'];

		$sql = "UPDATE Productes SET REGID='$regid' WHERE ID_USUARI='$email'";

		if(!$result = $db->query($sql)){
    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";
    	}else{
    		echo "true";
    	}

	}


	// --- modify_user_perfil -----------------------------------------------------
	if ($action == 'modify_user_perfil') {

		$nom = $_POST['nom'];
		$cognoms = $_POST['cognoms'];
		$id_user = $_POST['id_user'];
		$perfil = $_POST['perfil'];
		$email = $_POST['email'];

		$sql = "UPDATE Usuaris SET NOM='$nom',COGNOMS='$cognoms',PERFIL='$perfil',EMAIL='$email' WHERE ID='$id_user'";

		if(!$result = $db->query($sql)){
    		die('There was an error running the query [' . $db->error . ']');
    		echo "false";
    	}else{
    		echo "true";
    	}

	}


	// --- delete_product ---------------------------------------------------------
	if($action == 'delete_product'){

		$id_product = $_POST['id_product'];

		// sql to delete a record
		$sql = "DELETE FROM Productes WHERE ID='$id_product'";

		//echo $sql;

		if ($result = $db->query($sql)) {
			$rows =  array();
			while($row = $result->fetch_assoc()) {
	    		$rows[] = $row;
			}
		}

		$json = json_encode($rows);
		echo $json;	
	}


	// --- search_product ---------------------------------------------------------
	if($action == 'delete_product'){

		$text = $_POST['text'];

		// sql to delete a record
		$sql = "SELECT * FROM Productes WHERE TITOL LIKE '%$text%'";

		//echo $sql;

		if ($db->query($sql) === TRUE) {
		    echo "true";
		} else {
		    echo "false";
		}
	}

	$db->close();

}


// --- StringImage   to   Image -----------------------------------------------------

function base64_decode_image($image, $name, $ruta){
    if ($image){
        $new_file = fopen($ruta . $name, "w+");
        fwrite($new_file, base64_decode($image));
        fclose($new_file);
        //echo base64_decode($image);
        return $ruta.$name;
    }
}


function getImageProduct($id_product){

	$servername = "localhost";
	$username = "dbo624947598";
	$password = "David@Cristian";
	$db = new mysqli($servername, $username, $password, "db624947598");

	$sql = "SELECT RUTA FROM Imatges WHERE ID_PRODUCTE = '$id_product'";
	$result = $db->query($sql);
	$row = $result->fetch_assoc();
	$db->close();
	return $row['RUTA'];
}


?>