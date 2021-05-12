/* eslint-disable import/extensions */
/* eslint-disable camelcase */
import { API_KEY, API_URL, IMG_URL, POSTER_SIZE } from './config.js';

// Take the user to detailed tv show info page.
const showSelected = async (event) => {
  sessionStorage.setItem('showId', event.currentTarget.showId);
  window.open(
    `../pages/shows-page.html?showId=${event.currentTarget.showId}`,
    '_blank'
  );
  return false;
};

export const scrollToTopHandle = () => {
  const scrollToTop = document.querySelector('.scrollToTop');

  // Show ScrollToTop button after scrolling.
  const scrollableHeight =
    document.documentElement.scrollHeight -
    document.documentElement.clientHeight;
  const GOLDEN_RATIO = 0.1;
  if (document.documentElement.scrollTop / scrollableHeight > GOLDEN_RATIO) {
    // show button
    if (!scrollToTop.classList.contains('scrollButtonActive')) {
      scrollToTop.classList.add('scrollButtonActive');
    }
  } else if (scrollToTop.classList.contains('scrollButtonActive')) {
    // hide button
    scrollToTop.classList.remove('scrollButtonActive');
  }

  // SMOOTH SCROLLING
  scrollToTop.addEventListener('click', () => {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth',
    });
  });
};

export const hideMenu = () => {
  const menuOpen = document.querySelector('.nav-toggle').checked;
  if (menuOpen === true) {
    document.querySelector('.nav-toggle').checked = false;
  }
};

export const hideSpinner = () => {
  const spinner = document.querySelector('.spinner');
  spinner.style.display = 'none';
};
export const notification = (msg = '', type = '') => {
  if (!msg || !type) return;
  const notify = document.getElementById(type);
  notify.textContent = msg;
  notify.classList.add(type);
  setTimeout(() => {
    notify.classList.remove(type);
  }, 1500);
};

export const clear = (element) => {
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
};

// Add tv show to favorite tv shows.
const addFavorite = (id) => {
  const storedId = JSON.parse(localStorage.getItem('favoriteSeries')) || [];
  if (storedId.indexOf(id) === -1) {
    storedId.push(id);
    localStorage.setItem('favoriteSeries', JSON.stringify(storedId));
    // Notification that added to Favorites.
    notification('Added to Favorites !', 'added');
  } else {
    notification('Already stored in Favorites !', 'alreadyStored');
  }
};

const removeFavorite = (id) => {
  const storedId = JSON.parse(localStorage.getItem('favoriteSeries')) || [];
  const index = storedId.indexOf(id);
  storedId.splice(index, 1);
  localStorage.setItem('favoriteSeries', JSON.stringify(storedId));
  notification('Removed from favorites !', 'alreadyStored');
  setTimeout(() => {
    if (document.URL.includes('favorites')) window.location.reload();
  }, 1600);
};

async function getData(url) {
  const response = await fetch(url);
  if (response.ok) return response.json();
  return response.json().then((responseJson) => {
    if (responseJson.errors) {
      throw new Error(
        `HTTP ${response.status} ${response.statusText}: ${responseJson.errors}`
      );
    }
    throw new Error(
      `HTTP ${response.status} ${response.statusText}: ${responseJson.status_message}`
    );
  });
}

export const getObject = async (pageNum = 1, loc = '', url = null) => {
  let uri = url;
  if (!uri) {
    uri = pageNum
      ? `${API_URL}tv/${loc}?api_key=${API_KEY}&language=en-US&page=${pageNum}&include_adult=false`
      : `${API_URL}tv/${loc}?api_key=${API_KEY}&language=en-US&include_adult=false`;
  }
  return getData(encodeURI(uri));
};

const createShowItem = (id, name, voteAverage, firstAirDate) => {
  const showTitle = document.createElement('h2');
  showTitle.classList.add('card-title');
  showTitle.textContent = name;

  const voteAvgContainer = document.createElement('p');
  voteAvgContainer.id = 'p_rating';
  voteAvgContainer.textContent = 'Rating:';
  const votingCalc = document.createElement('span');
  votingCalc.textContent = `${voteAverage} / 10`;
  const starIcon = document.createElement('i');
  starIcon.classList.add('material-icons', 'star');
  starIcon.textContent = 'star_rate';
  votingCalc.appendChild(starIcon);
  voteAvgContainer.appendChild(votingCalc);

  const airDateContainer = document.createElement('p');
  airDateContainer.textContent = 'First air date:';
  const airDate = document.createElement('span');
  airDate.textContent = firstAirDate;
  const dateIcon = document.createElement('i');
  dateIcon.classList.add('material-icons', 'date');
  dateIcon.textContent = 'date_range';
  airDate.appendChild(dateIcon);
  airDateContainer.appendChild(airDate);

  const detailsBtn = document.createElement('a');
  detailsBtn.classList.add('details-button');
  detailsBtn.textContent = 'Details';
  detailsBtn.href = '#';
  detailsBtn.showId = id;
  detailsBtn.addEventListener('click', showSelected);

  const showItem = document.createElement('div');
  showItem.classList.add('tvShow');
  showItem.append(showTitle, voteAvgContainer, airDateContainer, detailsBtn);

  return showItem;
};

const createAddBtn = (id) => {
  // Change the icon to "trash" for already favorite shows or "heart" for added to favorites.
  const addBtnContainer = document.createElement('div');
  addBtnContainer.classList.add('addBtn');
  const container = document.createElement('span');
  const icon = document.createElement('i');
  const storedId = JSON.parse(localStorage.getItem('favoriteSeries')) || [];
  if (storedId.indexOf(id) === -1) {
    icon.classList.add('material-icons', 'favorite');
    icon.textContent = 'favorite';
    container.appendChild(icon);
    addBtnContainer.addEventListener('click', () => {
      addFavorite(id);
      icon.style.color = 'red';
      // icon.classList.add('favorite', 'favoriteMarked');
    });
  } else {
    icon.classList.add('material-icons', 'trash');
    icon.textContent = 'delete';
    container.appendChild(icon);
    addBtnContainer.addEventListener('click', () => {
      removeFavorite(id);
    });
  }
  addBtnContainer.appendChild(container);
  return addBtnContainer;
};

const createImg = (poster_path, name) => {
  const imgContainer = document.createElement('div');
  imgContainer.classList.add('card_img');
  const imgSrc = document.createElement('img');
  imgSrc.src = poster_path
    ? `${IMG_URL}${POSTER_SIZE}${poster_path}`
    : '../images/image-not-found.jpg';
  imgSrc.alt = name;
  imgContainer.appendChild(imgSrc);
  return imgContainer;
};

export const buildResults = (obj, loc = '') => {
  try {
    const seriesInfo = document.getElementById('tvShows');
    const { id, name, vote_average, first_air_date, poster_path } = obj;
    const showItem = createShowItem(id, name, vote_average, first_air_date);
    const addBtn = createAddBtn(id, loc);
    const overlay = document.createElement('div');
    overlay.classList.add('overlay');
    overlay.append(addBtn, showItem);
    const img = createImg(poster_path, name);
    const card = document.createElement('div');
    card.classList.add('card');
    card.append(overlay, img);
    seriesInfo.appendChild(card);
    hideSpinner();
  } catch (error) {
    throw new Error(error);
  }
};

export const buildShowResults = (showInfo, videoUrl, actors, externals) => {
  try {
    const main = document.querySelector('.main-details');

    // Creating Rows for show details.
    const tvShowRow = document.createElement('div');
    tvShowRow.classList.add('tvShowRow');
    const castsRow = document.createElement('div');
    castsRow.classList.add('castsRow');
    const trailerRow = document.createElement('div');
    trailerRow.classList.add('trailerRow');
    const recommendationRow = document.createElement('div');
    recommendationRow.classList.add('recommendationRow');

    const [posterContainer, tvShowDetailsContainer] = detailsTvInfo(showInfo);

    const title = document.createElement('h4');
    title.textContent = 'Top Cast Actors';

    const actorsToShow = actors.splice(0, 8).map((ele) => {
      const img = ele.picture
        ? `${IMG_URL}w300${ele.picture}`
        : '..images/image-not-found.jpg';
      const actorCard = document.createElement('div');
      actorCard.classList.add('actorCard', 'col');
      const cardPanel = document.createElement('div');
      cardPanel.classList.add('cardPanel');
      const actorImg = document.createElement('img');
      actorImg.classList.add('actorImg');
      actorImg.src = img;
      actorImg.alt = `${ele.name}-poster`;
      const actorName = document.createElement('h6');
      actorName.classList.add('actorName');
      const smallSpan = document.createElement('span');
      const charName = document.createElement('h6');
      charName.classList.add('charName');
      if (ele.character) {
        actorName.textContent = ele.name;
        smallSpan.textContent = 'AS';
        charName.textContent = ele.character;
        cardPanel.append(actorImg, actorName, smallSpan, charName);
      } else {
        actorName.textContent = ele.name;
        cardPanel.append(actorImg, actorName);
      }
      actorCard.appendChild(cardPanel);
      return actorCard;
    });

    castsRow.append(title, ...actorsToShow);
    // Appending elements to rows.
    tvShowRow.append(posterContainer, tvShowDetailsContainer);
    // main.append(tvShowRow, castsRow, trailerRow, recommendationRow);
    main.append(tvShowRow, castsRow);
    hideSpinner();
  } catch (error) {
    throw new Error(error);
  }
};

const detailsTvInfo = (showInfo) => {
  // Creating elements for tvShowRow.

  // Genres and Poster
  const genres = showInfo.genres.map((ele) => ` ${ele.name}`);
  const posterContainer = createImg(showInfo.poster_path, showInfo.name);
  posterContainer.classList.add('details', 'col');

  // Container to hold all the details
  const tvShowDetailsContainer = document.createElement('div');
  tvShowDetailsContainer.classList.add('tvShowDetailsContainer');
  const tvShowDetails = document.createElement('div');
  tvShowDetails.classList.add('tvShowDetails', 'col');
  const title = document.createElement('h3');
  title.textContent = showInfo.name;

  // Tagline
  const blockQuote = document.createElement('blockquote');
  const quoteIconLeft = document.createElement('i');
  quoteIconLeft.classList.add('material-icons');
  quoteIconLeft.textContent = 'format_quote';
  quoteIconLeft.style = 'transform: scaleX(-1)'; // Turn the quote around
  const quote = document.createElement('p');
  quote.textContent = showInfo.tagline;
  const quoteIconRight = document.createElement('i');
  quoteIconRight.classList.add('material-icons');
  quoteIconRight.textContent = 'format_quote';
  blockQuote.append(quoteIconLeft, quote, quoteIconRight);

  // Details of Tv Show
  const runtime = document.createElement('h6');
  const runtimeCont = document.createElement('span');
  runtime.textContent = 'Runtime: ';
  runtimeCont.textContent = showInfo.episode_run_time[0];
  runtime.appendChild(runtimeCont);
  const genre = document.createElement('h6');
  genre.textContent = 'Genres: ';
  const genreCont = document.createElement('span');
  genreCont.textContent = genres;
  genre.appendChild(genreCont);
  const releaseDate = document.createElement('h6');
  releaseDate.textContent = 'Release date: ';
  const releaseCont = document.createElement('span');
  releaseCont.textContent = showInfo.first_air_date;
  releaseDate.appendChild(releaseCont);
  const homepage = document.createElement('h6');
  homepage.textContent = 'Home page: ';
  const homepageCont = document.createElement('a');
  homepageCont.href = showInfo.homepage;
  homepageCont.target = '_blank';
  homepageCont.text = 'Visit homepage';
  homepage.appendChild(homepageCont);

  // Overview of Tv Show
  const overview = document.createElement('p');
  overview.textContent = showInfo.overview;
  tvShowDetails.append(
    title,
    blockQuote,
    runtime,
    genre,
    releaseDate,
    homepage,
    overview
  );

  // Buttons
  const buttons = document.createElement('div');
  tvShowDetailsContainer.append(tvShowDetails, buttons);

  return [posterContainer, tvShowDetailsContainer];
};

export const infoText = (msg, parent) => {
  if (!msg) return;
  const infoTextPar = document.createElement('p');
  infoTextPar.classList.add('infoText');
  infoTextPar.textContent = msg;
  parent.appendChild(infoTextPar);
};

export const errorHandling = (ev) => {
  const errorMessage = document.getElementById('error');
  const infoTxt = document.querySelector('.infoText');
  if (infoTxt) infoTxt.remove();
  if (errorMessage) errorMessage.textContent = '';
  hideSpinner();
  errorMessage.textContent = ev.reason ? ev.reason : ev;
  errorMessage.classList.add('error', 'active');
};
