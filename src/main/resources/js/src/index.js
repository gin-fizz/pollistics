import 'details-polyfill';

import {setSlug} from './lib/set-slug';
import {getCombo} from './lib/meme-words';
import {addNewPollInput} from './lib/poll-inputs';


addNewPollInput();

document.getElementById('slug').value = getCombo();

const titleInput = document.querySelector('.poll--title input');

titleInput.addEventListener('input', setSlug);
