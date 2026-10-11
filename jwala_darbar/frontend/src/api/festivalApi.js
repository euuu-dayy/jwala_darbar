import axiosClient from "./axiosClient";

export async function getFestivals() {
  const response = await axiosClient.get("/public/festivals");

  return response.data;
}

export async function getCurrentFestivalToday() {
  const response = await axiosClient.get(
    "/public/festivals/current/today"
  );

  return response.data;
}

export async function getFestivalByDate(date) {
  const response = await axiosClient.get(
    `/public/festivals/current/date/${date}`
  );

  return response.data;
}

export async function getFestivalDays(festivalId) {
  const response = await axiosClient.get(
    `/public/festivals/${festivalId}/days`
  );

  return response.data;
}

export async function getFestivalDayByDate(festivalId, date) {
  const response = await axiosClient.get(
    `/public/festivals/${festivalId}/days/date/${date}`
  );

  return response.data;
}