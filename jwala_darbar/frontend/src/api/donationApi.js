import axiosClient from "./axiosClient";

export async function getDonationCampaigns() {
  const response = await axiosClient.get(
    "/public/donations/campaigns"
  );

  return response.data;
}

export async function getDonationCampaignsByFestival(festivalId) {
  const response = await axiosClient.get(
    `/public/donations/campaigns/festival/${festivalId}`
  );

  return response.data;
}

export async function getActiveDonationCampaigns() {
  const response = await axiosClient.get(
    "/public/donations/campaigns/active"
  );

  return response.data;
}

export async function createDonation(payload) {
  const response = await axiosClient.post(
    "/public/donations",
    payload
  );

  return response.data;
}

export async function createPaymentTransaction(payload) {
  const response = await axiosClient.post(
    "/public/payments/transactions",
    payload
  );

  return response.data;
}

export async function getDonationReceipt(donationId) {
  const response = await axiosClient.get(
    `/public/donations/${donationId}/receipt`
  );

  return response.data;
}