import React from 'react'
import { NavLink } from 'react-router-dom'
import './Navbar.css'

function Navbar() {
  return (
    <nav className="navbar" >
      <ul>
        <NavLink to="/chores">
          Chores
        </NavLink>

        <NavLink to="/members">
          House Members
        </NavLink>

        <NavLink to="/managers">
        House Managers
        </NavLink>
      </ul>
    </nav>
  );
}

export default Navbar;
