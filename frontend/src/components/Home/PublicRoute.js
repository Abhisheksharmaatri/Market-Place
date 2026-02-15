// PublicRoute.jsx
import { Navigate } from "react-router-dom";

export default function PublicRoute({ children }) {
  const token = localStorage.getItem("token");

  if (token) {
    // If already logged in → redirect to home
    return <Navigate to="/home" replace />;
  }

  return children;
}
