<?php
header("Content-type: image/png");

/**
* Настройки
*/
$host = "sbfmc.net"; // IP сервера
$port = 25565; // PORT сервера
$cache_folder = "cache"; // Папка кеша
$time_update_cache = 60; // Время обновления картинки
 
$width = 200;
$height = 20; 

/**
* Функции
*/
function draw_monitoring($online = 1, $max_online = 21, $img) {
	global $width;
	global $height;

	// $img = imagecreatetruecolor($width, $height);
	$d = imagecolorexact($img, 0, 0, 0);

	if ($online != 'f') {
		$length = $online * ($width / $max_online);
		draw_gradient($img, $length, 60, 215, 60);
		imagestring($img, 5, 80, 2, "$online/$max_online", $d);	
	} else {
		draw_gradient($img, $width, 242, 61, 61);
		imagestring($img, 5, 70, 2, "offline", $d);  
	}
	return $img;
}

function draw_gradient($img, $length, $red, $green, $blue) {
	global $height;

	imagefill($img, 0, 0, imagecolorallocate($img, 230, 220, 230));

	$s = 3; // Сила градиента
	$color = imagecolorallocate($img, $red, $green, $blue);

	for ($y = 0; $y < $height; $y++) {
		// Рисование пикселя
		for ($x = 0; $x < $length; $x++) {
			imagesetpixel($img, $x, $y, $color);
		}

		// Изменение цвета
		$red -= $s;
		$green -= $s;
		$blue -= $s;

		if ($red < 0) {
			$red = 0;
		}
		if ($green < 0) {
			$green = 0;
		}
		if ($blue < 0) {
			$blue = 0;
		}

		$color = imagecolorallocate($img, $red, $green, $blue);
	}
}

function online($host, $port){
	$socket = @fsockopen($host, $port);
	if ($socket !== false) {
		@fwrite($socket, "\xFE");
		$data = "";
		$data = @fread($socket, 1024);
		@fclose($socket);
		if ($data !== false && substr($data, 0, 1) == "\xFF") {
			$info = explode("\xA7", mb_convert_encoding(substr($data,1), "iso-8859-1", "utf-16be"));
			return $info;
		}
	} else {
		return 'offline';
	}
}

function print_monitoring() {
	global $host;
	global $port;
	global $cache_folder;
	global $time_update_cache;

	if(!@file_exists($cache_folder)) {
		@mkdir($cache_folder);
	}

	$file_list = glob("./cache/*.png"); // Список файлов
	$file_list_size = count($file_list); // Размер списка файлов
	$new_cache_time = time(); // Текущее время
	$old_cache_time = false; // Время кеша

	for ($i = 0; $i < $file_list_size; $i++) {
		if (strpos($file_list[$i], "$host:$port ") !== false) {
			$strings = explode(" ", str_replace(".png", '', str_replace("./cache/", '', $file_list[$i])));
			$old_cache_time = $strings[1];

			if ($new_cache_time - $old_cache_time >= $time_update_cache) {
				unlink($file_list[$i]);
				$old_cache_time = false;
			}
		}
	}

	if ($old_cache_time == false) {
		$online = online($host, $port);
		$img = draw_monitoring($online[1], $online[2], imagecreatetruecolor(200, 20));
		imagepng($img,"./cache/$host:$port $new_cache_time.png");
		imagepng($img);
		imagedestroy($img);
	} else {
		$img = imagecreatefrompng("./cache/$host:$port $old_cache_time.png");
		imagepng($img);
		imagedestroy($img);
	}
}

/**
* Отдача картинки
*/
print_monitoring();
?>