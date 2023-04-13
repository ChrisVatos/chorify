import React from "react";
import "./HouseManagerCard.css";

function HouseManagerCard(props) {

  return (
    <div>
      <div className="main__houseManager__card">
        <div className="houseManager__ID__section">
          <h3>ID</h3>
          <h4>{props.id}</h4>
        </div>

        <div className="houseManager__name__section">
          <h3>Name</h3>
          <h4>{props.name}</h4>
        </div>

        <div className="houseManager__email__section">
          <h3>Email</h3>
          <h4>{props.email}</h4>
        </div>

        <div className="houseManager__phoneNumber__section">
          <h3>Phone Number</h3>
          <h4>{props.phoneNumber}</h4>
        </div>

        <div className="houseManager__type__section">
          <h3>House Manager Type</h3>
          <h4>{props.type}</h4>
        </div>

       
      </div>
    </div>
  );
}

export default HouseManagerCard;
