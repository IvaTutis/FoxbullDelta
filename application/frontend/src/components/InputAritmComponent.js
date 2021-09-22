import React, { Component } from 'react'
import LexerService from '../services/LexerService';

class InputAritmComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            input: '',
            result: ''
        }
        this.changeExprHandler = this.changeExprHandler.bind(this);

    }

    componentDidMount() {

    }



    //ON CLICK  -- SOLVE BUTTON
    solve = (e) => {
        e.preventDefault();

        if (!this.state.input) {
            alert("You didn't input anything.");
            return
        }

        //DO SADA UPISAN TEXT
        console.log('expression => ' + JSON.stringify(this.state.input));


        LexerService.sendAlgebraString(this.state.input).then((res) => {

            console.log("Recived: " + res.data);
            this.setState({ result: res.data });
        });

    }

    cancel() {
        this.props.history.push('/');
    }
    hist() {
        this.props.history.push('/history');
    }

    changeExprHandler = (event) => {
        this.setState({ input: event.target.value });
    }


    getTitle() {

        return <h3 className="text-center">Arithmetic solver</h3>

    }
    render() {
        const buttonStyle = {
            marginRight: '10px',

        };


        return (
            <div>
                <br></br>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {
                                this.getTitle()
                            }
                            <div className="card-body">
                                <form>
                                    <div className="form-group">

                                        <input placeholder="input some expression" name="firstName" className="form-control"
                                            value={this.state.input} onChange={this.changeExprHandler} />
                                    </div>

                                    <br></br>
                                    <button style={buttonStyle} className="btn btn-success" onClick={this.solve}>Solve</button>
                                    <button style={buttonStyle} className="btn btn-outline-warning" onClick={this.hist.bind(this)} >See history</button>
                                    <button className="btn btn-outline-danger" onClick={this.cancel.bind(this)} >Go back</button>
                                    <br /><br />
                                    <input readOnly placeholder="input some expression" name="firstName" className="form-control"
                                        value={this.state.result} />
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default InputAritmComponent