import {addCopy} from './lib/social';
import {visualise} from './lib/visualisation';

addCopy(decodeURIComponent(document.URL), '#social');
visualise(window.data);
