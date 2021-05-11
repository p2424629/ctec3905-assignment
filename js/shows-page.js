//API STUFF.
/*eslint-disable */
import {
  API_KEY,
  API_URL,
  IMG_URL,
  POSTER_SIZE
} from './config.js';
import {
  scrollToTopHandle,
  hideMenu,
  hideSpinner,
  getObject,
  buildResults,
  clear,
  errorHandling,
} from './functions.js';

//Spinner.
const spinner = document.querySelector('.spinner');
const container = document.querySelector('.container');
spinner.style.display = 'none';
container.style.display = 'none';

const buildShowResults = (showInfo, videoUrl, actors, externals) => {
  const genres = showInfo.genres.map((ele) => {
    return ele.name;
  });
};

const onFirstLoad = async () => {
  // showId from url parameters
  const url = new URLSearchParams(window.location.search);
  const showId = url.get('showId');

  const results = await Promise.all([
    getObject(null, showId),
    getObject(null, `${showId}/videos`),
    getObject(null, `${showId}/credits`),
    getObject(null, `${showId}/external_ids`),
  ]);

  const showInfo = results[0];

  const videoUrl = results[1];
  const actors = results[2].cast.map((ele) => {
    return {
      name: ele.name,
      picture: ele.profile_path,
      character: ele.character,
      actorId: ele.id,
    };
  });

  const externals = results[3];

  buildShowResults(showInfo, videoUrl, actors, externals);
  console.log(results);
  console.log(showInfo);
  console.log(videoUrl);
  console.log(actors);
  console.log(externals);
};
const initApp = () => {
  document.addEventListener('scroll', scrollToTopHandle);
  window.addEventListener('scroll', hideMenu);
  // main.addEventListener('mousedown', hideMenu);
  onFirstLoad();
};

document.addEventListener('readystatechange', (e) => {
  if (e.target.readyState === 'complete') {
    initApp();
  }
});
const burger = document.querySelector('.nav-toggle-label');

burger.addEventListener('click', () => {
  burger.classList.toggle('active');
});