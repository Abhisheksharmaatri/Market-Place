import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import ProductsPage from "./pages/product/ProductsPage";
import InventoryPage from "./pages/inventory/InventoryPage";
import OrdersPage from "./pages/order/OrdersPage";
import HomePage from "./pages/HomePage";
import SingleProductPage from "./pages/product/SingleProductPage";
import SingleInventoryPage from "./pages/inventory/SingleInventoryPage";
import SingleOrderPage from "./pages/order/SingleOrderPage";
import NavBar from "./components/NavBar";

export default function App() {

  return (
    <BrowserRouter>
      {/* Updated Navigation with Classes */}
      <NavBar/>

      <Routes>
        <Route path="/home" element={<HomePage />} />
        <Route path="/products" element={<ProductsPage />} />
        <Route path="/products/:id" element={<SingleProductPage />} />
        <Route path="/inventory" element={<InventoryPage />} />
        <Route path="/inventory/:id" element={<SingleInventoryPage />} />
        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/orders/:id" element={<SingleOrderPage />} />
      </Routes>
    </BrowserRouter>
  );
}
