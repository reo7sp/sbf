<?php

require_once('backend/template.php');
require_once('backend/login_manager.php');
require_once('backend/sbf_plugin_connection.php');

// check is user logined
if (!sbf_is_logined()) {
	echo '<html><head><meta http-equiv="refresh" content="0; url=index.php"></head></html>';
	exit;
}

// getting info
$log = '';
try {
	$connection = new SBF_Plugin_Connection();
	$connection->connect();

	$connection->send_request('SHOW-CONSOLE');
	for ($i = 0; $i < 500; $i++) {
		$response = explode(' ', $connection->read_response(), 2);
		if ($response[0] === 'CONSOLE') {
			$log .= $response[1];
		} else if ($response[0] === 'CONSOLE-END') {
			break;
		}
	}

	$connection->disconnect();
} catch (Exception $err) {
	echo $err;
}

// printing page
echo '<html>';
echo '<head>';
sbf_print_head();
echo '</head>';
echo '<body>';
sbf_print_panel();
sbf_print_content('Управление сервером', '

<textarea cols="125" rows="35" readonly>
' . $log . '
</textarea>

');
echo '</body>';
echo '</html>';

?>