/*
 * Components
 */

var EmergencyPage = React.createClass({
    propTypes: {
      value: React.PropTypes.object.isRequired,
      onChange: React.PropTypes.func.isRequired,
      onSubmit: React.PropTypes.func.isRequired
    },
    
    onNameChange: function(e) {
      this.props.onChange(Object.assign({}, this.props.value, {kisisayisi: e.target.value}));
    },
    
    onEmailChange: function(e) {
      this.props.onChange(Object.assign({}, this.props.value, {konum: e.target.value}));
    },
    
    onDescriptionChange: function(e) {
      this.props.onChange(Object.assign({}, this.props.value, {gerekenler: e.target.value}));
    },
  
    onSubmit: function(e) {
      e.preventDefault();
      this.props.onSubmit();
    },
  
    render: function() {
      var errors = this.props.value.errors || {};
  
      return (
        React.createElement('form', {onSubmit: this.onSubmit, className: 'EmergencyPage', noValidate: true},
          React.createElement('input', {
            type: 'text',
            placeholder: 'İsminizi Giriniz',
            value: this.props.value.kisisayisi,
            onChange: this.onNameChange,
          }),
          React.createElement('input', {
            type: 'location',
            placeholder: 'Konumunuzu giriniz',
            value: this.props.value.konum,
            onChange: this.onEmailChange,
          }),
          React.createElement('textarea', {
            placeholder: 'Durumunuzu ve ihtiyaçlarınızı bildiriniz',
            value: this.props.value.gerekenler,
            onChange: this.onDescriptionChange,
          }),
          React.createElement('button', {type: 'submit', className: "btn btn-info"}, "Acil Durum Bildir")
        )
      );
    },
  });
  
  
  var EmergencyItem = React.createClass({
    propTypes: {
      kisisayisi: React.PropTypes.string.isRequired,
      konum: React.PropTypes.string.isRequired,
      gerekenler: React.PropTypes.string,
    },
  
    render: function() {
      return (
        React.createElement('li', {className: 'EmergencyItem'},
          React.createElement('h2', {className: 'EmergencyItem-kisisayisi'}, this.props.kisisayisi),
          React.createElement('a', {className: 'EmergencyItem-konum'}, this.props.konum),
          React.createElement('div', {className: 'EmergencyItem-gerekenler'}, this.props.gerekenler)
        )
      );
    },
  });
  
  
  var EmergencyView = React.createClass({
    propTypes: {
      emergencies: React.PropTypes.array.isRequired,
      newEmergency: React.PropTypes.object.isRequired,
      onNewEmergencyChange: React.PropTypes.func.isRequired,
      onNewEmergencySubmit: React.PropTypes.func.isRequired,
    },
  
    render: function() {
      var emergencyItemElements = this.props.emergencies
        .filter(function(emergency) { return emergency.konum; })
        .map(function(emergency) { return React.createElement(EmergencyItem, emergency); });
  
      return (
        React.createElement('div', {className: 'EmergencyView'},
          React.createElement('h1', {className: 'EmergencyView-title'}, "Emergencies"),
          React.createElement('ul', {className: 'EmergencyView-list'}, emergencyItemElements),
          React.createElement(EmergencyPage, {
            value: this.props.newEmergency,
            onChange: this.props.onNewEmergencyChange,
            onSubmit: this.props.onNewEmergencySubmit,
          })
        )
      );
    },
  });
  
  
  /*
   * Constants
   */
  
  
  var EMERGENCY_TEMPLATE = {kisisayisi: "", konum: "", gerekenler: "", errors: null};
  
  
  
  /*
   * Actions
   */
  
  
  function updateNewEmergency(emergency) {
    setState({ newEmergency: emergency });
  }
  
  
  function submitNewEmergency() {
    var emergency = Object.assign({}, state.newEmergency, {key: state.emergencies.length + 1, errors: {}});
    
    if (!emergency.kisisayisi) {
      emergency.errors.kisisayisi = ["Kisi sayisini giriniz"];
    }
    if (!emergency.konum) {
      emergency.errors.konum = ["Konum Giriniz"];
    }
  
    setState(
      Object.keys(emergency.errors).length === 0 ? {
          newContact: Object.assign({}, EMERGENCY_TEMPLATE),
          emergencies: state.emergencies.slice(0).concat(emergency),
        }
      : { newEmergency: emergency }
    );
  }
  
  
  /*
   * Model
   */
  
  
  // The app's complete current state
  var state = {};
  
  // Make the given changes to the state and perform any required housekeeping
  function setState(changes) {
    Object.assign(state, changes);
    
    ReactDOM.render(
      React.createElement(EmergencyView, Object.assign({}, state, {
        onNewEmergencyChange: updateNewEmergency,
        onNewEmergencySubmit: submitNewEmergency,
      })),
      document.getElementById('react-app')
    );
  }
  
  // Set initial data
  setState({
    emergencies: [
      {key: 1, name: "HowToSurvive", email: "howtosurvive@gmail.com", description: ""},
      {key: 2, name: "", email: ""},
    ],
    newEmergency: Object.assign({}, EMERGENCY_TEMPLATE),
  });
  