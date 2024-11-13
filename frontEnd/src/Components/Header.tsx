import React from "react"
import { Link } from "react-router-dom"

const Header : React.FC = () =>{
    return(
        <div className="header">
            <ul className="list">
                <li> <Link to="buoi-hoc-1">Buổi 1</Link></li>
                <li>buổi 2</li>
                <li>buổi 3</li>
            </ul>
        

        </div>
    )
}

export default Header