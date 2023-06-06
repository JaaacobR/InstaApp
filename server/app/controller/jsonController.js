const { Photo, photosArray } = require("../model/model");

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
    if (!data.id) return null;
    const photo = photosArray.find((item) => {
      return item.id == parseInt(data.id);
    });

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
    photo.tags = [...data.tags]
    return photo;
  },
  addLocation: (data) => {
    if (!data.id) return null;
    const photo = photosArray.find((item) => item.id == data.id)
    if(!photo) return null;
    photo.location = data.location;
    return photo;
  },
  findChangedPhoto: (status, id) => {
    const photo = photosArray.find((photo) => photo.id == id);
    if (photo) {
      return photo.history.find((item) => item.status == status);
    }
    return undefined;
  },
  getPhotosByAlbum: (album) => {
    return photosArray.filter((photo) => photo.album === album);
  },
};
