import React from 'react';

class EmergencyPage extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        name: '',
        kisi: '',
        durum: ''
      }
    }
  
    render() {
      return(
        <div className="App">
        <div style={{paddingBottom: 10}}>
          <div style={{border: "1px solid gray", textAlign: "center"}}>
            <p> Durumunuz elverişli ise lütfen daha iyi yardım hizmeti alabilmeniz için aşağıdaki kısımları doldurunuz. Aksi durumda sadece "Acil Durum" butonuna basarak konum bilginizi paylaşabilirsiniz.</p>
            </div>
          </div>
          <form id="contact-form" onSubmit={this.handleSubmit.bind(this)} method="POST">
            <div className="form-group">
              <label htmlFor="name">İsim</label>
              <input type="text" className="form-control" value={this.state.name} onChange={this.onNameChange.bind(this)} />
            </div>
            <div className="form-group">
              <label htmlFor="kisi">Kişi Sayısı</label>
              <input type="text" className="form-control" value={this.state.kisi} onChange={this.onKisiChange.bind(this)} />
            </div>
            <div className="form-group">
              <label htmlFor="message">Ekstra Bir İhtiyacınız Var Mı ?</label>
              <textarea className="form-control" rows="5" value={this.state.durum} onChange={this.onMessageChange.bind(this)} />
            </div>
            <button type="submit" className="btn btn-primary">Acil Durum</button>
          </form>
        </div>
      );
    }
  
    onNameChange(event) {
      this.setState({name: event.target.value})
    }
  
    onKisiChange(event) {
      this.setState({kisi: event.target.value})
    }
  
    onMessageChange(event) {
      this.setState({durum: event.target.value})
    }
  
    handleSubmit(event) {
    }
  }
  
  export default EmergencyPage;