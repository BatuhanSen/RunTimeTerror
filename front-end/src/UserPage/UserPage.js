import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';



class UserPage extends React.Component {


    
    

    render() {

        
        return (

            <div className="main">
                
                
                 {/* <div className="form-group">

              <Link to="/changepw">
                    <button className="btn btn-primary">Şifre Değiştir</button>
                </Link>
        </div><br></br><br></br>*/}

        <br></br>

                <div className="form-group">

                <Link to="/newloc">
                    <button className="btn btn-primary">Yeni Konum Ekle</button>
                </Link>
                </div><br></br>


                <div className={'form-group' }>
                    <label htmlFor="location">Mevcut Konumlar</label>
                </div>


                
                
                 
            </div>
            
        );
    }
    
}



export default UserPage;