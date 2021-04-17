import React, { Component } from "react";
import { connect } from "react-redux";

import "./Login.css";
import Logo from "../HowToSurvive.png";

import * as actions from "../store/actions/index";

////// Eklendi baslangic
import Register from "../Register/Register";

import FacebookLogin from 'react-facebook-login';
import GoogleLogin from 'react-google-login';
////// bitis


class Login extends Component {
  state = {
    username: "",
    password: "",
  };

  handleUsername = (event) => {
    this.setState({
      ...this.state,
      username: event.target.value,
    });
  };

  handlePassword = (event) => {
    this.setState({
      ...this.state,
      password: event.target.value,
    });
  };

  handleEmail = (event) => {
    this.setState({
      ...this.state,
      email: event.target.value,
    });
  };

  handleLogin = async (e) => {
    e.preventDefault();
    await this.props.login(this.state.username, this.state.password);
  };


  //// Eklendi -  baslangic
  responseFacebook = async (e) => {
    console.log(e);
    console.log(e.name + " " + e.email + " " + e.id);

    await this.props.register(e.name, e.id, e.email, e.name, "5340203593", "m");
    await this.props.login(e.name, e.id);

  }

  responseGoogle = async (e) => {
    
    console.log(e);
    console.log(e.Qs.AT +" "+ e.googleId +" "+ e.profileObj.email +" "+ e.Qs.AT +" " +"5340203593" +" "+ "m");
    await this.props.register(e.profileObj.familyName, e.googleId, e.profileObj.email,e.profileObj.familyName, "5340203593","m");
    await this.props.login(e.profileObj.familyName, e.googleId);
    window.location.href = "/";
    
  }
//// Eklendi -  bitis

  render() {
    return (
      <div className="main">
        <form className="fields" onSubmit={this.handleLogin}>
          <img src={Logo} alt="logo" className="logo" />
          <div className="field">
            <label>Username</label>
            <input
              type="text"
              placeholder="e.g testuser"
              onChange={this.handleUsername}
              required
            />
          </div>
          <div className="field">
            <label>Password</label>
            <input
              type="text"
              placeholder="e.g testuser"
              onChange={this.handlePassword}
              required
            />
          </div>
          <div className="field buttons">
            <button type="submit">Login</button>


            <button onClick={() => (window.location = "/register")}>
              Register
            </button>
          </div>
          {this.props.error != null ? (
            <div>YANLIS KULLANICI ADI YA DA SIFRE</div>
          ) : null}

        <div style={{display: "inline-block"}}>
         {/* Eklendi baslangic*/}
        <FacebookLogin
          appId="3725536324209409"
          fields="name,email,picture"
          callback={this.responseFacebook}
        />
        

        <GoogleLogin
          clientId="425163740233-qs2e96mebnuv7h3kg0g5r2m46iusdroe.apps.googleusercontent.com"
          buttonText="LOGIN WITH GOOGLE"
          onSuccess={this.responseGoogle}
          onFailure={this.responseGoogle}
        />
        {/* Eklendi bitis*/}

        </div>

        </form>
      </div>
    );
  }
}

const mapDispatchToState = (state) => {
  return {
    user: state.auth.user,
    error: state.auth.error,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    login: (username, password) => dispatch(actions.login(username, password)),
    register: (username, password, email, firstName, phone) =>
      dispatch(actions.register(username, password, email, firstName, phone)),
  };
};

export default connect(mapDispatchToState, mapDispatchToProps)(Login);