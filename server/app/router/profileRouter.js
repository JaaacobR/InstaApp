const profileController = require("../controller/profileController");
const getRequestData = require("../util/getRequestData");
const profileRouter = async (request, response) => {
  let tokenData;
  if (
    request.headers.authorization &&
    request.headers.authorization.startsWith("Bearer")
  ) {
    let token = request.headers.authorization.split(" ")[1];
    tokenData = await verifyToken(token);
    console.log(tokenData);
    if (!tokenData) {
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
        const data = profileController.getProfile(tokenData)
        if(data){
          response.writeHead(200 , { "Content-type": "application/json" });
          response.end(JSON.stringify(data))
        }
      }
      break;
    case "POST":
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = profileRouter;
