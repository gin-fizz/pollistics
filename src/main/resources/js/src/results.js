import {addCopy} from './lib/social';
// import {poll} from './lib/long-polling.js';

// put in this callback a method that will repaint the grap
// if possible it should only repaint if the data is different
//
// poll(`/api/1/polls/${window.id}`, (data) => {
// 	console.log(data);
// }, 1000);

addCopy(decodeURIComponent(document.URL), '#social');
