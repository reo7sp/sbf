<?php

require_once('authme.php');

function sbf_start_session() {
	if (!sbf_is_session_exists()) {
		sbf_recreate_session();
	}
	sbf_verify();
}

function sbf_recreate_session() {
	sbf_destroy_session();

	session_name('SBF_DEV_SESSION');
	session_set_cookie_params(3600);
	session_cache_expire(60);
	session_start();
	$_SESSION['ip'] = $_SERVER['REMOTE_ADDR'];
}

function sbf_destroy_session() {
	session_regenerate_id(true);
}

function sbf_login($nick, $password) {
	if (!sbf_is_logined() && sbf_check_password($nick, $password)) {
		$_SESSION['nick'] = $nick;
		$_SESSION['login'] = true;
		return true;
	}
	sbf_verify();
}

function sbf_is_session_exists() {
	return session_id();
}

function sbf_check_session() {
	return sbf_is_session_exists() && $_SESSION['ip'] == $_SERVER['REMOTE_ADDR'];
}

function sbf_is_logined() {
	return sbf_check_session() && isset($_SESSION['login']) && $_SESSION['login'];
}

function sbf_verify() {
	if (!sbf_check_session()) {
		sbf_recreate_session();
		return false;
	}
	return true;
}

function sbf_check_password($nick, $password) {
	return sbf_check_password_db($nick, $password);
}

?>