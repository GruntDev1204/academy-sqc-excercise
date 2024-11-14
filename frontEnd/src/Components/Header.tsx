import React from "react"
import { Link } from "react-router-dom"

const Header : React.FC = () =>{
    return(
        <div className="header">
            <ul className="list">
                <li> <Link to="buoi-hoc-1">Buổi 1</Link></li>
                <li><Link to="buoi-hoc-2">Buổi 2</Link></li>
                <li><Link to="buoi-hoc-3">Buổi 3</Link></li>
            </ul>
        

        </div>
    )
}

export default Header