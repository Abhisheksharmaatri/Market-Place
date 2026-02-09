const CreateInventoryCard = ({
  availableProducts,
  productListLength,
  productId,
  setProductId,
  price,
  setPrice,
  amount,
  setAmount,
  onSubmit,
}) => {
  return (
    <section className="create-order-section">
      <h3>Add Inventory for New Product</h3>

      <form onSubmit={onSubmit}>
        <div className="form-row">
          <div className="input-group-product">
            <label className="input-label">Select Product</label>

            <select
              className="form-input"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
              required
            >
              <option value="">-- Select a Product --</option>

            {availableProducts.map((product) => (
                <option key={product.id} value={product.id}>
                  {product.name}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="form-row">
          <div className="input-group-qty">
            <label className="input-label">Price</label>
            <input
              className="form-input"
              type="number"
              value={price}
              onChange={(e) => setPrice(Number(e.target.value))}
              required
            />
          </div>

          <div className="input-group-qty">
            <label className="input-label">Initial Amount</label>
            <input
              className="form-input"
              type="number"
              value={amount}
              onChange={(e) => setAmount(Number(e.target.value))}
              required
            />
          </div>
        </div>

        <button type="submit" className="btn-submit">
          Create Inventory Entry
        </button>
      </form>

      {availableProducts.length === 0 && productListLength > 0 && (
        <p className="status-msg-warning">
          All products already have inventory assigned.
        </p>
      )}
    </section>
  );
};

export default CreateInventoryCard;
