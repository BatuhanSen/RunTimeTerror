import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';



class NewLocation extends React.Component {


    
    

    render() {
        const { user, users } = this.props;

        
        return (

            <div className="col-md-6 col-md-offset-3">
                
                
                <div className="form-group">
                    <label htmlFor="location">Konum adı</label>
                    <input type="location" className="form-control" name="location" /> <br></br>          
                    <button className="btn btn-primary">Yeni Konum oluştur</button>
                </div>
                 
            </div>
            
        );
    }
    
}



export default NewLocation;