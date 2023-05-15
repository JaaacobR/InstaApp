const { encryptPass, decryptPass } = require("../util/security");
const { users } = require("../model/model");

module.exports = {
  registerUser: async ({ name, lastName, email, password }) => {
    if (name === "" || lastName === "" || email === "" || password === "")
      return null;
    const hashedPassword = await encryptPass(password);
    const user = {
      id: new Date().getTime(),
      name,
      lastName,
      email,
      password: hashedPassword,
      confirmed: false,
    };
    users.push(user);
  },
};
