import {addCopy} from './lib/social';
//import {poll} from './lib/long-polling.js';

addCopy(location.href, '#social');

/*
 poll(`/api/1/polls/${window.id}`, (data) => {
 console.log(data);
 }, 1000);
 */
