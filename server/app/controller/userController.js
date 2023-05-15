const { encryptPass, decryptPass, createToken } = require("../util/security");
const { users } = require("../model/model");

module.exports = {
  registerUser: async ({ name, lastName, email, password }) => {
    console.log("info", name, lastName, email, password);
    if (name === "" || lastName === "" || email === "" || password === "")
      return null;
    console.log("ingkfmnlds");
    const hashedPassword = await encryptPass(password);

    const user = {
      id: new Date().getTime(),
      name,
      lastName,
      email,
      password: hashedPassword,
      confirmed: false,
    };
    const token = await createToken({ email, name });
    users.push(user);

    return { token, user };
  },
};
