<?php

require_once('utils.php');

function sbf_get_anticheat_data($nick) {
	$data = array();
	$file_content = file(sbf_get_anticheat_dir() . '/' . $nick . '.txt');
	$currentMode = 0;

	foreach ($file_content as $number => $line) {
		$lineParts = explode('=', $line, 2);
		if ($lineParts[0] === 'ReportMode') {
			$currentMode = $lineParts[1];
		} else if ($lineParts[0] === 'Name') {
			$data['nick'] = $lineParts[1];
		} else if ($lineParts[0] === 'IP') {
			$data['ip'] = $lineParts[1];
		} else if ($lineParts[0] === 'Date') {
			$data['date'] = $lineParts[1];
		} else if (strpos($line, '-----') !== false) {
			if ($currentMode == 0) {
				if (isset($data['unknown_files'])) {
					unset($data['unknown_files']);
				}
				if (isset($data['denied_files'])) {
					unset($data['denied_files']);
				}
			} else if ($currentMode == 1) {
				if (isset($data['nicks'])) {
					unset($data['nicks']);
				}
			}
		} else {
			if ($currentMode == 0) {
				if (!isset($data['unknown_files'])) {
					$data['unknown_files'] = array();
				}
				if (!isset($data['denied_files'])) {
					$data['denied_files'] = array();
				}
				if (strpos($line, '!!!') === false) {
					$data['unknown_files'][] = $line;
				} else {
					$data['denied_files'][] = substr($line, 3);
				}
			} else if ($currentMode == 1) {
				if (!isset($data['nicks'])) {
					$data['nicks'] = array();
				}
				$data['nicks'][] = $line;
			}
		}
	}

	return $data;
}

function sbf_get_anticheat_dir() {
	return sbf_get_server_dir() . '/plugins/SBF Server Mod/anticheat_reports';
}

?>