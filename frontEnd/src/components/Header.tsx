import React from "react"
import { Link } from "react-router-dom"

const Header: React.FC = () => {
    return (
        <div className="header">
            <ul className="list">
                <li> <Link to="employees">Employees</Link></li>
                <li><Link to="departments">Department</Link></li>
                <li><Link to="market-places">Marketplace</Link></li>
                <li><Link to="type-market-places">Type Marketplace</Link></li>
            </ul>
        </div>
    )
}

export default Header