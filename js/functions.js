/* eslint-disable import/extensions */
/* eslint-disable camelcase */
import {
  API_KEY,
  API_URL,
  IMG_URL,
  POSTER_SIZE,
} from './config.js';

// Take the user to detailed tv show info page.
const showSelected = async (event) => {
  event.preventDefault();
  sessionStorage.setItem('showId', event.currentTarget.showId);
  window.open(
    `../pages/shows-page.html?showId=${event.currentTarget.showId}`,
    '_blank',
  );
  return false;
};

export const scrollToTopHandle = () => {
  const scrollToTop = document.querySelector('.scrollToTop');

  // Show ScrollToTop button after scrolling.
  const scrollableHeight = document.documentElement.scrollHeight
    - document.documentElement.clientHeight;
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
    document.querySelector('.nav-toggle-label').classList.remove('active');
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

const tweet = (title) => {
  const windowFeatures = 'location=yes,height=300,width=550,scrollbars=yes,status=yes';
  const url = `https://twitter.com/intent/tweet?text=Going to watch "${title}" . Created for &hashtags=CTEC3905,DMU`;
  window.open(url, '_blank', windowFeatures);
};

async function getData(url) {
  const response = await fetch(url);
  if (response.ok) return response.json();
  return response.json().then((responseJson) => {
    if (responseJson.errors) {
      throw new Error(
        `HTTP ${response.status} ${response.statusText}: ${responseJson.errors}`,
      );
    }
    throw new Error(
      `HTTP ${response.status} ${response.statusText}: ${responseJson.status_message}`,
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
  // detailsBtn.addEventListener('click', showSelected);
  detailsBtn.addEventListener('mousedown', showSelected);
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
    : './images/image-not-found.jpg';
  imgSrc.alt = name;
  imgContainer.appendChild(imgSrc);
  return imgContainer;
};

export const buildResults = (obj) => {
  try {
    let seriesInfo = document.getElementById('tvShows');

    const {
      id,
      name,
      vote_average,
      first_air_date,
      poster_path,
    } = obj;
    const showItem = createShowItem(id, name, vote_average, first_air_date);
    const addBtn = createAddBtn(id);
    const overlay = document.createElement('div');
    overlay.classList.add('overlay');
    overlay.append(addBtn, showItem);
    const img = createImg(poster_path, name);
    const card = document.createElement('div');
    card.classList.add('card');
    card.append(overlay, img);
    if (!seriesInfo) {
      seriesInfo = document.createElement('div');
      seriesInfo.id = 'tvShow';
    }
    seriesInfo.appendChild(card);
    hideSpinner();
    return seriesInfo;
  } catch (error) {
    throw new Error(error);
  }
};

const castRowProcess = (actors) => {
  const title = document.createElement('h4');
  title.textContent = 'Top Cast Actors';

  const actorsCards = actors.splice(0, 8).map((ele) => {
    const img = ele.picture
      ? `${IMG_URL}w300${ele.picture}`
      : './images/image-not-found.jpg';
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

  const actorsContainer = document.createElement('div');
  actorsContainer.classList.add('actorsContainer');
  actorsContainer.append(...actorsCards);
  return [title, actorsContainer];
};
const detailsTvInfo = (showInfo, externals) => {
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
  runtimeCont.textContent = `${showInfo.episode_run_time[0]} min.`;
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

  const rating = document.createElement('h6');
  rating.textContent = 'Rating: ';
  const ratingCont = document.createElement('span');
  ratingCont.textContent = `${showInfo.vote_average} / 10 `;
  const ratingCont2 = document.createElement('span');
  ratingCont2.textContent = `(${showInfo.vote_count} votes)`;
  ratingCont2.classList.add('smallText');
  rating.append(ratingCont, ratingCont2);

  const numEpisodes = document.createElement('h6');
  numEpisodes.textContent = 'Number of episodes: ';
  const numEpisodesCont = document.createElement('span');
  numEpisodesCont.textContent = showInfo.number_of_episodes;
  numEpisodes.appendChild(numEpisodesCont);

  const numSeasons = document.createElement('h6');
  numSeasons.textContent = 'Number of seasons: ';
  const numSeasonsCont = document.createElement('span');
  numSeasonsCont.textContent = showInfo.number_of_seasons;
  numSeasons.appendChild(numSeasonsCont);

  const latestEpisodeToAir = document.createElement('h6');
  latestEpisodeToAir.textContent = 'Latest Episode: ';
  const latestEpisodeToAirCont = document.createElement('span');
  latestEpisodeToAirCont.textContent = `${
    showInfo.last_episode_to_air.air_date
  } - "${
    showInfo.last_episode_to_air.name
      ? showInfo.last_episode_to_air.name
      : 'Unknown'
  }".`;
  latestEpisodeToAir.appendChild(latestEpisodeToAirCont);

  const status = document.createElement('h6');
  status.textContent = 'Status: ';
  const statusCont = document.createElement('span');
  statusCont.textContent = showInfo.status;
  status.appendChild(statusCont);

  const homepage = document.createElement('h6');
  homepage.textContent = 'Home page: ';
  const homepageCont = document.createElement('a');
  homepageCont.href = showInfo.homepage;
  homepageCont.target = '_blank';
  homepageCont.text = 'Visit homepage';
  homepage.appendChild(homepageCont);

  // Overview of Tv Show
  const overviewTitle = document.createElement('h6');
  overviewTitle.textContent = 'Plot:';
  const overview = document.createElement('p');
  overview.textContent = showInfo.overview;

  // Buttons
  const buttonsCont = buttons(showInfo, externals);
  tvShowDetails.append(
    title,
    blockQuote,
    rating,
    runtime,
    genre,
    releaseDate,
    numEpisodes,
    numSeasons,
    latestEpisodeToAir,
    status,
    homepage,
    buttonsCont,
    overviewTitle,
    overview,
  );

  // Buttons
  tvShowDetailsContainer.append(tvShowDetails);

  return [posterContainer, tvShowDetailsContainer];
};

const trailerProcess = (videoUrl) => {
  const trailerTitle = document.createElement('h4');
  trailerTitle.textContent = 'Trailer';
  const videoContainer = document.createElement('div');
  videoContainer.classList.add('videoContainer');
  if (!videoUrl || videoUrl.results.length === 0) {
    const noTrailer = document.createElement('p');
    noTrailer.textContent = 'No video available';
    noTrailer.classList.add('trailer_error');
    videoContainer.appendChild(noTrailer);
    return [trailerTitle, videoContainer];
  }
  const trailers = videoUrl.results.filter(
    (ele) => ele.site === 'YouTube' && ele.type.includes('Trailer'),
  );
  // Get random Trailer.
  let min = 0;
  let max = trailers.length - 1;
  min = Math.ceil(min);
  max = Math.floor(max);
  const trailerNumber = Math.floor(Math.random() * (max - min + 1)) + min;
  const trailerIframe = document.createElement('iframe');
  const url = `https://www.youtube.com/embed/${trailers[trailerNumber].key}`;
  trailerIframe.src = url;
  trailerIframe.setAttribute('allowfullscreen', '');
  videoContainer.appendChild(trailerIframe);

  return [trailerTitle, videoContainer];
};

const buttons = (showInfo, externals) => {
  // IMDB link.
  const buttonsCont = document.createElement('div');
  buttonsCont.classList.add('buttons');

  const imdb = document.createElement('a');
  imdb.href = `https://www.imdb.com/title/${externals.imdb_id}`;
  imdb.target = '_blank';
  imdb.text = 'IMDB Link';
  const addToFavorites = document.createElement('a');
  addToFavorites.classList.add('favorite');
  addToFavorites.addEventListener('click', () => {
    addFavorite(showInfo.id);
  });
  addToFavorites.text = 'Add to Favorites';
  const twitter = document.createElement('a');
  twitter.classList.add('twitter-share-button', 'twitter');
  twitter.addEventListener('click', () => {
    tweet(showInfo.name);
  });

  buttonsCont.append(imdb, addToFavorites, twitter);
  return buttonsCont;
};

const recommendationsProcess = (recommendations) => {
  const recommendationsTitle = document.createElement('h4');
  recommendationsTitle.textContent = 'Recommendations';
  const recommendationsCont = document.createElement('div');
  recommendationsCont.classList.add('recommendations');
  if (!recommendations || recommendations.results.length === 0) {
    const noRecommendations = document.createElement('h6');
    noRecommendations.textContent = 'Sorry! No recommendations for this Tv Show for now.';
    recommendationsCont.appendChild(noRecommendations);
    return [recommendationsTitle, recommendationsCont];
  }
  recommendations.results.splice(0, 8).forEach((ele) => {
    const node = buildResults(ele);
    recommendationsCont.appendChild(node);
  });
  return [recommendationsTitle, recommendationsCont];
};

export const buildShowResults = (
  showInfo,
  videoUrl,
  actors,
  externals,
  recommendations,
) => {
  try {
    const main = document.querySelector('.main-details');
    clear(main);
    // Creating Rows for show details.
    const tvShowRow = document.createElement('div');
    tvShowRow.classList.add('tvShowRow');
    const castsRow = document.createElement('div');
    castsRow.classList.add('castsRow');
    const trailerRow = document.createElement('div');
    trailerRow.classList.add('trailerRow');
    const recommendationRow = document.createElement('div');
    recommendationRow.classList.add('recommendationRow');

    // Show info.
    const [posterContainer, tvShowDetailsContainer] = detailsTvInfo(
      showInfo,
      externals,
    );

    // Casts of tv show.
    const [castTitle, actorsContainer] = castRowProcess(actors);
    // Trailer.
    const [trailerTitle, trailer] = trailerProcess(videoUrl);

    // Recommendations.
    const [recommendationsTitle, recommendationsCont] = recommendationsProcess(recommendations);
    // Appending elements to rows.
    tvShowRow.append(posterContainer, tvShowDetailsContainer);
    castsRow.append(castTitle, actorsContainer);
    trailerRow.append(trailerTitle, trailer);
    recommendationRow.append(recommendationsTitle, recommendationsCont);
    // main.append(tvShowRow, castsRow, trailerRow, recommendationRow);
    main.append(tvShowRow, castsRow, trailerRow, recommendationRow);
    hideSpinner();
  } catch (error) {
    throw new Error(error);
  }
};

export const infoText = (msg, parent) => {
  if (!msg) return;
  const infoTextPar = document.createElement('p');
  infoTextPar.classList.add('infoText');
  infoTextPar.textContent = msg;
  parent.appendChild(infoTextPar);
};

export const errorHandling = (ev) => {
  const errorDiv = document.querySelector('.errors');
  if (errorDiv) clear(errorDiv);
  const infoTxt = document.querySelector('.infoText');
  if (infoTxt) infoTxt.remove();
  const errorMsg = document.createElement('p');
  hideSpinner();
  errorMsg.textContent = ev.reason ? ev.reason : ev;
  errorDiv.appendChild(errorMsg);
  errorDiv.classList.add('active');
};
