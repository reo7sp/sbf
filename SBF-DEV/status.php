<?php

require_once('backend/template.php');
require_once('backend/sbf_plugin_connection.php');

// getting info
$response = false;
try {
	$connection = new SBF_Plugin_Connection();
	$connection->connect();

	$connection->send_request('STATUS');
	for ($i = 0; $i < 10; $i++) {
		$response = explode(' ', $connection->read_response());
		if ($response[0] === 'STATUS') {
			break;
		}
	}

	$connection->disconnect();
} catch (Exception $err) {
	echo $err;
}

// building output
$message = false;
if ($response) {
	if ($response[0] == 'STATUS') {
		$message = '
				Включен
				<br>
				<br>
				TPS: ' . $response[1] . '/20
				<br>
				Память: ' . $response[2] . '%
				<br>
				Чанков: ' . $response[3] . '
				<br>
				Мобов: ' . $response[4];
	} else {
		$message = 'Ошибка';
	}
} else {
	$message = 'Выключен';
}

// printing page
echo '<html>';
echo '<head>';
sbf_print_head();
echo '</head>';
echo '<body>';
sbf_print_panel();
sbf_print_content('Статус сервера', $message);
echo '</body>';
echo '</html>';

?>