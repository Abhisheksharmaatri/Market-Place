import { useEffect, useState } from "react";
import { getAllProducts, createProduct, deleteProduct } from "../../api/productApi";
import { Link } from "react-router-dom";
import CreateProductCard from "../../components/product/CreateProductCard";
import "../../public/product.css"
import ProductListCard from "../../components/product/ProductListCard"

/**
 * CHANGE MADE:
 * - Simple API call to product-service via gateway
 */
export default function ProductsPage() {
  const [products, setProducts] = useState([]);

  const[name, setName]=useState("")
  const [description, setDescription]=useState("")

  useEffect(() => {
    getAllProducts().then(setProducts);
  }, []);

  useEffect(() => {
  console.log("Products updated:", products);
}, [products]);

  const handleSubmit = async (e) => {
      e.preventDefault();

      const newProduct = {
        name,
        description
      };

      await createProduct(newProduct);
      getAllProducts().then(setProducts);

      // CHANGE MADE: reset form
      setName("");
      setDescription("");
    };



  return (
    <div className="product-container">
      <h2>Products Catalog</h2>
      <CreateProductCard
        name={name}
        description={description}
        setName={setName}
        setDescription={setDescription}
        handleSubmit={handleSubmit}
      />
      
      {/* Product List as a Grid */}
      <ul className="product-grid">
        {products.map((p) => (
          <ProductListCard key={p.id} product={p} />
        ))}
      </ul>

      <hr />
    </div>
  );
}

