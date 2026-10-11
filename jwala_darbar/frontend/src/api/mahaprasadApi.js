import axiosClient from "./axiosClient";

export const getMahaprasad = async () => {
  const response = await axiosClient.get("/public/mahaprasad");
  return response.data;
};

export const getMahaprasadByFestival = async (festivalId) => {
  const response = await axiosClient.get(
    `/public/mahaprasad/festival/${festivalId}`
  );

  return response.data;
};

export const getMahaprasadByDay = async (festivalDayId) => {
  const response = await axiosClient.get(
    `/public/mahaprasad/day/${festivalDayId}`
  );

  return response.data;
};

export const getTodayMahaprasad = async () => {
  const response = await axiosClient.get(
    "/public/mahaprasad/today"
  );

  return response.data;
};

export const getMahaprasadByDate = async (date) => {
  const response = await axiosClient.get(
    `/public/mahaprasad/date/${date}`
  );

  return response.data;
};