const logout = document.getElementById('logout');

logout.addEventListener('click', (e) => {
	httpPost('/logout')
		.then((response) => {
			console.log(response);
		})
		.catch((err) => console.log(err));
});

function httpPost(url) {
	return new Promise((resolve, reject) => {
		var request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (this.status === 200) {
				resolve(this.response);
			} else {
				reject(new Error(this.statusText));
			}
		};
		request.onerror = function () {
			reject(new Error('XMLHttpRequest Error: '+ this.statusText));
		};
		request.open('POST', url);
		request.send();
	});
}

