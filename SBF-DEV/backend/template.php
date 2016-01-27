<?php 

require_once('backend/login_manager.php');

sbf_start_session();

function sbf_print_head() {

?>

<title>SBF DEV</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="style.css">

<?php

}

function sbf_print_panel() {

?>

<font size="4" face="Arial">
	<div id="panel1">
		<font color="white">
			<div id="panel1-label"><a href="index.php">SBF <b>&#183;</b> DEV</a></div>
			<div id="panel1-buttons">
				<?php

				if (sbf_is_logined()) {
					echo '<a href="server.php">Сервер</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					echo '<a href="anticheat.php">Античит</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					echo '<a href="status.php">Статус</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					echo '<a href="unlogin.php">Выход</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
				} else {
					echo '<a href="status.php">Статус</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					echo '<a href="login.php">Вход</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
				}

				?>
			</div>
		</font>
	</div>
</font>

<?php

}

function sbf_print_content($label, $content) {

?>

<font size="4" face="Arial">
	<div id="panel2">
		<font color="black">
			<div id="panel2-label">
				<?php echo $label; ?>
			</div>
		</font>
	</div>

	<div id="content">
		<?php echo $content; ?>
	</div>
</font>

<?php

}

?>