const http = require("http");
require("dotenv").config();
const imageRouter = require("./app/router/imageRouter");
const tagsRouter = require("./app/router/tagsRouter");
const filtersRouter = require("./app/router/filtersRouter");
const fileRouter = require("./app/router/fileRouter");
const usersRouter = require("./app/router/userRouter");
const profileRouter = require("./app/router/profileRouter");

http
  .createServer(async (req, res) => {
    if (req.url.search("/api/photos") !== -1) {
      await imageRouter(req, res);
    } else if (req.url.search("/api/tags") !== -1) {
      await tagsRouter(req, res);
    } else if (req.url.search("/api/filters") !== -1) {
      await filtersRouter(req, res);
    } else if (req.url.search("/api/getfile") !== -1) {
      await fileRouter(req, res);
    } else if (req.url.search("api/user") !== -1) {
      await usersRouter(req, res);
    } else if (req.url.search("/api/profile") !== -1) {
      console.log("KURWAAJKSAJKHGHJK")
      await profileRouter(req, res);
    }
  })
  .listen(3000, () => console.log("listen on 3000"));
