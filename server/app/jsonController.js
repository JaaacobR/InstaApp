const { Photo, photosArray } = require("./model");

module.exports = {
  addPhoto: (photoData) => {
    const { album, originalName, url } = photoData;
    const photo = new Photo(album, originalName, url);
    return JSON.stringify(photo);
  },
  getPhotos: () => {
    return JSON.stringify(photosArray);
  },
  getPhoto: (id) => {
    console.log(id, photosArray);
    return photosArray.find((item) => {
      return item.id == id;
    });
  },

  delPhoto: (id) => {
    const index = photosArray.findIndex((item) => {
      return item.id == id;
    });
    if (index == -1) return null;
    const url = photosArray[index].getUrl();
    photosArray.splice(index, 1);
    return url;
  },
  patchPhoto: (data) => {
    console.log(data.id);
    if (!data.id) return null;
    const photo = photosArray.find((item) => {
      return item.id == parseInt(data.id);
    });
    console.log(photo);
    if (!photo) return null;
    photo.update(data.content);
    return photo.getId();
  },
  addTag: (data) => {
    if (!data.id) return null;
    const photo = photosArray.find((item) => {
      return item.id == parseInt(data.id);
    });
    if (!photo) return null;
    photo.tags = [...photo.tags, data.tag];
    photo.update();
    return photo;
  },
  addMassTags: (data) => {
    if (!data.id) return null;
    const photo = photosArray.find((item) => {
      return item.id == parseInt(data.id);
    });
    if (!photo) return null;
    photo.tags = [...photo.tags, ...data.tags];
    photo.update();
    return photo;
  },
  findChangedPhoto: (status, id) => {
    const photo = photosArray.find((photo) => photo.id == id);
    console.log(photo, status);
    if (photo) {
      return photo.history.find((item) => item.status == status);
    }
    return undefined;
  },
  getPhotosByAlbum: (album) => {
    return photosArray.filter((photo) => photo.album === album);
  },
};
