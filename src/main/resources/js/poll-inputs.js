var options = document.querySelector('.options');
var allOptions = options.querySelectorAll('.poll--option');
var lastOption = allOptions[allOptions.length - 1];
var lastInput = lastOption.querySelector('input');
var i = 2; // optionI

function addNewPollInput() {
	var newOption = document.createElement('div');
	newOption.classList.add('poll--option');
	newOption.innerHTML = `<label for="option${i}">Option ${i + 1}</label><input type="text" name="option${i}" id="option${i}" />`;

	options.appendChild(newOption);
	lastInput.removeEventListener('input',addNewPollInput);

	allOptions = options.querySelectorAll('.poll--option');
	lastOption = allOptions[allOptions.length - 1];
	lastInput = lastOption.querySelector('input');

	lastInput.addEventListener('input', addNewPollInput);
	i++;
}

addNewPollInput();

function setSlug(ev) {
	var raw = ev.target.value;
	document.getElementById('slug').value = raw.replace(/\s/g, '-').toLocaleLowerCase();
}

var titleInput = document.querySelector('.poll--title input');
titleInput.addEventListener('input', setSlug);
