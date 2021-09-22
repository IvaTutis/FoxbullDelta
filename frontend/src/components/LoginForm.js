import React, { useState } from 'react';
//import { useHistory } from 'react-router-dom';

function LoginForm({ Login }) {
    const [details, setDetails] = useState({ name: "", email: "", password: "" });




    const submitHandler = e => {
        e.preventDefault();

        Login(details);



    };
    return (
        <div className="container">
            <br />
            <form className="card loginform" onSubmit={submitHandler}>
                <div className="form-inner" >
                    <h2>Login</h2>
                    {/**ERROR! */}
                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input className="form-control" style={{ width: "200px" }} type="text" name="name" id="name" onChange={e => setDetails({ ...details, name: e.target.value })} value={details.name} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input className="form-control" style={{ width: "200px" }} type="email" name="email" id="email" onChange={e => setDetails({ ...details, email: e.target.value })} value={details.email} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input className="form-control " style={{ width: "200px" }} type="password" name="password" id="password" onChange={e => setDetails({ ...details, password: e.target.value })} value={details.password} />
                    </div>
                    <br></br>
                    <input className="btn btn-primary" type="submit" value="LOGIN"></input>
                </div>
                <br />
            </form>

        </div>
    )
}

export default LoginForm
