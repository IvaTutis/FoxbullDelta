import React, { Component } from 'react'

class FooterComponent extends Component {
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
                <footer className="footer">
                </footer>
            </div>
        )
    }
}

export default FooterComponent