import {addCopy} from './lib/social';
import {initVisualisation, visualise} from './lib/visualisation';

addCopy(decodeURIComponent(document.URL), '#social');

initVisualisation();
visualise(window.data);

window.addEventListener('resize', () => {
	requestAnimationFrame(() => {
		visualise(window.data);
	});
});
