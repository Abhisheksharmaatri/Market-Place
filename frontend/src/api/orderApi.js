import axios from "axios";
import SERVICE_URLS from "./config";

export const getOrders = async () => {
  const token = localStorage.getItem("token");

  // 🔒 If token doesn't exist → redirect
  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/order`,
//"https://market-place-api-3bbo.onrender.com/api/order",
    {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    }
  );

  // 🔒 If token expired / invalid → redirect
  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  if (!response.ok) {
    throw new Error("Failed to fetch orders");
  }

  // ✅ Read body ONCE
  const data = await response.json();

  console.log("Orders:", data); // Now real JSON
  return data;
};




export const createOrder = async (order) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  try {
    const response = await axios.post(
       `${SERVICE_URLS.url}/order`,
//      "https://market-place-api-3bbo.onrender.com/api/order",
      order,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;

  } catch (error) {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
      return;
    }
    throw error;
  }
};


export const deleteOrder = async (id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  try {
    const response = await axios.delete(
      `${SERVICE_URLS.url}/order`,
      {
        params: {
          id: Number(id),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;

  } catch (error) {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
      return;
    }
    throw error;
  }
};



export const getOneOrder = async (id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  try {
    const response = await axios.get(
      `${SERVICE_URLS.url}/order/item`,
      {
        params: {
          id: Number(id),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;

  } catch (error) {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
      return;
    }
    throw error;
  }
};
