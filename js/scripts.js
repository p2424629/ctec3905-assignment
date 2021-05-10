/* eslint-disable import/extensions */
// /* eslint-disable */
import {
  scrollToTopHandle,
  hideMenu,
  hideSpinner,
  getObject,
  buildResults,
  clear,
  errorHandling,
} from './functions.js';

// Define variables.
const seriesInfo = document.getElementById('tvShows');
const pages = document.querySelector('.pages');
const next = document.getElementById('next');
const prev = document.getElementById('prev');
const main = document.querySelector('.main');

// Display the tv-shows.
async function createOutput(pageNumber) {
  clear(seriesInfo);

  try {
    const obj = await getObject(pageNumber, 'on_the_air');
    obj.results.forEach((show) => {
      buildResults(show);
    });
    hideSpinner();
    // Show the pages buttons after tvShows are listed.
    const totalPages = obj.total_pages;
    if (totalPages < 2) {
      pages.style.display = 'none';
    } else {
      pages.style.display = 'flex';
    }
    if (pageNumber >= 2) {
      prev.style.display = 'block';
    } else if (pageNumber === totalPages) {
      next.style.display = 'none';
    } else if (pageNumber === 1) {
      prev.style.display = 'none';
    }
  } catch (error) {
    errorHandling(error, main);
  }
}

const onFirstLoad = async () => {
  const results = await createOutput(1);
  return results;
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

// Page Number
let pageNum = 1;
// Get "prev" id and goes backwards one page.
prev.addEventListener('click', () => {
  pageNum -= 1;
  window.scrollTo(0, 0);
  createOutput(pageNum);
});

// Get "next" id and forwards one page.
next.addEventListener('click', () => {
  pageNum += 1;
  window.scrollTo(0, 0);
  createOutput(pageNum);
});

const burger = document.querySelector('.nav-toggle-label');

burger.addEventListener('click', () => {
  burger.classList.toggle('active');
});
