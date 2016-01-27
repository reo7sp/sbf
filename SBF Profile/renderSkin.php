<?php
	$scale = isset($_GET['scale']) ? (($_GET['scale'] < 32) ? (($_GET['scale'] > 1) ? $_GET['scale'] : 1) : 32) : 18;
	$nick = isset($_GET['nick']) ? $_GET['nick'] : 'char';

	function get_skin($nick = 'char') {
	    $ch = curl_init();
	    curl_setopt($ch, CURLOPT_URL, 'http://sbfmc.net/McSkins/MinecraftSkins/' . $nick . '.png');
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	    curl_setopt($ch, CURLOPT_TIMEOUT, 5);
	    $output = curl_exec($ch);
	    $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	    curl_close($ch);

	    if($status != '200') {
	        $ch = curl_init();
	        curl_setopt($ch, CURLOPT_URL, 'http://skins.minecraft.net/MinecraftSkins/' . $nick . '.png');
	        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	        curl_setopt($ch, CURLOPT_TIMEOUT, 5);
	        $output = curl_exec($ch);
	        $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	        curl_close($ch);

	        if ($status!='200') {
	            // Default Avatar: http://www.minecraft.net/skin/char.png
	            $output = 'R0lGODlhMAAQAPUuALV7Z6p9ZkUiDkEhDIpMPSgcC2pAMFI9ibSEbZxpTP///7uJciodDTMkEYNVO7eCcpZfQJBeQ5xjRkIdCsaWgL2OdL';
	            $output .= '6IbL2OcqJqRyweDj8qFXpOMy8fDyQYCC8gDUIqEiYaCraJbL2Lco9ePoBTNG1DKpxyXK2AbbN7Yqx2WjQlEoFTOW9FLCseDQAAAAAAAAA';
	            $output .= 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH/C1hNUCBEYXRhWE1QRD94cDIzRThDRkQwQzcyIiB4';
	            $output .= 'bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkU2RTVBQzAwMDFwYWNrZXQgZW5kPSJyIj8+ACH5BAUAAC4ALAAAAAAwABAAQAZkQJdwSCwaj';
	            $output .= '8ik0uVpcQodUIuxrFqv2OwRoTgAFgdFQEsum8/ocit0oYgqKVVaG4EMCATBaDXv+/+AgYKDVS2GDR8aGQWESAEIAScmCwkJjUcSKA8GBh';
	            $output .= 'YYJJdGLCUDEwICDhuEQQA7';
	            $output = base64_decode($output);
	        }
	    }
	    return $output;
	}

	function image_create_transparent($x, $y) {
	    $imageOut = imagecreatetruecolor($x, $y);
	    imagealphablending($dst, true);
	    imagesavealpha($dst, true);
	    $colourBlack = imagecolorallocate($imageOut, 0, 0, 0);
	    imagecolortransparent($imageOut, $colourBlack);
	    return $imageOut;
	}

	$skin = get_skin($nick);

	$src = imagecreatefromstring($skin);
	$dst = image_create_transparent(16 * $scale, 32 * $scale);

	imagecopyresized($dst, $src, 4 * $scale, 0 * $scale, 8, 8, 8 * $scale, 8 * $scale, 8, 8); // head
	imagecopyresized($dst, $src, 4 * $scale, 0 * $scale, 40, 8, 8 * $scale, 8 * $scale, 8, 8); // accessories
	imagecopyresized($dst, $src, 4 * $scale, 8 * $scale, 20, 20, 8 * $scale, 12 * $scale, 8, 12); // body
	imagecopyresized($dst, $src, 4 * $scale, 20 * $scale, 4, 20, 4 * $scale, 12 * $scale, 4, 12); // left leg
	imagecopyresized($dst, $src, 8 * $scale, 20 * $scale, 4, 20, 4 * $scale, 12 * $scale, 4, 12); // right leg
	imagecopyresized($dst, $src, 0 * $scale, 8 * $scale, 44, 20, 4 * $scale, 12 * $scale, 4, 12); // left arm
	imagecopyresized($dst, $src, 12 * $scale, 8 * $scale, 44, 20, 4 * $scale, 12 * $scale, 4, 12); // right arm

	header('Content-type: image/png');
	imagepng($dst);
	imagedestroy($src);
	imagedestroy($dst);
?>
