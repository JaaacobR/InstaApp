class Photo {
  constructor(album, originalName, url) {
    this.id = Date.now();
    this.album = album;
    this.originalName = originalName;
    this.url = url;
    this.lastChange = "original";
    this.tags = [];
    this.history = [
      {
        status: "original",
        lastModifiedDate: this.id,
      },
    ];
    photosArray.push(this);
  }
  getUrl() {
    return this.url;
  }
  getId() {
    return this.id;
  }
  update(data) {
    this.history.push({
      timestamp: Date.now(),
      ...data,
    });
    this.lastChange = this.history[this.history.length - 1].status;
  }
}

let photosArray = [];

const famousTags = [
  "#love",
  "#instagood",
  "#fashion",
  "#photooftheday",
  "#art",
  "#photography",
  "#instagram",
  "#beautiful",
  "#picoftheday",
  "#nature",
  "#happy",
  "#cute",
  "#travel",
  "#style",
];

module.exports = { Photo, photosArray, famousTags };
