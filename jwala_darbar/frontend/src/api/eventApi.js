import axiosClient from "./axiosClient";

export async function getEventsByDay(festivalDayId) {
  const response = await axiosClient.get(
    `/public/events/day/${festivalDayId}`
  );

  return response.data;
}

export async function getEventsByType(
  festivalDayId,
  eventType
) {
  const response = await axiosClient.get(
    `/public/events/day/${festivalDayId}/type/${eventType}`
  );

  return response.data;
}

export async function getTodayEvents() {
  const response = await axiosClient.get(
    "/public/events/today"
  );

  return response.data;
}

export async function getEventsByDate(date) {
  const response = await axiosClient.get(
    `/public/events/date/${date}`
  );

  return response.data;
}