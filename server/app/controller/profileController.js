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
  updateProfile: (data, profileData) => {
    if (!profileData.login) return null;
    const user = users.find((user) => user.login === profileData.login);
    if (!user) return null;
    user.fullName = data.fullName;
    user.login = data.login;
    return this.getProfile(profileData);
  },
  saveFile: async (request, response) => {
    const form = formidable.IncomingForm();
    return new Promise((resolve) => {
      form.parse(request, async (err, fields, files) => {
        if (err) return null;
        console.log(files);
        const uploadFolder = path.join(
          __dirname,
          "../../uploads",
          fields.album
        );
        if (!fs.existsSync(uploadFolder)) {
          fs.mkdirSync(uploadFolder, { recursive: true });
        }
        form.uploadDir = uploadFolder;
        const file = files.file;
        const new_path = path.join(uploadFolder, "profile.jpg");
        try {
          fs.renameSync(file.path, new_path);
          resolve({
            album: fields.album,
            originalName: file.name,
            url: new_path,
          });
        } catch (error) {
          response.writeHead(404, { "Content-Type": "text/html" });
          response.write(error);
          response.end();
        }
      });
    });
  },
  addProfilePicture: (tokenData, url) => {
    if (!tokenData.login) return null;
    const user = users.find((user) => user.login === profileData.login);
    if (!user) return null;
    user.profile = url;
    return this.addProfilePicture(tokenData);
  },
};
