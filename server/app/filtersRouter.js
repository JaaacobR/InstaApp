const getRequestData = require("./getRequestData");
const { getMetaData } = require("./imageActions");
const { getPhoto } = require("./jsonController");

const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      response.writeHead(200, { "Content-Type": "application/json" });
      console.log("WYK", request.url);
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
      break;
  }
  response.end();
};

module.exports = router;
