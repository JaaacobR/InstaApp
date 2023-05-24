const {
  encryptPass,
  decryptPass,
  createToken,
  verifyToken,
} = require("../util/security");
const { users } = require("../model/model");

module.exports = {
  registerUser: async ({ fullName, login, email, password }) => {
    if (fullName === "" || login === "" || email === "" || password === "")
      return null;
    const hashedPassword = await encryptPass(password);
    console.log(fullName, email, login, password);

    const token = await createToken({ email, login });
    const user = {
      id: new Date().getTime(),
      fullName,
      login,
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
  loginUser: async ({ login, password }) => {
    const user = users.find((user) => user.login === login && user.confirmed);
    if (!user) return null;
    const isCorrectPassword = decryptPass(password, user.password);
    if (!isCorrectPassword) return null;
    return await createToken({ email: user.email, login });
  },
};
