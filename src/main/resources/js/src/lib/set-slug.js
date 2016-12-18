export function setSlug(ev) {
	let raw = ev.target.value;
	raw = raw.replace(/\?|\./g, '');
	raw = raw.replace(/\s|#|\/|:|,/g, '-');
	raw = raw.replace(/-+/g, '-');
	document.getElementById('slug').value = raw.toLocaleLowerCase();
}
