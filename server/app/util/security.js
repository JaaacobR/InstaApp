const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

const encryptPass = async (password) => {
  return await bcrypt.hash(password, 10);
};

const decryptPass = async (userPass, encrypted) => {
  return await bcrypt.compare(userPass, encrypted);
};

const createToken = async (data) => {
  let token = await jwt.sign(data, process.env.VERY_SECRET_KEY, {
    expiresIn: "2m",
  });
  return token;
};

const verifyToken = async (token) => {
  try {
    let decoded = await jwt.verify(token, process.env.VERY_SECRET_KEY);
    return decoded;
  } catch (ex) {
    console.log({ message: ex.message });
  }
};

module.exports = { encryptPass, decryptPass, createToken, verifyToken };
