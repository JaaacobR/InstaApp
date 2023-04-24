const { findChangedPhoto, getPhoto } = require("../controller/jsonController");
const fs = require("fs");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      if (request.url.match(/\/api\/getfile\/([0-9]+)\/([a-z]+)/)) {
        console.log(request.url.split("/")[request.url.split("/").length - 2]);
        const photoID =
          request.url.split("/")[request.url.split("/").length - 2];
        const actionName =
          request.url.split("/")[request.url.split("/").length - 1];
        const changedPhoto = findChangedPhoto(actionName, photoID);
        if (changedPhoto) {
          const img = fs.readFileSync(changedPhoto.url);
          response.writeHead(200, { "Content-Type": "image/jpg" });
          response.write(img);
          response.end();
        }
      } else if (request.url.match(/\/api\/getfile\/([0-9]+)/)) {
        const photoID =
          request.url.split("/")[request.url.split("/").length - 1];
        const photo = getPhoto(photoID);
        if (photo) {
          const img = fs.readFileSync(photo.url);
          response.writeHead(200, { "Content-Type": "image/jpg" });
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
