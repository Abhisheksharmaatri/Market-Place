import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import { deleteOrder, getOneOrder } from "../../api/orderApi";
import { getOneProduct } from "../../api/productApi";


export default function SingleOrderPage(){
    const [order, setOrder]=useState([]);
    const {id} =useParams();
     const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);   

    const navigate=useNavigate();

    useEffect(() => {
        const fetchAndEnrichOrder = async () => {
            setLoading(true);          // ✅ ADD
            setError(null); 
            try {
            const baseOrder = await getOneOrder(id);

            const enrichedItems = await enrichProducts(baseOrder.orderItemList);

            // CHANGE: replace orderItemList with enriched one
            setOrder({
                ...baseOrder,
                orderItemList: enrichedItems
            });

            } catch (err) {
            handleApiError(err);
            }finally {
            setLoading(false);  
            }
        };

        if (id) {
            fetchAndEnrichOrder();
        }
    }, [id]);

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

     const enrichProducts = async (products) => {
        const enriched = await Promise.all(
            products.map(async (p) => {
            const details = await getOneProduct(p.productId);
            return {
                ...p,
                ...details
            };
            })
        );

        return enriched;
        };

    const handleDelete=async(e)=>{
        e.preventDefault();
        await deleteOrder(id);
        navigate("/orders");
    }


    return(
      <div className="single-order-container">
        <p className="order-header-title">Order Management Detail</p>
        {loading && <p>Loading...</p>}
         {/* CHANGE: global error display */}
        {error && (
        <div className="error-box">
            {error}
        </div>
        )}

        {order && order.orderItemList ? (
            <>
                <div className="order-info-block">
                    <strong>Order ID:</strong> {order.id} <br />
                    <strong>Customer (User ID):</strong> {order.userId}
                </div>

                <ul className="item-grid">
                    {order.orderItemList.map((i) => (
                        <li key={i.productId} className="item-row">
                            <div className="item-details">
                                <p><strong>Product:</strong> {i.name}</p>
                                <p>Quantity: {i.amount}</p>
                            </div>
                            <div className="item-price">
                                ₹{i.price}
                            </div>
                        </li>
                    ))}
                </ul>

                <div className="total-section">
                    <span>Total Amount:</span>
                    <strong>₹{order.total}</strong>
                </div>
                <button 
                    type="button" 
                    className="btn-delete-order" 
                    onClick={handleDelete}
                >
                    Cancel & Delete Order
                </button>
            </>
        ) : (
            <p>Loading order data...</p>
        )}
    </div>
    )
};