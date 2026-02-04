/**
 * CHANGE MADE:
 * - Centralized service base URLs
 * - Works for direct ports now
 * - Can switch to gateway later
 */
const SERVICE_URLS = {
  product: "http://localhost:8080/api",
  inventory: "http://localhost:8080/api",
  order: "http://localhost:8080/api",
};

export default SERVICE_URLS;
