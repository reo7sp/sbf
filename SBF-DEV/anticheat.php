<?php

require_once('backend/template.php');
require_once('backend/login_manager.php');
require_once('backend/anticheat_manager.php');
require_once('backend/utils.php');

$message = '';

// check is user logined
if (!sbf_is_logined()) {
	echo '<html><head><meta http-equiv="refresh" content="0; url=index.php"></head></html>';
	exit;
}

if (isset($_GET['nick'])) {
	// getting info
	$data = sbf_get_anticheat_data($_GET['nick']);

	// building output
	$message .= 'Ник: ' . $data['nick'] . '<br>';
	$message .= 'Время: ' . $data['date'] . '<br>';
	$message .= 'IP: ' . $data['ip'] . '<br>';
	$message .= '<br>';
	$message .= 'Другие ники:<br>';
	foreach ($data['nicks'] as $key => $value) {
		$message .= $value . '<br>';
	}
	$message .= '<br>';
	$message .= 'Файлы в minecraft.jar:<br>';
	foreach ($data['denied_files'] as $key => $value) {
		$message .= '!!! ' . $value . '<br>';
	}
	foreach ($data['unknown_files'] as $key => $value) {
		$message .= $value . '<br>';
	}
} else {
	// getting info
	$data = array();
	$anticheat_reports = get_dir_contents(sbf_get_anticheat_dir());
	foreach ($anticheat_reports as $key => $value) {
		$data[] = sbf_get_anticheat_data(explode('.', $value)[0]);
	}

	// building output
	$message .= '<table width=100% border="1">';
	$message .= '<tr>';
	$message .= '<td><b>Ник</b></td>';
	$message .= '<td><b>Время</b></td>';
	$message .= '<td><b>IP</b></td>';
	$message .= '<td><b>Файлы</b></td>';
	$message .= '<td><b>Другие ники</b></td>';
	$message .= '</tr>';
	foreach ($data as $key => $value) {
		$message .= '<tr>';
		$message .= '<td><a href="' . $_SERVER['REQUEST_URL'] . '?nick=' . $value['nick'] . '">' . $value['nick'] . '</a></td>';
		$message .= '<td>' . $value['date'] . '</td>';
		$message .= '<td>' . $value['ip'] . '</td>';
		$message .= '<td>' . count($value['denied_files']) . '/' . count($value['unknown_files']) . '</td>';
		$message .= '<td>';
		foreach ($value['nicks'] as $key0 => $value0) {
			$message .= explode(' ', $value0) . ', ';
		}
		$message .= '</td>';
		$message .= '</tr>';
	}
	$message .= '</table>';
}

// printing page
echo '<html>';
echo '<head>';
sbf_print_head();
echo '</head>';
echo '<body>';
sbf_print_panel();
sbf_print_content('Отчёты античита', $message);
echo '</body>';
echo '</html>';

?>