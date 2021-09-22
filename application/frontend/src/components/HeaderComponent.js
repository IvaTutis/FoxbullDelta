import React, { Component } from 'react'

import { Link } from 'react-router-dom';



class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }

        this.logoutHandler = e => {
            e.preventDefault();
            props.Logout();
        };

    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">


                        <div className="container-fluid">
                            <div className="navbar-header">
                                <Link className="navbar-brand nav-item nav-link active" to="/">SOLVER APP</Link>

                            </div>

                            <ul className="nav navbar-nav">
                                <Link className="nav-item nav-link " to="/input-aritm">Arithmetic</Link>
                                <Link className="nav-item nav-link " to="/input-matrix">Matrix</Link>
                                <Link className="nav-item nav-link " to="/input-log">Logic</Link>
                            </ul>
                            <ul className="nav navbar-nav navbar-right">

                                <Link className="nav-item nav-link " to="/history">History</Link>
                                <button className="btn btn-outline-primary nav-item nav-link " onClick={this.logoutHandler}><span className="glyphicon glyphicon-user"></span> Logout </button>


                            </ul>
                        </div>
                    </nav>
                </header>
            </div>
        )




    }
}

export default HeaderComponent





