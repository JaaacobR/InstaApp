const sharp = require("sharp");

module.exports = {
  getMetaData: async (url) => {
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
  editPhoto: async (url, type, params) => await getAction(url, type, params),
};

const getAction = async (url, type, params) => {
  return new Promise(async (resolve, reject) => {
    try {
      if (url) {
        switch (type) {
          case "rotate":
            await sharp(url)
              .rotate(params)
              .toFile(url + "-rotated" + ".jpg");
            break;
          case "resize":
            await sharp(url)
              .resize(params)
              .toFile(url + "-resized" + ".jpg");
            break;
          case "reformat":
            await sharp(url)
              .toFormat(params)
              .toFile(url + "-rotated" + "." + format);
            break;
          case "crop":
            await sharp(url)
              .extract(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-cropped" + ".jpg");
            break;
          case "grayscale":
            await sharp(url)
              .grayscale()
              .toFile(url + "-cropped" + ".jpg");
            break;
          case "tint":
            await sharp(url)
              .tint(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-tint" + ".jpg");
            break;
          case "negate":
            await sharp(url)
              .negate()
              .toFile(url + "-negate" + ".jpg");
            break;
          case "flip":
            await sharp(url)
              .flip()
              .toFile(url + "-flipped" + ".jpg");
            break;
          case "flop":
            await sharp(url)
              .flop()
              .toFile(url + "-flopped" + ".jpg");
            break;
        }
      } else {
        resolve("url_not_found");
      }
    } catch (error) {
      reject(error);
    }
  });
};
