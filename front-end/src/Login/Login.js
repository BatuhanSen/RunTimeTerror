import React, { Component } from "react";
import { connect } from "react-redux";

import "./Login.css";
import Logo from "../HowToSurvive.png";

import * as actions from "../store/actions/index";

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

  handleLogin = (e) => {
    e.preventDefault();
    this.props.login(this.state.username, this.state.password);
  };

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
              type="password"
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
            <div className="warning"> {this.props.error.data.message + "!!!"}</div>
          ) : null}
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
