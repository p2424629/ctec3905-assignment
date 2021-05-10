import { API_KEY, API_URL, IMG_URL, POSTER_SIZE } from './config.js';
export const scrollToTopHandle = () => {
  const scrollToTop = document.querySelector('.scrollToTop');

  // Show ScrollToTop button after scrolling.
  const scrollableHeight =
    document.documentElement.scrollHeight -
    document.documentElement.clientHeight;
  const GOLDEN_RATIO = 0.12;
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
export const notification = (msg = '', type = '', reload = false) => {
  if (!msg || !type) return;
  const notify = document.getElementById(type);
  notify.textContent = msg;
  notify.classList.add(type);
  setTimeout(() => {
    notify.classList.remove(type);
    if (reload) {
      window.location.reload();
    }
  }, 1500);
};

const showSpinner = () => {
  const spinner = document.querySelector('.spinner');
  spinner.style.display = 'block';
};
export const clear = (element) => {
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
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
  if (!url) {
    url = pageNum
      ? `${API_URL}tv/${loc}?api_key=${API_KEY}&language=en-US&page=${pageNum}`
      : `${API_URL}tv/${loc}?api_key=${API_KEY}&language=en-US`;
  }
  return getData(encodeURI(url));
};

const createShowItem = (id, name, voteAverage, firstAirDate) => {
  const showTitle = document.createElement('h2');
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
  detailsBtn.textContent = 'Details';
  detailsBtn.href = '#';
  detailsBtn.showId = id;
  detailsBtn.addEventListener('click', showSelected);

  const showItem = document.createElement('div');
  showItem.classList.add('tvShow');
  showItem.append(showTitle, voteAvgContainer, airDateContainer, detailsBtn);

  return showItem;
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
    // Notification that it is already in Favorites.
    notification('Already in favorites !', 'alreadyStored');
  }
};

const removeFavorite = (id) => {
  const storedId = JSON.parse(localStorage.getItem('favoriteSeries')) || [];
  const index = storedId.indexOf(id);
  storedId.splice(index, 1);
  localStorage.setItem('favoriteSeries', JSON.stringify(storedId));
  notification('Removed from watchlist !', 'alreadyStored', true);
};

const createAddBtn = (id, loc) => {
  // Change the icon to "trash" for already favorite shows or "heart" for added to favorites.
  const addBtnContainer = document.createElement('div');
  addBtnContainer.classList.add('addBtn');
  const container = document.createElement('span');
  const icon = document.createElement('i');
  if (document.URL.includes('favorites') && loc === 'favorites') {
    icon.classList.add('material-icons', 'trash');
    icon.textContent = 'delete';
    container.appendChild(icon);
    addBtnContainer.addEventListener('click', () => {
      removeFavorite(id);
    });
  } else {
    icon.classList.add('material-icons', 'favorite');
    icon.textContent = 'favorite';
    container.appendChild(icon);
    addBtnContainer.addEventListener('click', () => {
      addFavorite(id);
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
    ? `${IMG_URL}/${POSTER_SIZE}/${poster_path}`
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

// Take the user to detailed tv show info page.
function showSelected(ev) {
  sessionStorage.setItem('showId', ev.currentTarget.showId);
  window.open('../pages/shows-page.html');
  return false;
}

export const infoText = (msg, parent) => {
  if (!msg) return;
  const infoTextPar = document.createElement('p');
  infoTextPar.classList.add('infoText');
  infoTextPar.textContent = msg;
  parent.appendChild(infoTextPar);
};

export const errorHandling = (ev, appendTo) => {
  const errorMessage = document.querySelector('#error');
  const infoTxt = document.querySelector('.infoText');
  if (infoTxt) infoTxt.remove();
  if (errorMessage) errorMessage.textContent = '';
  hideSpinner();
  errorMessage.textContent = ev.reason ? ev.reason : ev;
  errorMessage.classList.add('error', 'active');
  appendTo.appendChild(errorMessage);
};
