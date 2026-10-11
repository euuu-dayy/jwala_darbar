
import axiosClient from "./axiosClient";

export async function submitContactMessage(payload) {
  const response = await axiosClient.post("/public/contact", payload);
  return response.data;
}
