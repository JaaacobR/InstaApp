const getRequestData = require("../util/getRequestData");
const { saveFile, deleteFile } = require("../controller/fileController");
const {
  addPhoto,
  getPhotos,
  getPhoto,
  delPhoto,
  patchPhoto,
  addTag,
  addMassTags,
  findChangedPhoto,
  getPhotosByAlbum,
} = require("../controller/jsonController");
const fs = require("fs");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      if (request.url == "/api/photos") {
        response.writeHead(200, { "Content-Type": "application/json" });
        response.write(getPhotos());
      } else if (request.url.match(/\/api\/photos\/tags\/([0-9]+)/)) {
        const photo = getPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        if (photo) response.end(JSON.stringify({ tags: photo.tags }, null, 5));
        else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found!");
        }
      } else if (request.url.match(/\/api\/photos\/([0-9]+)/)) {
        const photo = getPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        if (photo) response.write(JSON.stringify(photo));
        else {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write("ID not found!");
        }
      } else if (request.url.match(/\/api\/photos\/album\/([A-Za-z0-9]+)/)) {
        const albumName =
          request.url.split("/")[request.url.split("/").length - 1];

        response.end(
          JSON.stringify({ photos: getPhotosByAlbum(albumName) }, null, 5)
        );
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

        response.end(JSON.stringify({ result: photo }));
      } else if (request.url == "/api/photos/tags/mass") {
        const data = await getRequestData(request);
        const photo = addMassTags(JSON.parse(data));

        response.end(JSON.stringify({ result: photo }));
      }
      break;
  }
  response.end();
};

module.exports = router;
