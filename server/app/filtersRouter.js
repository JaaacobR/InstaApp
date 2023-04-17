const getRequestData = require("./getRequestData");
const { getMetaData, editPhoto } = require("./filtersController");
const { getPhoto } = require("./jsonController");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      response.writeHead(200, { "Content-Type": "application/json" });

      if (request.url.match(/\/api\/filters\/metadata\/([0-9]+)/)) {
        console.log(request.url);
        const photo = getPhoto(
          request.url.split("/")[request.url.split("/").length - 1]
        );
        if (photo) {
          const metadata = await getMetaData(photo.url);

          response.end(JSON.stringify(metadata, null, 5));
        } else {
          response.end(JSON.stringify({ res: "not found" }));
        }
      }
      break;
    case "PATCH":
      response.writeHead(200, { "Content-Type": "application/json" });
      if (request.url === "/api/filters") {
        const data = await getRequestData(request);
        console.log(data, "A");
        const dataImage = JSON.parse(data);
        console.log(dataImage);
        await editPhoto(dataImage.url, dataImage.type, dataImage.params);
      }
      break;
  }
  response.end();
};

module.exports = router;
