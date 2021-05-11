/* eslint-disable operator-linebreak */
/* eslint-disable import/extensions */
// API STUFF
import {
  getObject,
  buildResults,
  clear,
  infoText,
  scrollToTopHandle,
  hideMenu,
  notification,
  errorHandling,
  hideSpinner,
} from './functions.js';

import {
  API_KEY,
  API_URL,
} from './config.js';

const seriesInfo = document.getElementById('tvShows');
const main = document.querySelector('.main');
const removeAllTvShows = document.getElementById('removeAllTvShows');
const randomTvShows = document.getElementById('randomTvShows');

async function recommendTvShows() {
  // Get random year on each call, between 2000 - current year.
  const minYear = 2000;
  const maxYear = new Date().getFullYear();
  const recommendedYear =
    Math.floor(Math.random() * (maxYear - minYear + 1)) + minYear;

  // Get random page on each call.
  const minPage = 1;
  const maxPage = 10;
  const recommendedPage = Math.floor(
    Math.random() * (maxPage - minPage + 1) + minPage,
  );
  const url = `${API_URL}discover/tv?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc&
  first_air_date_year=${recommendedYear}&page=${recommendedPage}&
  vote_average.gte=7&include_null_first_air_dates=false&include_adult=false`;
  try {
    clear(seriesInfo);
    const obj = await getObject(null, '', url);
    const response = obj.results;
    response.slice(0, Math.floor(response.length / 1.5)).forEach((show) => {
      buildResults(show);
    });
  } catch (error) {
    errorHandling(error);
  }
}

// DISPLAY WATCHLISTS ON PAGE LOAD.
const onFirstLoad = async () => {
  // TV SHOWS
  clear(seriesInfo);
  const favoriteTvShows =
    JSON.parse(localStorage.getItem('favoriteSeries')) || [];

  if (favoriteTvShows.length !== 0) {
    // Display "Clear List" button and remove "Shuffle".
    removeAllTvShows.style.display = 'block';
    randomTvShows.style.display = 'none';

    favoriteTvShows.forEach(async (id) => {
      try {
        const obj = await getObject(null, id);
        buildResults(obj, 'favorites');
      } catch (error) {
        errorHandling(error);
      }
    });
  } else {
    hideSpinner();
    removeAllTvShows.style.display = 'none';
    randomTvShows.style.display = 'block';
    infoText(
      'There are no favorite tv shows stored! Go watch some. Here are some recommendations',
      document.querySelector('.pageTitle'),
    );
    recommendTvShows();
  }
};
const initApp = () => {
  document.addEventListener('scroll', scrollToTopHandle);
  window.addEventListener('scroll', hideMenu);
  main.addEventListener('mousedown', hideMenu);
  onFirstLoad();
};

document.addEventListener('readystatechange', (e) => {
  if (e.target.readyState === 'complete') {
    initApp();
  }
});

// Remove all tv shows.
removeAllTvShows.addEventListener('click', () => {
  localStorage.removeItem('favoriteSeries');
  // Notification that all tv shows are removed from watchlist.
  notification('Removed all favorite tv shows !', 'alreadyStored');
  setTimeout(() => {
    if (document.URL.includes('favorites')) window.location.reload();
  }, 1600);
});

randomTvShows.addEventListener('click', () => {
  recommendTvShows();
  // Notification that recommendations are shuffled.
  notification('Shuffled recommendations !', 'added');
});

const burger = document.querySelector('.nav-toggle-label');

burger.addEventListener('click', () => {
  burger.classList.toggle('active');
});
