// https://developers.google.com/web/updates/2015/04/cut-and-copy-commands?hl=en
function copyStuff(button, output) {
	let range = document.createRange();
	range.selectNode(output);
	window.getSelection().removeAllRanges();
	window.getSelection().addRange(range);

	try {
		// Now that we've selected the anchor text, execute the copy command
		const copy = document.execCommand('copy');
		log((copy ? '✅' : '❌'));
	} catch (err) {
		log('❌');
	}

	function log(text) {
		const old = button.textContent;
		button.textContent = text;
		setTimeout(() => {
			button.textContent = old;
		}, 2000);
	}
}

/**
 * Adds copyable input for an url
 * @param content the content to copy (location.href for example)
 * @param selector where to append the input and button
 */
export function addCopy(content, selector) {
	const copyBtn = document.createElement('button');
	const textInput = document.createElement('div');
	const container = document.querySelector(selector);

	textInput.textContent = content;
	copyBtn.textContent = '🔗';

	textInput.className = 'social--url';
	copyBtn.className = 'social--copy';

	copyBtn.disabled = !document.queryCommandSupported('copy');
	copyBtn.addEventListener('click', () => {
		copyStuff(copyBtn, textInput);
	});

	textInput.addEventListener('click', () => {
		let range = document.createRange();
		range.selectNode(textInput);
		window.getSelection().addRange(range);
	});

	container.appendChild(textInput);
	container.appendChild(copyBtn);
}
