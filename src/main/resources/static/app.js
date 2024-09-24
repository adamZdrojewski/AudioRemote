// Constants
const folderNameDisplay = document.querySelector("#folderNameDisplay");
const audioContainer = document.querySelector("#audioContainer");

// Get setup
fetch("/getsetup").then((res) => {
    return res.json();
}).then((data) => {
    renderFolderNameDisplay(data.folderName);
    renderAudioContainer(data.files);
});

// Functions
function renderFolderNameDisplay(folderName) {
    folderNameDisplay.textContent = folderName;
}

function renderAudioContainer(files) {
    let htmlOutput = `
        <div class="audioContainer">
            <div id="stopAudioBtn" class="audio" onclick="stopAudio()">
                <p>Stop Audio</p>
            </div>
    `;

    for(let i = 0; i < files.length; i++) {
        htmlOutput += `
            <div class="audio" onclick="playAudio(${i})">
                <p>${files[i]}</p>
            </div>
        `;
    }

    htmlOutput += `</div>`;

    audioContainer.innerHTML = htmlOutput;
}

function playAudio(audioIndex) {
    fetch("/playaudio?" + new URLSearchParams({
        index: audioIndex
    }).toString());
}

function stopAudio() {
    fetch("/stopaudio");
}