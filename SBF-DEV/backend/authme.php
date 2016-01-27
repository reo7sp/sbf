<?php

require_once('utils.php');

function sbf_check_password_db($nickname, $password) {
	if ($handle = mysql_connect('localhost:3306', 'root', 'MsqL1879')) {
		if (!mysql_select_db('bukkit')) {
			mysql_close($handle);
			return false;
		}
		$result = mysql_query($handle, "SELECT password FROM authme where username = '$nickname'");
		if (mysql_num_rows($result) == 1) {
			$password_info = mysql_fetch_array($result);
			$sha_info = explode("$", $password_info[0]);
		} else {
			mysql_close($handle);
			return false;
		}
		if($sha_info[1] === "SHA") {
			$salt = $sha_info[2];
			$sha256_password = hash('sha256', $password);
			$sha256_password .= $sha_info[2];
			if(strcasecmp(trim($sha_info[3]), hash('sha256', $sha256_password)) == 0) {
				mysql_close($handle);
				return true;
			} else {
				mysql_close($handle);
				return false;
			}
		}
		mysql_close($handle);
	} else {
		return false;
	}
}

?>