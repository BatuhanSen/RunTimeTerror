import React, { Component } from 'react';
import './App.css';

import FacebookLogin from 'react-facebook-login';

import GoogleLogin from 'react-google-login';

class App extends Component {

  render() {

    const responseFacebook = (response) => {
      console.log(response);
    }

    const responseGoogle = (response) => {
      console.log(response);
    }

    return (
      <div className="App">
        <h1>LOGIN WITH FACEBOOK AND GOOGLE</h1>

      <FacebookLogin
        appId="3725536324209409"
        fields="name,email,picture"
        callback={responseFacebook}
      />
      <br />
      <br />

      <GoogleLogin
        clientId="425163740233-qs2e96mebnuv7h3kg0g5r2m46iusdroe.apps.googleusercontent.com"
        buttonText="LOGIN WITH GOOGLE"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
      />

      </div>
    );
  }
}

export default App;