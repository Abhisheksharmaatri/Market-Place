/**
 * CHANGE MADE:
 * - Centralized service base URLs
 * - Works for direct ports now
 * - Can switch to gateway later
 */
const SERVICE_URLS = {
//  url:"https://market-place-api-3bbo.onrender.com/api"
//    url:"http://localhost:8080/api"
    url:process.env.REACT_APP_BACKEND_URL
};

export default SERVICE_URLS;


// https://market-place-api-3bbo.onrender.com/api