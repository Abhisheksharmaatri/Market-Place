export default function CreateProductCard({
  name,
  description,
  setName,
  setDescription,
  handleSubmit
}) {
  return (
    <section className="product-card">
      <h3 className="product-card-title">Add New Product</h3>

      <form onSubmit={handleSubmit} className="product-form">
        <div className="product-form-row">
          <label className="product-label">Product Name</label>
          <input
            className="product-input"
            type="text"
            placeholder="e.g. Wireless Mouse"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="product-form-row">
          <label className="product-label">Product Description</label>
          <input
            className="product-input"
            type="text"
            placeholder="Enter technical specs or details"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>

        <button type="submit" className="product-submit-btn">
          Add Product
        </button>
      </form>
    </section>
  );
}
