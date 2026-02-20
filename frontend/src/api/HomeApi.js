import axios from "axios";
import SERVICE_URLS from "./config";

export const getUser=async()=>{
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login";
        return;
      }

      const response = await fetch(
          `${SERVICE_URLS.url}/user`,
//      "https://market-place-api-3bbo.onrender.com/api/order",
//      "https://market-place-user.onrender.com/api/user",
          {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json"
            }
          }
        );

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
}