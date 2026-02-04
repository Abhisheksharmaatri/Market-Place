import axios from "axios";

/**
 * CHANGE MADE:
 * - Single entry point (API Gateway)
 * - Frontend never talks to individual services directly
 */
const api = axios.create({
  // baseURL: "http:/", // gateway
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
