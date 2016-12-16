import {addCopy} from './lib/social';
import {initVisualisation, visualise} from './lib/visualisation';

addCopy(decodeURIComponent(document.URL), '#social');

initVisualisation();
visualise(window.data);

window.addEventListener('resize', function(){
	requestAnimationFrame(() => {
		visualise(window.data);
	});
});
