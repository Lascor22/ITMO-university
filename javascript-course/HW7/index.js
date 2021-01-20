setTimeout(main, 0);

function main() {
    const input = document.getElementById('input');
    let focus;
    input.addEventListener('input', async function (event) {
        const value = this.value;
        closeAllLists(input);
        if (!value) { return false; }
        focus = -1;
        const suggList = document.createElement("DIV");
        suggList.setAttribute('id', this.id + 'suggest-list');
        suggList.setAttribute('class', 'suggest-items');
        this.parentNode.appendChild(suggList);
        const airports = await fetchData();
        let count = 0;
        for (const airport of airports) {
            if (checkPrefix(airport, value)) {
                if (++count === 18) {
                    break;
                }
                const elem = document.createElement('DIV');
                elem.setAttribute('id', `${airport.city} - ${airport.code}`);
                elem.setAttribute('class', 'item');
                createHTMLBody(elem, value, airport);
                elem.addEventListener('click', function (event) {
                    input.value = this.getAttribute('id');
                    closeAllLists(input);
                });
                suggList.appendChild(elem);
            }
        }
    });
    input.addEventListener('keydown', function (event) {
        let suggList = document.getElementById(this.id + 'suggest-list');
        if (!suggList) return;

        const items = suggList.getElementsByClassName('item');
        if (event.code === 'ArrowDown') {
            focus = changeFocus(++focus, items);
        } else if (event.code === 'ArrowUp') {
            focus = changeFocus(--focus, items);
        } else if (event.code === 'Enter') {
            event.preventDefault();
            if (items && focus > -1) {
                items[focus].click();
            }
        }
    });
}

function closeAllLists(input, elem) {
    var items = document.getElementsByClassName('suggest-items');
    for (const item of items) {
        if (elem !== item && elem !== input) {
            item.parentNode.removeChild(item);
        }
    }
}

function changeFocus(focus, elems) {
    if (!elems) return;
    for (const elem of elems) {
        elem.classList.remove('suggest-active');
    };
    focus = focus >= elems.length ? 0 : (focus < 0 ? elems.length - 1 : focus);
    elems[focus].classList.add('suggest-active');
    return focus;
}

function checkPrefix(airport, value) {
    return airport.city.toLowerCase().startsWith(value.toLowerCase()) ||
        airport.code.toLowerCase().startsWith(value.toLowerCase());
}

function createHTMLBody(elem, value, airport) {
    if (airport.code.toLowerCase().startsWith(value.toLowerCase())) {
        elem.innerHTML = `${airport.city} - <b>${airport.code.substr(0, value.length)}</b>`;
        elem.innerHTML += airport.code.substr(value.length);
    } else {
        elem.innerHTML = `<b>${airport.city.substr(0, value.length)}</b>`;
        elem.innerHTML += `${airport.city.substr(value.length)} - ${airport.code}`;
    }
}

async function fetchData() {
    const rawData = await fetch('https://raw.githubusercontent.com/algolia/datasets/master/airports/airports.json');
    data = await rawData.json();
    return data.map(el => {
        return {
            city: el.city,
            code: el.iata_code
        };
    });
}
