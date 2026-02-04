import { useEffect, useState } from "react"
import { useParams } from "react-router-dom";

import { getOneProduct, deleteProduct, updateProduct } from "../../api/productApi"


export default function SingleProductPage(){
    const {id}=useParams();
    const [product, setProduct]=useState([]);
    const[name, setName]=useState("")
  const [description, setDescription]=useState("")

  useEffect(()=>{
    getOneProduct(id).then(setProduct)
  },[id])

  useEffect(() => {
    console.log("Products updated:", product);
    }, [product]);


// CHANGE MADE: accept id, not event
  const handleDelete = async (id) => {
    await deleteProduct(id);

    const data = await getOneProduct(); // CHANGE MADE
    setProduct(data);
  };

  const handleUpdate=async(e)=>{
    e.preventDefault();
    const updatedProduct={
        name,
        description
    }
    await updateProduct(updatedProduct, id);
    const data = await getOneProduct(); // CHANGE MADE
    setProduct(data);
    setName("");
      setDescription("");
  }


    return (
       <div className="single-order-container">
        <p className="order-header-title">Product Specification Detail</p>

        {product ? (
            <>
                {/* Current Product Info Block */}
                <div className="product-meta">
                    <p><strong>Current Name:</strong> {product.name}</p>
                    <p><strong>Current Description:</strong> {product.description}</p>
                </div>

                <div className="total-section">
                    <span>System Status:</span>
                    <strong>Active Product</strong>
                </div>

                {/* Update Product Form Section */}
                <div className="product-update-section">
                    <h3>Update Product Details</h3>
                    <form onSubmit={handleUpdate}>
                        <div className="form-row">
                            <div className="input-group-product">
                                <label className="input-label">Product Name</label>
                                <input
                                    className="form-input"
                                    type="text"
                                    placeholder="Edit name"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="input-group-product">
                                <label className="input-label">Description</label>
                                <input
                                    className="form-input"
                                    type="text"
                                    placeholder="Edit description"
                                    value={description}
                                    onChange={(e) => setDescription(e.target.value)}
                                    required
                                />
                            </div>
                        </div>

                        <button type="submit" className="btn-submit">
                            Apply Changes to Catalog
                        </button>
                    </form>
                </div>

                {/* Delete Action */}
                <button 
                    type="button" 
                    className="btn-delete-order" 
                    onClick={() => handleDelete(id)}
                >
                    Permanently Remove Product
                </button>
            </>
        ) : (
            <p>Loading product data...</p>
        )}
    </div>
    )
}