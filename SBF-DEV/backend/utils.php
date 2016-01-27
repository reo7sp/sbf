<?php

function sbf_get_dir_contents($path) {
	if ($handle = opendir($path)) {
		$contents = array();

		while (false !== ($entry = readdir($handle))) {
			$contents[] = $entry;
		}

		closedir($handle);

		return $contents;
	}
}

function sbf_get_server_dir() {
	// return '/var/www/sbf/data/sbf/sbfserver';
	return '/home/reo7sp/my_little_usr/mcservers/modern/sbfserver';
}

?>