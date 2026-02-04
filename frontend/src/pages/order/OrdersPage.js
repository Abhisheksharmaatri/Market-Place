import { useEffect, useState } from "react";
import { getOrders, createOrder } from "../../api/orderApi";
import { getAllProducts } from "../../api/productApi";
import { Link } from "react-router-dom";
import "../../public/order.css"
import { getOneProduct } from "../../api/productApi";
import OrderListItemCard from "../../components/order/orderListItemCard";
import CreateOrderCard from "../../components/order/createOrderCard";

export default function OrdersPage() {
  const [orders, setOrders] = useState([]);
  const [productList, setProductList] = useState([]);
  const [userId] = useState(1); 
  const [orderItems, setOrderItems] = useState([{ productId: "", amount: 1 }]);
  // CHANGE: added error and loading states
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // CHANGE: separate error handling for orders & products
  const refreshData = async () => {
    setError(null);
    setLoading(true);

    try {
      const [ordersRes, productsRes] = await Promise.all([
        getOrders(),
        getAllProducts()
      ]);

      const enrichedOrders = await Promise.all(
        ordersRes.map(async (o) => {
          const finishedProducts = await enrichProducts(o.orderItemList);

          return {
            ...o,
            orderItemList: finishedProducts
          };
        })
      );

      setOrders(enrichedOrders);
      setProductList(productsRes);

    } catch (err) {
      handleApiError(err);
    }
      finally {
      setLoading(false);
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

  // CHANGE: centralized error handler
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

  const handleItemChange = (index, field, value) => {
    const updatedItems = [...orderItems];
    updatedItems[index][field] = field === "amount" ? Number(value) : value;
    setOrderItems(updatedItems);
  };

  const addProductRow = () => {
    if (orderItems.length < productList.length) {
      setOrderItems([...orderItems, { productId: "", amount: 1 }]);
    }
  };

  const removeProductRow = (index) => {
    setOrderItems(orderItems.filter((_, i) => i !== index));
  };

  // CHANGE: structured submit handling
const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    if (orderItems.some(item => !item.productId)) {
      setError("Please select a product for all rows.");
      return;
    }

    try {
      setLoading(true);

      await createOrder({ userId, productList: orderItems });

      alert("Order created!");
      setOrderItems([{ productId: "", amount: 1 }]);
      refreshData();

    } catch (err) {
      handleApiError(err);
    } finally {
      setLoading(false);
    }
  };


  return (
    <div className="orders-container">
      {/* CHANGE: global error display */}
        {error && (
          <div className="error-box">
            {error}
          </div>
        )}
        <CreateOrderCard
          orderItems={orderItems}
          productList={productList}
          handleItemChange={handleItemChange}
          addProductRow={addProductRow}
          removeProductRow={removeProductRow}
          handleSubmit={handleSubmit}
        />
      <h2>Orders List</h2>
      {loading && <p>Loading...</p>}
      <ul className="orders-list">
        {orders.map((o) => (
          <OrderListItemCard key={o.id} order={o}/>
        ))}
      </ul>
    </div>
  );
}