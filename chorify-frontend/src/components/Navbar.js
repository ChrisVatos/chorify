import React from 'react'
import { NavLink } from 'react-router-dom'
import './Navbar.css'

function Navbar() {
  return (
    <nav className="navbar">
      <ul className="navbar__list">
        <div className="navbar__links">
          <NavLink 
            to="/chores"  
            className={({ isActive }) =>
              isActive ? "active" : undefined
          }>
            Chores
          </NavLink>

          <NavLink 
            to="/members" 
            className={({ isActive }) =>
                  isActive ? "active" : undefined
          }>
            House Members
          </NavLink>

          <NavLink 
            to="/managers/newManager" 
            className={({ isActive }) =>
              isActive ? "active" : undefined
          }>
          House Managers
          </NavLink>
        </div>

        <h3 className="navbar__app__name">Chorify</h3>
      </ul>
    </nav>
  );
}

export default Navbar;
