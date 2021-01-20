const mode = 7;
let currId = 0;

function buttonHandler() {
    document.getElementsByClassName(`card-${currId + 1}`)[0].style.display = 'none';
    currId = (currId + 1) % mode;
    document.getElementsByClassName(`card-${currId + 1}`)[0].style.display = 'flex';
    
}