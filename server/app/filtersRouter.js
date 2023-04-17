const getRequestData = require("./getRequestData");


const router = async (request, response) => {
  switch (request.method) {
    case "GET":
      if(request.url.match(/\/api\/filters\/metadata\/([0-9]+)/)){
        
      }
      break;
    case "PATCH":
      
      break;
  }
  response.end();
};

module.exports = router;
