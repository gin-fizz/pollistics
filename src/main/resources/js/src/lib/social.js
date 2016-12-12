// https://developers.google.com/web/updates/2015/04/cut-and-copy-commands?hl=en
function copyStuff(button, output) {
	let range = document.createRange();
	range.selectNode(output);
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

	// Remove the selections - NOTE: Should use
	// removeRange(range) when it is supported
	window.getSelection().removeAllRanges();
}

/**
 * Adds copyable input for an url
 * @param content the content to copy (location.href for example)
 * @param selector where to append the input and button
 */
export function addCopy(content, selector) {
	const copyBtn = document.createElement('button');
	const textInput = document.createElement('input');
	const container = document.querySelector(selector);

	textInput.value = content;
	textInput.disabled = true;
	copyBtn.textContent = '🔗';

	copyBtn.className = 'social--copy';

	copyBtn.disabled = !document.queryCommandSupported('copy');
	copyBtn.addEventListener('click', () => {
		copyStuff(copyBtn, textInput);
	});

	container.appendChild(textInput);
	container.appendChild(copyBtn);
}
