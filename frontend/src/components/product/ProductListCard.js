import { Link } from "react-router-dom";

// CHANGE: single product list item component
export default function ProductListCard({ product }) {
  return (
    <li className="product-card">
      <div className="product-card-body">
        <h4 className="product-title">{product.name}</h4>
        <p className="product-description">{product.description}</p>
      </div>

      <Link
        to={`/products/${product.id}`}
        className="product-action-link"
      >
        Edit →
      </Link>
    </li>
  );
}
