import React from 'react'
import { NavLink } from 'react-router-dom'
import './HouseManagersLandingPage.css'
import ManagerForm from '../components/ManagerForm'

function HouseManagersLandingPage() {
  return (
    <div className="house__manager__greeting">
        <ManagerForm method="POST" title="Create New House Manager" isForEdit={false} currentData={null}/>
    </div>
  )
}

export default HouseManagersLandingPage;
