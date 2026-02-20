import { Link } from "react-router-dom";
// CHANGE: reusable compact order card
export default function OrderListItemCard({ order }) {
  return (
    <li className="order-card compact">

      {/* Top meta row */}
      <div className="order-meta">
        <span className="order-id">Order: {order.id}</span>
        <span className="order-user">User: {order.userId}</span>
      </div>

      {/* Items table */}
      <div className="order-items">
        <div className="order-items-header">
          <span>Product</span>
          <span>Qty</span>
          <span>Price</span>
          <span>Effective Price</span>
        </div>

        {order.orderItemList?.map((item, idx) => (
          <div
            key={`${order.id}-${item.productId}-${idx}`}
            className="order-item-row"
          >
            <span className="product-name">{item.name}</span>
            <span className="product-qty">{item.amount}</span>
            <span className="product-price">₹{item.price}</span>
            <span className="product-effective-price">₹{item.price*item.amount}</span>
          </div>
        ))}
      </div>

      {/* Bottom actions */}
      <div className="order-footer">
        <div className="order-total">
          Total: <strong>₹{order.total}</strong>
        </div>
        <div className="order-total">
          Create At: <strong>{order.createdAt}</strong>
        </div>
        <div className="order-total">
          User: <strong>{order.userId}</strong>
        </div>

        <Link
          to={`/orders/${order.id}`}
          className="order-action-btn"
        >
          View
        </Link>
      </div>

    </li>

  );
}
