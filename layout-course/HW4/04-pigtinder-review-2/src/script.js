// https://en.wikipedia.org/wiki/List_of_guinea_pig_breeds
pigDatabase = [
  {
    photo: './photos/Texel_guinea_pig.jpg',
    info: "Texel,\nI'm curly"
  },
  {
    photo: './photos/Teddy_guinea_pig.jpg',
    info: 'Teddy,\nwhite and fluffy'
  },
  {
    photo: './photos/Skinny_Pig.jpg',
    info: 'Skinny,\nbald, but gold'
  },
  {
    photo: './photos/Dösendes_Hausmeerschweinchen.JPG',
    info: 'Goldie,\nsun is bright'
  },
  {
    photo: './photos/Coronet_cavia.JPG',
    info: 'Karen,\nnicer hair than yours'
  },
  {
    photo: './photos/Cavia_porcellus.jpg',
    info: 'Coal,\nnight terror'
  },
  {
    photo: './photos/A_Guinea_pig_and_its_carrot.jpg',
    info: 'Two-Face,\nwanna carrot?'
  }
];

function withoutTransition(card, action) {
  const transitionDuration = card.style['transition-duration'];
  card.style['transition-duration'] = '0s';
  action(card);
  card.offsetHeight; // trigger reflow to apply changes (tip from StackOverflow)
  card.style['transition-duration'] = transitionDuration;
}

function fillCurrent(nextCard, prevCard = null) {
  let index = Math.min(Number.parseInt(Math.random() * pigDatabase.length), pigDatabase.length - 1);
  if (
    prevCard !== null &&
    prevCard.style['background-image'].indexOf(pigDatabase[index].photo) !== -1
  ) {
    index = (index + 1) % pigDatabase.length;
  }

  withoutTransition(nextCard, card => {
    card.style.removeProperty('transform');
    card.style['background-image'] = `url(${pigDatabase[index].photo})`;
  });

  nextCard.classList.add('current');
  nextCard.querySelector('.info').innerHTML = pigDatabase[index].info.replace('\n', '<br>');

  if (prevCard !== null) {
    prevCard.classList.remove('current');
  }
}

function swapCards() {
  const cards = document.querySelectorAll('.pig_card');

  let current = -1;
  for (let i = 0; i < cards.length && current === -1; ++i) {
    const cardClasses = cards[i].classList;
    for (const clss of cardClasses) {
      if (clss === 'current') {
        current = i;
        break;
      }
    }
  }

  fillCurrent(cards[1 - current], cards[current]);

  return cards[current];
}

function nope() {
  const oldCard = swapCards();

  oldCard.style['transform'] = 'translate(-150vw)';
}

function star() {
  const oldCard = swapCards();

  oldCard.style['transform'] = 'translate(0, -150vh)';
}

function heart() {
  const oldCard = swapCards();

  oldCard.style['transform'] = 'translate(150vw)';
}

function onLoad() {
  fillCurrent(document.querySelector('.pig_card.current'));

  let lastStart = null;

  function touchMoveUpdate(event) {
    if (lastStart === null) {
      return;
    }

    if (event.changedTouches) {
      event = event.changedTouches[0];
    }

    const dx = Number.parseInt(event.pageX - lastStart.pageX);
    const dy = Number.parseInt(event.pageY - lastStart.pageY);

    withoutTransition(
      lastStart.card,
      card => (card.style['transform'] = `translate(${dx}px, ${dy}px)`)
    );

    if (event.cancelable) {
      event.preventDefault();
    }
  }

  function touchMoveEnd(event) {
    if (lastStart === null) {
      return;
    }

    if (event.changedTouches) {
      event = event.changedTouches[0];
    }

    const dx = event.pageX - lastStart.pageX;
    const dy = event.pageY - lastStart.pageY;
    const EPS = 4; // px

    if (Math.abs(dx) > Math.abs(dy)) {
      if (dx > EPS) {
        heart();
      } else if (dx < -EPS) {
        nope();
      }
    } else {
      if (dy < -EPS) {
        // top is 0
        star();
      } else {
        lastStart.card.style.removeProperty('transform');
      }
    }

    lastStart = null;

    if (event.cancelable) {
      event.preventDefault();
    }
  }

  for (const card of document.querySelectorAll('.pig_card')) {
    function touchMoveStart(event) {
      if (event.changedTouches) {
        event = event.changedTouches[0];
      }

      lastStart = {
        card: card,
        pageX: event.pageX,
        pageY: event.pageY
      };

      if (event.cancelable) {
        event.preventDefault();
      }
    }

    card.addEventListener('mousedown', touchMoveStart, false);
    card.addEventListener('touchstart', touchMoveStart, false);

    let shouldHide = true;
    for (const clss of card.classList) {
      shouldHide = shouldHide && clss !== 'current';
    }

    if (shouldHide) {
      withoutTransition(card, card => (card.style['transform'] = 'translate(150vw)'));
    }
  }

  document.addEventListener('mousemove', touchMoveUpdate, false);
  document.addEventListener('touchmove', touchMoveUpdate, false);

  document.addEventListener('mouseup', touchMoveEnd, false);
  document.addEventListener('touchend', touchMoveEnd, false);
}
