const { famousTags } = require("../model/model");

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

    return tags.find((tag) => tag.id == id);
  },
  addTag: (tag) => {
    const tagName = tag.name;

    if (famousTags.find((famousTag) => tagName === famousTag) == undefined) {
      famousTags.push(tagName);
      return { ...tag, index: famousTags.length };
    } else {
      return null;
    }
  },
};
