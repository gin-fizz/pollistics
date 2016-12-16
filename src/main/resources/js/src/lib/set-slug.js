export function setSlug(ev) {
	let raw = ev.target.value;
	raw = raw.replace(/\?|\./g, '');
	document.getElementById('slug').value = raw.replace(/\s|#|\/|:/g, '-').toLocaleLowerCase();
}
