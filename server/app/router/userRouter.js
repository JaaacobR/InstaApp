const userController = require("../controller/userController");
const getRequestData = require("../util/getRequestData");
const tagsRouter = async (request, response) => {
  switch (request.method) {
    case "GET":
      console.log(request.url);
      if (
        request.url.search(/\/api\/user\/confirm\/(^[\w-]*\.[\w-]*\.[\w-]*$)/)
      ) {
        console.log("GIT");
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
      }
      break;
    case "DELETE":

    case "PATCH":
  }
};

module.exports = tagsRouter;
