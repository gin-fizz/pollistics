var words = [
	'meme',
	'bullgit',
	'yolo',
	'swag',
	'memer',
	'coolio',
	'fun',
	'quark',
	'odisee',
	'lel',
	'xddd',
	'giga',
	'wauw',
	'poll',
	'paul',
	'maliek',
	'hanne',
	'elias',
	'ruben',
	'haroen',
	'peter',
	'katja',
	'kevin',
	'ok',
	'leuk'
];

function getRandom() {
	return words[Math.floor(Math.random() * words.length)];
}
function getCombo() {
	return `${getRandom()}-${getRandom()}-${getRandom()}`;
}

document.getElementById('slug').value = getCombo();
