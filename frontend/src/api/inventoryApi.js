import axios from "axios";
import SERVICE_URLS from "./config";

export const getInventory = async () => {
  const token = localStorage.getItem("token");

  // 🔒 No token → go to login
  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory`,
    {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    }
  );

  // 🔒 Token invalid / expired → logout
  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  if (!response.ok) {
    throw new Error("Failed to fetch inventory");
  }

  return await response.json();
};



export const createInventory = async (inventory) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory`,
    {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(inventory),
    }
  );

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  return await response.json();
};



export const updateInventory = async (inventory, id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  const { productId, price, amount } = inventory;

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory?id=${Number(id)}`,
    {
      method: "PUT",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        productId,
        price,
        amount,
      }),
    }
  );

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  return await response.json();
};

export const deleteInventory = async (id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory?id=${Number(id)}`,
    {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    }
  );

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  return await response.json();
};



export const getOneInventory = async (id) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory/item?id=${Number(id)}`,
    {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    }
  );

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  return await response.json();
};


export const getOneInventoryByProductId = async (productId) => {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "/login";
    return;
  }

  const response = await fetch(
    `${SERVICE_URLS.url}/inventory/product?productId=${Number(productId)}`,
    {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    }
  );

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/login";
    return;
  }

  return await response.json();
};

