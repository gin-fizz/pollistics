var allOptions = document.querySelectorAll('.poll--option');
var lastOption = allOptions[allOptions.length - 2];
var i = 3;
var lastInput = lastOption.querySelector('input');
var createPollFrm = document.getElementById('createPollForm');

function addNewPollInput () {
	var newOption = document.createElement('div');
	newOption.classList.add('poll--option');
	newOption.innerHTML = `<label for="option${i}">Option ${i + 1}</label><input type="text" name="option${i}" id="option${i}" />`;
	createPollFrm.insertBefore(newOption,lastOption.nextSibling);
	lastInput.removeEventListener('focus',addNewPollInput);
	allOptions = document.querySelectorAll('.poll--option');
	lastOption = allOptions[allOptions.length - 2];
	lastInput = lastOption.querySelector('input');
	lastInput.addEventListener('focus', addNewPollInput);
	i++;
}
lastInput.addEventListener('focus', addNewPollInput);
