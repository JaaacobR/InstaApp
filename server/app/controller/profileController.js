const {
  encryptPass,
  decryptPass,
  createToken,
  verifyToken,
} = require("../util/security");
const { users, photos } = require("../model/model");

module.exports = {
  getProfile: (profileData) => {
    if (!profileData.login) return null;
    const user = users.find((user) => user.login === profileData.login);
    if (!user) return null;
    const photos = photos.filter((photo) => photo.alnum === user.login);
    return { user, photos };
  },
};
