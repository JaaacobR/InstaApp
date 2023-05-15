const userController = require("../controller/userController");
const getRequestData = require("../util/getRequestData");
const tagsRouter = async (request, response) => {
  switch (request.method) {
    case "GET":
      break;
    case "POST":
      if (request.url === "/api/user/register") {
        const data = await getRequestData(request);
        const jsonData = JSON.parse(data);
        const { token, user } = await userController.registerUser(jsonData);
        console.log(user, typeof token);
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
