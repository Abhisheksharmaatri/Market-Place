import { BrowserRouter, Routes, Route, Link,  Navigate } from "react-router-dom";
import ProductsPage from "./pages/product/ProductsPage";
import InventoryPage from "./pages/inventory/InventoryPage";
import OrdersPage from "./pages/order/OrdersPage";
import HomePage from "./pages/HomePage";
import SingleProductPage from "./pages/product/SingleProductPage";
import SingleInventoryPage from "./pages/inventory/SingleInventoryPage";
import SingleOrderPage from "./pages/order/SingleOrderPage";
import NavBar from "./components/NavBar";
import LoginPage from "./components/Login/LoginPage";
import SignUp from "./components/Login/SignUp";
import ProtectedRoute from "./components/Home/ProtectedRoute";
import PublicRoute from "./components/Home/PublicRoute";

export default function App() {
    const token = localStorage.getItem("token");

  return (
    <BrowserRouter>
          <NavBar />

          <Routes>
            {/* Public Routes */}
            <Route
              path="/login"
              element={
                <PublicRoute>
                  <LoginPage />
                </PublicRoute>
              }
            />
            <Route
              path="/signup"
              element={
                <PublicRoute>
                  <SignUp />
                </PublicRoute>
              }
            />

            {/* Protected Routes */}
            <Route
              path="/home"
              element={
                <ProtectedRoute>
                  <HomePage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/products"
              element={
                <ProtectedRoute>
                  <ProductsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/products/:id"
              element={
                <ProtectedRoute>
                  <SingleProductPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/inventory"
              element={
                <ProtectedRoute>
                  <InventoryPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/inventory/:id"
              element={
                <ProtectedRoute>
                  <SingleInventoryPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/orders"
              element={
                <ProtectedRoute>
                  <OrdersPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/orders/:id"
              element={
                <ProtectedRoute>
                  <SingleOrderPage />
                </ProtectedRoute>
              }
            />

            {/* Default Route */}
            <Route path="*" element={<Navigate to="/home" replace />} />
          </Routes>
        </BrowserRouter>
  );
}
