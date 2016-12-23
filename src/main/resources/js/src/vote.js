import {addCopy} from './lib/social';
import {getCookie} from './lib/cookie';

addCopy(decodeURIComponent(document.URL), '#social');

const slug = location.pathname.split('/')[1];
if (getCookie('pollistics-voted').includes(slug)) {
    const info = document.createElement('p');
    info.innerHTML = `<strong>Oops</strong>, you already voted for this poll. Check out the <a href="https://pollistics.com/${slug}/results">results</a>`;
    info.className = 'message error';
    info.style.marginLeft = 0;
    info.style.marginRight = 0;
    document.querySelector('.vote--container').appendChild(info);
}
