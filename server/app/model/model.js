class Photo {
  constructor(album, originalName, url) {
    this.id = Date.now();
    this.album = album;
    this.originalName = originalName;
    this.url = url;
    this.lastChange = "original";
    this.tags = [];
    this.location = null;
    this.history = [
      {
        timestamp: Date.now(),
        status: "original",
        url: url,
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

class User {
  constructor(data) {
    this.login = data.login;
    this.email = data.email;
    this.token = data.token;
    this.fullName = data.fullName;
    this.password = data.password;
    this.profile = null;
    this.confirmed = false;
    
  }

  confirm() {
    this.confirmed = true;
  }

  update(login, fullName) {
    this.fullName = fullName;
    this.login = login;
  }
}

const users = [];

module.exports = { Photo, photosArray, famousTags, users, User };
