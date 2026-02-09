import { useEffect, useState } from "react";
import { createInventory, getInventory } from "../../api/inventoryApi";
import { Link } from "react-router-dom";
import { getAllProducts } from "../../api/productApi";
import CreateInventoryCard from "../../components/inventory/CreateInventoryCard";

export default function InventoryPage() {
  const [items, setItems] = useState([]); 
  const [productList, setProductList] = useState([]);
  const [productId, setProductId] = useState("");
  const [amount, setAmount] = useState(0);
  const [price, setPrice] = useState(0);

  useEffect(() => {
    getInventory().then((data) => setItems(data));
    getAllProducts().then((data) => setProductList(data));
  }, []);

  // --- FILTER LOGIC ---
  // We filter the productList to only include products whose ID 
  // is NOT found in the current inventory items.
  const availableProducts = productList.filter((product) => {
    return !items.some((item) => item.productId === product.id);
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Use 'return' instead of 'break'
    if (productId === "") {
      alert("Please select a product");
      return; 
    }

    const newInventory = {
      productId,
      amount: Number(amount),
      price: Number(price)
    };

    await createInventory(newInventory);
    
    // Refresh inventory list
    const updatedData = await getInventory();
    setItems(updatedData);
    
    // Reset form
    setProductId("");
    setAmount(0);
    setPrice(0);
  };

  return (
    <div className="inventory-container">
      <h2>Current Inventory</h2>
      <ul className="inventory-list">
        {items.map((i) => (
          <li key={i.productId} className="inventory-item">
            <div>
              <span className="item-main-info">Product ID: {i.productId}</span>
              <span className="item-sub-info">
                ({i.amount} units available at ${i.price})
              </span>
            </div>
            <Link to={`/inventory/${i.id}`} className="edit-link">Edit Stock</Link>
          </li>
        ))}
      </ul>

      <hr />
      <CreateInventoryCard
        availableProducts={availableProducts}
        productListLength={productList.length}
        productId={productId}
        setProductId={setProductId}
        price={price}
        setPrice={setPrice}
        amount={amount}
        setAmount={setAmount}
        onSubmit={handleSubmit}
      />

      
    </div>
  );
}