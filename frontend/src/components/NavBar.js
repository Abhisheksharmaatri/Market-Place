import { Link } from "react-router-dom"
import "../public/NavBar.css"

export default function NavBar(){
    return(
        <nav className="main-nav">

            <Link to="/home" className="nav-link">Home</Link>
            <span className="nav-separator">|</span>
            <Link to="/products" className="nav-link">Products</Link>
            <span className="nav-separator">|</span>
            <Link to="/inventory" className="nav-link">Inventory</Link>
            <span className="nav-separator">|</span>
            <Link to="/orders" className="nav-link">Orders</Link>
        </nav>
    )
}