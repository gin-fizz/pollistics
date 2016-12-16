import {addCopy} from './lib/social';
import {visualise} from './lib/visualisation';

addCopy(decodeURIComponent(document.URL), '#social');

visualise(window.data);

// todo: make visualise a function that *replaces* the old vis
//window.addEventListener('resize', function(){
//	requestAnimationFrame(() => {
//		visualise(window.data);
//	});
//});
