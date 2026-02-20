import { useEffect, useState } from "react";
import {getOrders, createOrder} from "../api/orderApi";
import {getUser} from "../api/HomeApi.js"


export default function HomePage(){
    const [user, setUser]=useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const refreshData =async()=>{
        setError(null);
        setLoading(true);
        try{
            const userRes=await getUser();
            console.log("User:",userRes);
            setUser(userRes);
        }
        catch (err) {
            console.log(err)
            handleApiError(err);
        }
        finally {
            setLoading(false);
        }
    }
    const handleApiError = (err) => {
        // Network / server not reachable
        if (!err.response) {
          setError("Unable to connect to server. Please try again later.");
          return;
        }

        const { status, message } = err.response.data || {};

        switch (status) {
          case 400:
            setError(message || "Invalid request.");
            break;
          case 404:
            setError(message||"Requested resource not found.");
            break;
          case 409:
            setError(message || "Conflict occurred.");
            break;
          case 500:
            setError(message || "Internal server error. Please try again.");
            break;
          default:
            setError("Something went wrong.");
        }
      };
  useEffect(() => {
    refreshData();
  }, []);
    return(
        <div>
            <h1>Home</h1>
            <h2>Hello {user.name} welcome</h2>
            <p>This is the home page for your Marketplace. </p>
            <p>MarketPlace is a management system for your products, order and inventory.</p>
        </div>
    )
}