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
  const [message, setMessage]=useState("");

  useEffect(() => {

  const fetchData = async () => {

    const inventoryRes = await getInventory();
    const productRes = await getAllProducts();

    // Create product map
    const productMap = new Map(
      productRes.map(product => [product.id, product])
    );

    // Merge directly using fresh API data (NOT state)
    const mergedList = inventoryRes.map(item => {
      const product = productMap.get(item.productId);

      return {
        ...item,
        name: product ? product.name : "Product not found in database",
        description: product
          ? product.description
          : "Product not found in database"
      };
    });

    setItems(mergedList); // ✅ set merged result once

    console.log(mergedList);
  };

  fetchData();

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

    const response=await createInventory(newInventory);
    if(response.ok){const updatedData = await getInventory();
    setItems(updatedData);
    
    // Reset form
    setProductId("");
    setAmount(0);
    setPrice(0);
    console.log(productList)
    console.log(items)}
    else{
      setMessage("Some error encountered while creating the inventory.");
    }
    
    // Refresh inventory list
  };

  return (
    <div className="inventory-container">
      <h2>Current Inventory</h2>
      <ul className="inventory-list">
        {items.map((i) => (
          <li key={i.productId} className="inventory-item">
            <div className="total-section">
              <span className="item-main-info">Product ID: {i.productId}</span>
              <span className="item-sub-info">
                ({i.amount} units available at ${i.price})
              </span>
            </div>
            <div className="total-section">
              <span className="item-main-info">Product name: {i.name}</span>
              <span className="item-sub-info">
                ({i.description})
              </span>
            </div>
            <Link to={`/inventory/${i.id}`} className="btn-submit">Edit Stock</Link>
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

      <div className="total-section">
                        <strong>{message}</strong>
                    </div>
    </div>
  );
}