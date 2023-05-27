const profileController = require("../controller/profileController");
const getRequestData = require("../util/getRequestData");
const profileRouter = async (request, response) => {
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
      if (request.url === "/api/profile") {
      }
      break;
    case "POST":
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = profileRouter;
