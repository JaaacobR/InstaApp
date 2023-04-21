const getRequestData = require("./getRequestData");
const { saveFile, deleteFile } = require("./fileController");
const {
  addPhoto,
  getPhotos,
  getPhoto,
  delPhoto,
  patchPhoto,
  addTag,
  addMassTags,
  findChangedPhoto,
} = require("./jsonController");
const fs = require("fs");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      if (request.url == "/api/photos") {
        response.writeHead(200, { "Content-Type": "application/json" });
        response.write(getPhotos());
      } else if (request.url.match(/\/api\/photos\/([0-9]+)\/([a-z]+)/)) {
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
      } else if (request.url.match(/\/api\/photos\/tags\/([0-9]+)/)) {
        response.writeHead(200, { "Content-Type": "application/json" });
        const photo = getPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        console.log(photo);
        if (photo) response.end(JSON.stringify({ tags: photo.tags }, null, 5));
        else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found!");
        }
      } else if (request.url.match(/\/api\/photos\/([0-9]+)/)) {
        response.writeHead(200, { "Content-Type": "application/json" });
        const photo = getPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        if (photo) response.write(JSON.stringify(photo));
        else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found!");
        }
      }

      break;
    case "POST":
      response.writeHead(201, { "Content-Type": "application/json" });
      if (request.url == "/api/photos") {
        const uploadData = await saveFile(request, response);
        const newPhoto = addPhoto(uploadData);
        response.write(newPhoto);
      }
      break;
    case "DELETE":
      response.writeHead(200, { "Content-Type": "text/html" });
      if (request.url.match(/\/api\/photos\/([0-9]+)/)) {
        const urlDeleted = delPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        if (urlDeleted) {
          deleteFile(urlDeleted);
          response.write(
            "Successfuly deleted photo with id " +
              request.url.split("/")[request.url.split("/").length - 1]
          );
        } else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found");
        }
      }
      break;
    case "PATCH":
      if (request.url == "/api/photos") {
        const data = await getRequestData(request);
        const idPatched = patchPhoto(JSON.parse(data));
        if (idPatched) {
          response.write("Successfuly patched photo with id " + idPatched);
        } else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found");
        }
      } else if (request.url == "/api/photos/tags") {
        const data = await getRequestData(request);
        const photo = addTag(JSON.parse(data));
        console.log(photo);
        response.end(JSON.stringify({ result: photo }));
      } else if (request.url == "/api/photos/tags/mass") {
        const data = await getRequestData(request);
        const photo = addMassTags(JSON.parse(data));
        console.log(photo);
        response.end(JSON.stringify({ result: photo }));
      }
      break;
  }
  response.end();
};

module.exports = router;
