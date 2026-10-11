import axiosClient from "./axiosClient";

export async function getGalleryAlbums() {
  const response = await axiosClient.get("/public/gallery");
  return response.data;
}

export async function getGalleryAlbumsByFestival(festivalId) {
  const response = await axiosClient.get(
    `/public/gallery/festival/${festivalId}`
  );

  return response.data;
}

export async function getGalleryAlbumsByDay(festivalDayId) {
  const response = await axiosClient.get(
    `/public/gallery/day/${festivalDayId}`
  );

  return response.data;
}