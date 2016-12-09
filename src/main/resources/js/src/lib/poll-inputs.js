const options = document.querySelector('.options');
let allOptions = options.querySelectorAll('.poll--option');
let lastOption = allOptions[allOptions.length - 1];
let lastInput = lastOption.querySelector('input');
let i = 2; // optionI

export function addNewPollInput() {
	const newOption = document.createElement('div');
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
