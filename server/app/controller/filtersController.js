const sharp = require("sharp");
const { photosArray } = require("../model/model");

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
  editPhoto: async (url, type, params) => {
    await getAction(url, type, params);
  },
};

const getAction = async (url, type, params) => {
  return new Promise(async (resolve, reject) => {
    try {
      if (url) {
        switch (type) {
          case "rotate":
            await sharp(url)
              .rotate(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-rotated" + ".jpg");
            break;
          case "resize":
            await sharp(url)
              .resize(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-resized" + ".jpg");
            break;
          case "reformat":
            await sharp(url)
              .toFormat(params)
              .toFile(
                url.slice(0, url.lastIndexOf(".")) + "-rotated" + "." + format
              );
            break;
          case "crop":
            await sharp(url)
              .extract(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-cropped" + ".jpg");
            break;
          case "grayscale":
            await sharp(url)
              .grayscale()
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-grayscale" + ".jpg");
            break;
          case "tint":
            await sharp(url)
              .tint(params)
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-tint" + ".jpg");
            break;
          case "negate":
            await sharp(url)
              .negate()
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-negate" + ".jpg");
            break;
          case "flip":
            await sharp(url)
              .flip()
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-flip" + ".jpg");
            break;
          case "flop":
            await sharp(url)
              .flop()
              .toFile(url.slice(0, url.lastIndexOf(".")) + "-flop" + ".jpg");
            break;
        }
      } else {
        resolve("url_not_found");
      }
      resolve("ready")
    } catch (error) {
      reject(error);
    }
  });
};
