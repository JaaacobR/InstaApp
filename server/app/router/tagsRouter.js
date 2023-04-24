const tagsController = require("../controller/tagsController");
const getRequestData = require("../util/getRequestData");
const tagsRouter = async (request, response) => {
  switch (request.method) {
    case "GET":
      if (request.url == "/api/tags/raw") {
        response.writeHead(200, { "Content-type": "application/json" });
        const tagsList = tagsController.getRaw();
        response.end(JSON.stringify({ tagsList }, null, 5));
      } else if (request.url == "/api/tags") {
        response.writeHead(200, { "Content-type": "application/json" });
        const tagsList = tagsController.getTags();
        response.end(JSON.stringify({ tagsList }, null, 5));
      } else if (request.url.match(/\/api\/tags\/([0-9]+)/)) {
        const id = request.url.split("/")[3];
        response.writeHead(200, { "Content-type": "application/json" });
        const tag = tagsController.getTag(id);
        if (tag === undefined) {
          response.end(JSON.stringify({ message: "Cannnot get tag" }, null, 5));
        } else {
          response.end(JSON.stringify(tag, null, 5));
        }
      }
      break;
    case "POST":
      if (request.url == "/api/tags") {
        const data = await getRequestData(request);
        const r = tagsController.addTag(JSON.parse(data));
        response.end(JSON.stringify({ r }, null, 5));
      }
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = tagsRouter;
