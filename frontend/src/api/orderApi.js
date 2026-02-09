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
    `${SERVICE_URLS.order}/order`,
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

  return await response.data();
};



export const createOrder = async (order) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  try {
    const response = await axios.post(
      `${SERVICE_URLS.order}/order`,
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



// export const updateOrder=async(order, id)=>{
//   const response=await axios.put(
//     `${SERVICE_URLS.order}/order`,
//     {

//     }
//   )
//   return response;
// }

export const deleteOrder = async (id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  try {
    const response = await axios.delete(
      `${SERVICE_URLS.order}/order`,
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
      `${SERVICE_URLS.order}/order/item`,
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
