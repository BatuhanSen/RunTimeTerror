import React, { Component } from "react";

class Home extends Component {
  render() {
    return (
      <div>
        <h1>HOME PAGE</h1>
        <div className="field buttons">
            <button onClick={() => (window.location = "/user")}>
              UserPage
            </button>
          </div>
          <div className="field buttons">
            <button onClick={() => (window.location = "/emergency")}>
              EmergencyPage
            </button>
          </div>
          <div className="field buttons">
            <button onClick={() => (window.location = "/contact")}>
              ContactPage
            </button>
          </div>
      </div>
      
    );
  }
}

export default Home;
