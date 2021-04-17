import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';



class UserPage extends React.Component {


    
    

    render() {
        const { user, users } = this.props;

        
        return (

            <div className="col-md-6 col-md-offset-3">
                
                
                <div className="form-group">

                <Link to="/changepw">
                    <button className="btn btn-primary">Şifre Değiştir</button>
                </Link>
                </div><br></br><br></br>

                <div className="form-group">

                <Link to="/newloc">
                    <button className="btn btn-primary">Yeni Konum Ekle</button>
                </Link>
                </div><br></br><br></br><br></br>


                <div className={'form-group' }>
                    <label htmlFor="location">Mevcut Konumlar</label>
                </div>


                
                
                 
            </div>
            
        );
    }
    
}



export default UserPage;