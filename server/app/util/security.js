const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

const encryptPass = async (password) => {
  return await bcrypt.hash(password, 10);
};

const decryptPass = async (userPass, encrypted) => {
  return await bcrypt.compare(userPass, encrypted);
};

const createToken = async (data) => {
  let token = await jwt.sign(data, "hjksdagfjhksdghjksdgfhjksdfgbjksd", {
    expiresIn: "15m",
  });
  return token;
};

const verifyToken = async (token) => {
  try {
    console.log(token)
    let decoded = await jwt.verify(token, "hjksdagfjhksdghjksdgfhjksdfgbjksd");
    console.log(typeof decoded, "decoded data");
    return decoded;
  } catch (ex) {
    console.log({ message: ex.message });
  }
};

module.exports = { encryptPass, decryptPass, createToken, verifyToken };
