// CHANGE: extracted create-order UI into its own card component
export default function CreateOrderCard({
  orderItems,
  productList,
  handleItemChange,
  addProductRow,
  removeProductRow,
  handleSubmit,
  inventoryList
}) {
  const inventoryMap = inventoryList.reduce((acc, item) => {
      acc[item.productId] = item.amount;
      return acc;
    }, {});
  return (
    <section className="create-order-section">
      <h3>Create New Order</h3>

      <form onSubmit={handleSubmit}>
        {orderItems.map((item, index) => {
          const otherSelectedIds = orderItems
            .filter((_, i) => i !== index)
            .map(r => r.productId);

          const availableForThisRow = productList
            .filter(p => !otherSelectedIds.includes(p.id));

          return (
            <div key={index} className="form-row">
              <div className="input-group-product">
                <label className="input-label">Product</label>
                <select
                  className="form-input"
                  value={item.productId}
                  onChange={(e) =>
                    handleItemChange(index, "productId", e.target.value)
                  }
                  required
                >
                  <option value="">-- Select Product --</option>
                  {productList.map((p) => {
                    const availableQty = inventoryMap[p.id] || 0;
                    const isOutOfStock = availableQty === 0;

                    return (
                      <option
                        key={p.id}
                        value={p.id}
                        disabled={isOutOfStock}
                        style={{
                          color: isOutOfStock ? "#aaa" : "black"
                        }}
                      >
                        {p.name}{" "}
                        {isOutOfStock
                          ? "(0 - Out of Stock)"
                          : `(${availableQty} available)`
                        }
                      </option>
                    );
                  })}

                </select>
              </div>

              <div className="input-group-qty">
                <label className="input-label">Quantity</label>
                <input
                  className="form-input"
                  type="number"
                  min="1"
                  value={item.amount}
                  onChange={(e) =>
                    handleItemChange(index, "amount", e.target.value)
                  }
                  required
                />
              </div>

              {orderItems.length > 1 && (
                <button
                  type="button"
                  className="btn-remove"
                  onClick={() => removeProductRow(index)}
                >
                  Remove
                </button>
              )}
            </div>
          );
        })}

        <button
          type="button"
          className="btn-add"
          onClick={addProductRow}
          disabled={orderItems.length >= productList.length}
        >
          + Add Another Product
        </button>

        <button type="submit" className="btn-submit">
          Submit Order
        </button>
      </form>
    </section>
  );
}
