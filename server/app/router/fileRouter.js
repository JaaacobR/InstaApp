const { findChangedPhoto, getPhoto } = require("../controller/jsonController");
const { users } = require("../model/model");
const fs = require("fs");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      response.writeHead(200, { "Content-Type": "image/jpg" });
      if (request.url.match(/\/api\/getfile\/([a-z]+)\/profile/)) {
        const login = request.url.split("/")[request.url.split("/").length - 2];
        const user = users.find((user) => user.login == login);
        if (user) {
          const img = fs.readFileSync(user.profile);
          response.write(img);
          response.end();
        }
      } else if (request.url.match(/\/api\/getfile\/([0-9]+)\/([a-z]+)/)) {
        const photoID =
          request.url.split("/")[request.url.split("/").length - 2];
        const actionName =
          request.url.split("/")[request.url.split("/").length - 1];
        const changedPhoto = findChangedPhoto(actionName, photoID);
        if (changedPhoto) {
          const img = fs.readFileSync(changedPhoto.url);
          response.write(img);
          response.end();
        }
      } else if (request.url.match(/\/api\/getfile\/([0-9]+)/)) {
        const photoID =
          request.url.split("/")[request.url.split("/").length - 1];
        const photo = getPhoto(photoID);
        if (photo) {
          const img = fs.readFileSync(photo.url);
          response.write(img);
          response.end();
        }
      }
      break;
    case "PATCH":
      break;
  }
  response.end();
};

module.exports = router;
