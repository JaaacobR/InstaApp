const profileController = require("../controller/profileController");
const getRequestData = require("../util/getRequestData");
const {verifyToken} = require("../util/security")
const profileRouter = async (request, response) => {
  console.log("DIPA")
  let tokenData;
  if (
    request.headers.authorization &&
    request.headers.authorization.startsWith("Bearer")
  ) {
    let token = request.headers.authorization.split(" ")[1];
    console.log(token , request.headers.authorization);
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
        console.log(request.headers.authorization);
        const data = profileController.getProfile(tokenData);
        console.log(data)
        if (data) {
          console.log(data, "POTWIEHDCBJ")
          response.writeHead(200, { "Content-type": "application/json" });
          response.end(JSON.stringify(data), null ,5);
        }
      }
      break;
    case "POST":
      if (request.url === "/api/profile") {
        const dataImage = await profileController.saveFile(request);
        const data = profileController.addProfilePicture(
          tokenData,
          dataImage.url
        );
        if (data) {
          response.end(JSON.stringify(data));
        }
      }
      break;
    case "DELETE":

    case "PATCH":
      if (request.url === "/api/profile") {
        const dataString = await getRequestData(request);
        const data = JSON.parse(dataString);
        console.log(tokenData);
        console.log("AAA" , request.headers.authorization)
        const profileData = profileController.updateProfile(tokenData, data);
        
        response.writeHead(200, { "Content-type": "application/json" });
        response.end(JSON.stringify(profileData), null ,5);
      }
  }
};
module.exports = profileRouter;
