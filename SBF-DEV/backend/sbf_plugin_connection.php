<?php

class SBF_Plugin_Connection {
	private static $IP = 'sbfmc.net';
	private static $PORT = 17879;
	private static $SECRET = 838823003;

	private $socket;

	public function connect() {
		if (!($this->socket = fsockopen(self::$IP, self::$PORT))) {
			throw new Exception('Connection refused');
		}
		socket_set_timeout($this->socket, 5);

		$this->send_request('HEADER SBF-DEV-SITE');
		$this->send_request('SECRET ' . self::$SECRET);
	}

	public function read_response() {
		return fgets($this->socket);
	}

	public function send_request($request) {
		fwrite($this->socket, $request . "\n");
	}

	public function disconnect($reason = '') {
		$this->send_request('DISCONNECT ' . $reason);
		fclose($this->socket);
	}
}

?>