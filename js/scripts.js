/* eslint-disable import/extensions */
// /* eslint-disable */
import { IMG_URL } from './config.js';
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
const pageIndicator = document.getElementById('pageNumber');

// Display the tv-shows.
async function createOutput(pageNumber) {
  clear(seriesInfo);

  try {
    const obj = await getObject(pageNumber, 'on_the_air');
    obj.results.forEach((show) => {
      buildResults(show);
    });
    hideSpinner();
    pageIndicator.textContent = pageNumber;
    randomImg(obj);
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
    errorHandling(error);
  }
}

const onFirstLoad = async () => {
  // Set the page number parameter on the url
  const url = new URL(window.location.href);
  let page = url.searchParams.get('page');
  if (page <= 0) page = 1;
  if (!page) page = 1;
  url.searchParams.set('page', page);
  window.history.replaceState(null, null, url);
  return createOutput(page);
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

const randomImg = (obj) => {
  let randomImageContainer = document.querySelector('.randomImageContainer');
  if (randomImageContainer) {
    clear(randomImageContainer);
  } else {
    randomImageContainer = document.createElement('div');
    randomImageContainer.classList.add('randomImageContainer');
  }
  const cleanObj = obj.results.filter((res) => res.backdrop_path);
  const randomShow =
    cleanObj[Math.floor(Math.random() * cleanObj.length)] || cleanObj[0];
  const randomImage = document.createElement('div');
  randomImage.classList.add('randomImage');
  randomImage.style = `--url: url('${IMG_URL}w1280${randomShow.backdrop_path}')`; // Passing arguments to css file from JS.
  const pContainer = document.createElement('div');
  pContainer.classList.add('pContainer');
  const randomTitle = document.createElement('h4');
  randomTitle.textContent = randomShow.name;
  const randomParagraph = document.createElement('p');
  randomParagraph.classList.add('randomParagraph');
  randomParagraph.textContent = randomShow.overview;
  pContainer.append(randomTitle, randomParagraph);
  randomImage.appendChild(pContainer);
  randomImageContainer.appendChild(randomImage);
  const parent = document.querySelector('.main').parentNode;
  parent.insertBefore(randomImageContainer, main);
};

function addPageToUrl(page) {
  const url = new URL(window.location.href);
  url.searchParams.set('page', page);
  window.history.replaceState(null, null, url);
}

// Page Number
let pageNum = 1;
// Get "prev" id and goes backwards one page.
prev.addEventListener('click', () => {
  pageNum -= 1;
  if (pageNum <= 0) pageNum = 1;
  window.scrollTo(0, 0);
  pageIndicator.textContent = pageNum;
  addPageToUrl(pageNum);
  createOutput(pageNum);
});

// Get "next" id and forwards one page.
next.addEventListener('click', () => {
  pageNum += 1;
  window.scrollTo(0, 0);
  pageIndicator.textContent = pageNum;
  addPageToUrl(pageNum);
  createOutput(pageNum);
});

const burger = document.querySelector('.nav-toggle-label');

burger.addEventListener('click', () => {
  burger.classList.toggle('active');
});
