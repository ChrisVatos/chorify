import React from 'react'
import { NavLink } from 'react-router-dom';

function HouseMembers() {
  return (
    <>
      <div>
        <NavLink to="newMember"><button>New Member</button></NavLink>
        <NavLink><button>See Members</button></NavLink>
      </div>
      <h1>House Members</h1>
    </>
  );
}

export default HouseMembers;
