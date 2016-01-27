<?php

require_once('backend/template.php');
require_once('backend/login_manager.php');

sbf_start_session();

$message = '';

if (isset($_POST['nick']) && isset($_POST['password'])) {
	if (!sbf_login($_POST['nick'], $_POST['password'])) {
		$message = 'Ошибка!';
	}
}

echo '<html>';
echo '<head>';
sbf_print_head();
if (sbf_is_logined()) {
	echo '<meta http-equiv="refresh" content="0; url=index.php">';
}
echo '</head>';
echo '<body>';
sbf_print_panel();
sbf_print_content('Вход', $message.'

<form action="login.php" method="POST">
	Логин: &nbsp;&nbsp;&nbsp;<input name="nick" type="text" value="">
	<br>
	Пароль: <input name="password" type="password" value="">
	<br>
	<input type="submit" value="Войти">
</form>

');
echo '</body>';
echo '</html>';

?>