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

export function getWord() {
	return words[Math.floor(Math.random() * words.length)];
}

export function getCombo() {
	return `${getWord()}-${getWord()}-${getWord()}`;
}
