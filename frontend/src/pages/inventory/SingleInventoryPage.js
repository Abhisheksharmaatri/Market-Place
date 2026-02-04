import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
// import { getOneInventory, deleteInventory, updateInventory } from "../../api/inventoryApi";
import { getOneInventory, deleteInventory, updateInventory } from "../../api/inventoryApi";

export default function SingleInventoryPage(){
    const [inventory, setInventory]=useState([]);
    const [product, setProduct]=useState([])
    const {id}=useParams();
    const navigate=useNavigate()

    const [amount, setAmount]=useState(0);
    const [price, setPrice]=useState(0);

    const handleDelete=async(e)=>{
        await deleteInventory(id);
        navigate("/inventory")
    }

    const handleUpdate=async(e)=>{
        e.preventDefault();
        const updatedInventory={
            productId:inventory.productId,
            price:price,
            amount:amount
        }
        await updateInventory(updatedInventory, id);
    }

    // Inside your fetch useEffect
    useEffect(() => {
        getOneInventory(id).then((data) => {
            setInventory(data);
            setAmount(data.amount ?? 0); // Set them here once
            setPrice(data.price ?? 0);   // Set them here once
        });
        // getOneProduct(inventory.productId).then(setProduct);
    }, [id]);

// REMOVE the separate useEffect that watches [inventory]

    return(
        <div className="single-order-container">
            <p className="order-header-title">Inventory Management Detail</p>

            {inventory ? (
                <>
                    {/* Current Status Block */}
                    <div className="inventory-meta">
                        <strong>Product ID:</strong> {inventory.productId}
                        <p>Current Price in System: ₹{inventory.price}</p>
                    </div>

                    <div className="total-section">
                        <span>Stock Remaining:</span>
                        <strong>{inventory.amount} Units</strong>
                    </div>

                    {/* Update Form Section */}
                    <div className="inventory-update-section">
                        <h3>Update Stock & Price</h3>
                        <form onSubmit={handleUpdate}>
                            <div className="form-row">
                                <div className="input-group-qty">
                                    <label className="input-label">New Amount</label>
                                    <input
                                        className="form-input"
                                        type="number"
                                        placeholder="Amount"
                                        value={amount}
                                        onChange={(e) => setAmount(Number(e.target.value))}
                                        required
                                    />
                                </div>
                                <div className="input-group-qty">
                                    <label className="input-label">New Price</label>
                                    <input
                                        className="form-input"
                                        type="number"
                                        placeholder="Price"
                                        value={price}
                                        onChange={(e) => setPrice(Number(e.target.value))}
                                        required
                                    />
                                </div>
                            </div>

                            <button type="submit" className="btn-submit">
                                Save Inventory Changes
                            </button>
                        </form>
                    </div>

                    {/* Delete Action */}
                    <button 
                        type="button" 
                        className="btn-delete-order" 
                        onClick={() => handleDelete(id)}
                    >
                        Remove Item From Inventory
                    </button>
                </>
            ) : (
                <p>Loading inventory data...</p>
            )}
        </div>
    )
}