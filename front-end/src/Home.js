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

          

          {/* Eklendi baslangic */}
          <div className="field buttons">
            <button onClick={() => (window.location = "/blogpage")}>
              BlogPage
            </button>
          </div>
          {/* bitis */}




      </div>
      
    );
  }
}

export default Home;
