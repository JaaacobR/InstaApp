const userController = require("../controller/userController");
const getRequestData = require("../util/getRequestData");
const tagsRouter = async (request, response) => {
  switch (request.method) {
    case "GET":
     
      if (
        request.url.search(/\/api\/user\/confirm\/(^[\w-]*\.[\w-]*\.[\w-]*$)/)
      ) {
        
        const token = request.url.split("/")[request.url.split("/").length - 1];
        const user = await userController.confirmUser(token);
        response.writeHead(200, { "Content-Type": "application/json" });
        response.end(JSON.stringify({ user }));
      }
      break;
    case "POST":
      if (request.url === "/api/user/register") {
        const data = await getRequestData(request);
        const jsonData = JSON.parse(data);
        const { token, user } = await userController.registerUser(jsonData);
        console.log(user, token);
        if (!!user && !!token) {
          response.writeHead(200, { "Content-Type": "application/json" });
          response.end(JSON.stringify({ url: token }));
        }
      } else if (request.url === "/api/user/login") {
        const data = await getRequestData(request);
        const jsonData = JSON.parse(data);
        const token = await userController.loginUser(jsonData);
        if (token) {
          response.writeHead(200, { "Content-Type": "application/json" });
          response.end(JSON.stringify({ token }));
        } else {
          response.writeHead(401, { "Content-Type": "application/json" });
          response.end(
            JSON.stringify({
              message: "wrong credentials or unAuthorized user",
            })
          );
        }
      }
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = tagsRouter;
