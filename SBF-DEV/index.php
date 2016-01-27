<?php

require_once('backend/template.php');

echo '<html>';
echo '<head>';
sbf_print_head();
echo '</head>';
echo '<body>';
sbf_print_panel();
sbf_print_content('Главная страница', '

Hello, World!

');
echo '</body>';
echo '</html>';

?>