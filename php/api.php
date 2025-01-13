<?php
// Enable CORS
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
header("Content-Type: application/json");

// Load configuration
$config = require_once 'config.php';

try {
	$pdo = new PDO(
		"mysql:host={$config['database']['host']};dbname={$config['database']['name']}",
		$config['database']['username'],
		$config['database']['password']
	);
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	http_response_code(500);
	echo json_encode(['error' => 'Database connection failed']);
	exit;
}

// Handle OPTIONS request for CORS
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
	http_response_code(200);
	exit;
}

// Route handling
$request_method = $_SERVER['REQUEST_METHOD'];
$request_path = $_SERVER['REQUEST_URI'];

if (preg_match('/\/api\/message\/?$/', $request_path)) {
	switch ($request_method) {
		case 'GET':
			getMessages();
			break;
		case 'POST':
			createMessage();
			break;
		default:
			http_response_code(405);
			echo json_encode(['error' => 'Method not allowed']);
			break;
	}
} else {
	http_response_code(404);
	echo json_encode(['error' => 'Not found']);
}

function getMessages()
{
	global $pdo;

	try {
		$stmt = $pdo->query("SELECT id, timestamp as creationTime, timestamp as lastUpdateTime, ip, message FROM messageentity ORDER BY timestamp DESC");
		$messages = $stmt->fetchAll(PDO::FETCH_ASSOC);

		echo json_encode($messages);
	} catch (PDOException $e) {
		http_response_code(500);
		echo json_encode(['error' => 'Failed to fetch messages']);
	}
}

function createMessage()
{
	global $pdo;

	try {
		$data = json_decode(file_get_contents('php://input'), true);

		if (!isset($data['message'])) {
			http_response_code(400);
			echo json_encode(['error' => 'Message is required']);
			return;
		}

		$stmt = $pdo->prepare("INSERT INTO messageentity (message, ip, timestamp) VALUES (?, ?, NOW())");
		$ip = $_SERVER['REMOTE_ADDR'];
		$stmt->execute([$data['message'], $ip]);

		http_response_code(201);
		echo json_encode([
			'id' => $pdo->lastInsertId(),
			'message' => $data['message'],
			'ip' => $ip,
			'creationTime' => date('Y-m-d\TH:i:s.u'),
			'lastUpdateTime' => date('Y-m-d\TH:i:s.u')
		]);
	} catch (PDOException $e) {
		http_response_code(500);
		echo json_encode(['error' => 'Failed to create message']);
	}
}

