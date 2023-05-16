const tagsController = require("../controller/tagsController");
const getRequestData = require("../util/getRequestData");
const { verifyToken } = require("../util/security");
const tagsRouter = async (request, response) => {
  if (
    request.headers.authorization &&
    request.headers.authorization.startsWith("Bearer")
  ) {
    let token = request.headers.authorization.split(" ")[1];
    const isTokenActive = await verifyToken(token);
    console.log(isTokenActive);
    if (!isTokenActive) {
      response.writeHead(401, { "Content-type": "application/json" });
      response.end(JSON.stringify({ message: "unAuthorized" }));
    }
  } else {
    response.writeHead(401, { "Content-type": "application/json" });
    response.end(JSON.stringify({ message: "unAuthorized" }));
  }
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
