const sharp = require("sharp");

module.exports = {
  getMetaData: async(url) => {
    return new Promise(async (resolve, reject) => {
      try {
        if (url) {
          let meta = await sharp(url).metadata();
          resolve(meta);
        } else {
          resolve("url_not_found");
        }
      } catch (error) {
        reject(error);
      }
    });
  },
};
