const {
  encryptPass,
  decryptPass,
  createToken,
  verifyToken,
} = require("../util/security");
const { users } = require("../model/model");

module.exports = {
  registerUser: async ({ name, lastName, email, password }) => {
    console.log("info", name, lastName, email, password);
    if (name === "" || lastName === "" || email === "" || password === "")
      return null;
    console.log("ingkfmnlds");
    const hashedPassword = await encryptPass(password);

    const token = await createToken({ email, name });
    const user = {
      id: new Date().getTime(),
      name,
      lastName,
      email,
      password: hashedPassword,
      confirmed: false,
      token,
    };
    users.push(user);

    return { token, user };
  },
  confirmUser: async (token) => {
    const decrypted = await verifyToken(token);
    if (decrypted) {
      const user = users.find((user) => user.token === token);
      if (!!user) {
        user.confirmed = true;
        return user;
      } else {
        return null;
      }
    }
    return null;
  },
};
