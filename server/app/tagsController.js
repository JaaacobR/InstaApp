const { famousTags } = require("./model");
const getRequestData = require("./getRequestData");

module.exports = {
  getRaw: () => {
    return famousTags;
  },
  getTags: () => {
    return famousTags.map((tag, index) => {
      return { id: index, tag, popularity: 123 };
    });
  },
  getTag: (id) => {
    const tags = famousTags.map((tag, index) => {
      return { id: index, tag, popularity: 123 };
    });
    console.log(tags);
    return tags.find((tag) => tag.id == id);
  },
  addTag: (tag) => {
    const tagName = tag.name;
    if (famousTags.find((tag) => (tagName = tag.name)) === undefined) {
      famousTags.push(tagName);
      return { ...tag, index: famousTags.length };
    } else {
      return null;
    }
  },
};
