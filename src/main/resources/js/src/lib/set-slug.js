export function setSlug(ev) {
	const raw = ev.target.value;
	document.getElementById('slug').value = raw.replace(/\s/g, '-').toLocaleLowerCase();
}
