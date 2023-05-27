const tagsController = require("../controller/tagsController");
const getRequestData = require("../util/getRequestData");
const { verifyToken } = require("../util/security");
const tagsRouter = async (request, response) => {
  
  switch (request.method) {
    case "GET":
      response.writeHead(200, { "Content-type": "application/json" });
      if (request.url == "/api/tags/raw") {
        const tagsList = tagsController.getRaw();
        response.end(JSON.stringify({ tagsList }, null, 5));
      } else if (request.url == "/api/tags") {
        const tagsList = tagsController.getTags();
        response.end(JSON.stringify({ tagsList }, null, 5));
      } else if (request.url.match(/\/api\/tags\/([0-9]+)/)) {
        const id = request.url.split("/")[3];
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
        const tag = tagsController.addTag(JSON.parse(data));
        response.end(JSON.stringify({ tag }, null, 5));
      }
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = tagsRouter;
