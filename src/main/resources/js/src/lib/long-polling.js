import 'whatwg-fetch';

function checkStatus(response) {
	if (response.status >= 200 && response.status < 300) {
		return response
	} else {
		var error = new Error(response.statusText)
		error.response = response
		throw error
	}
}

function parseJSON(response) {
	return response.json()
}

export function poll(url, callback, timeout = 1000) {
	fetch(url)
		.then(checkStatus)
		.then(parseJSON)
		.then((data) => {
			callback(data);
			setTimeout(() => {
				poll(url, callback, timeout);
			}, timeout);
		});
}
