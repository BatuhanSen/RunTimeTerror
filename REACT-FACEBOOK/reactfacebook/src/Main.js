import React, { Component } from "react";
import {Route,NavLink,HashRouter} from "react-router-dom";
import AnaSayfa from "./AnaSayfa";
import AcilDurum from "./AcilDurum";
import Iletisim from "./Iletisim";

class Main extends Component {
  render() {

    return (
        <HashRouter>
        <div>
          <h1>HOW TO SURVIVE WEB UYGULAMASI</h1>
          <ul className="header">
            <li><NavLink exact to="/">Ana Sayfa</NavLink></li>
            <li><NavLink to="/acilDurum">Acil Durum</NavLink></li>
            <li><NavLink to="/iletisim">Iletisim</NavLink></li>
          </ul>
          <div className="content">
            <Route exact path="/" component={AnaSayfa}/>
            <Route path="/acilDurum" component={AcilDurum}/>
            <Route path="/iletisim" component={Iletisim}/>
          </div>
        </div>
        </HashRouter>

    );
  }
}
 
export default Main;