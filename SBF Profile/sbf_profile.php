<?php
	/*
	Plugin Name: SBF Profile
	Plugin URI: http://sbfmc.net
	Description: SBF Profile 
	Author: Reo_SP
	Version: 1.0
	*/

	function sbf_print_profile() {
		$current_user = wp_get_current_user();

		if ($current_user->ID != 0) {
			$changeskin_output = '';
			if (isset($_POST['skins_btn'])) {
				$changeskin_output = sbf_change_skin();
			}
			echo '
				<img style="float: left;" src="http://sbfmc.net/site/wp-content/plugins/SBF%20Profile/renderSkin.php?nick=' . htmlspecialchars($current_user->user_login) . '&scale=15">
				<style type="text/css">
					#settings {
						float: right;
					}
				</style>
				<div id="settings">
					<h3>Изменить скин</h3>
					' . $changeskin_output . '
					<form action="' . $_SERVER['REQUEST_URI'] . '" method="post" enctype="multipart/form-data">
				';

			wp_nonce_field('sbf_print_profile', 'sbf_nonce_field');

			echo '
						Скин: <input type="file" name="fileskin"><br>
						<input type="submit" name="skins_btn" value="Загрузить">
					</form>
					<br>
					<h3>Другие настройки</h3>
					<form action="http://sbfmc.net/site/wp-admin/profile.php">
						<input type="submit" value="Перейти">
					</form>
				</div>
			';
		} else {
			echo '
				Ошибка: Вы не зашли!
			';
		}
	}

	function sbf_change_skin() {
		$current_user = wp_get_current_user();
		if (empty($_POST) || !wp_verify_nonce($_POST['sbf_nonce_field'], 'sbf_print_profile') || $current_user->ID == 0) {
			return 'Ошибка: Вы не зашли!';
		}

		$changeskin_output = '';

		$changeSkin = true;
		$name = $current_user->user_login;
		
		$skindir = '/var/www/sbf/data/www/sbfmc.net/McSkins/MinecraftSkins';

		// check params
		if (!$name) {
			return 'Ошибка: Вы не зашли!';
		}
		if (!is_uploaded_file($_FILES['fileskin']['tmp_name'])){
			$changeSkin = false;
		} else {
			$imageskininfo = getimagesize($_FILES['fileskin']['tmp_name']);
		}
		
		// check format and size
		if ($changeSkin) {
			if ($_FILES['fileskin']['type'] != 'image/png'){
				$changeskin_output = $changeskin_output . 'Скин не в формате png!';
				$changeSkin = false;
			} else if ($imageskininfo['0'] != '64' || $imageskininfo['1'] != '32'){
				$changeskin_output = $changeskin_output . 'Скин должен быть размером 64x32!';
				$changeSkin = false;
			}
		}
		
		// load skin
		if ($changeSkin) {
			move_uploaded_file($_FILES['fileskin']['tmp_name'], $skindir . '/' . $name . '.png');
			$changeskin_output = $changeskin_output . 'Скин успешно загружен!';
		}

		// return output
		return $changeskin_output;
	}

	/*
	require_once('interkassa.php');

	register_activation_hook( __FILE__, 'morkovin_install');
	register_deactivation_hook( __FILE__, 'morkovin_uninstall');

	add_action('admin_menu', 'morkovin_add_admin_pages');
	add_action( 'init', 'morkovin_run' );
	 */
?>