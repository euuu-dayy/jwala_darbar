import axiosClient from "./axiosClient";

export const getAnnouncements = async () => {
  const response = await axiosClient.get("/public/announcements");
  return response.data;
};

export const getActiveAnnouncements = async () => {
  const response = await axiosClient.get(
    "/public/announcements/active"
  );

  return response.data;
};

export const getAnnouncementsByFestival = async (festivalId) => {
  const response = await axiosClient.get(
    `/public/announcements/festival/${festivalId}`
  );

  return response.data;
};