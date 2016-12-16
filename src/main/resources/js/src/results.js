import {addCopy} from './lib/social';
import {initVisualisation, visualise} from './lib/visualisation';
import {poll} from './lib/long-polling.js';

addCopy(decodeURIComponent(document.URL), '#social');

initVisualisation();
visualise(window.data);

window.addEventListener('resize', () => {
	requestAnimationFrame(() => {
		visualise(window.data);
	});
});

poll(`/api/1/polls/${window.id}`, (data) => {
	window.data = [];
	for (let k in data.options) {
		window.data.push({label: k, value: data.options[k]});
	}
	visualise(window.data);
}, 1000);
