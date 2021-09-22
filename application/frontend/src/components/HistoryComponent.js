import React, { Component } from 'react'
import LexerService from '../services/LexerService'

class HistoryComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {


            povijest: []

        }

    }



    componentDidMount() {
        LexerService.sendHistory().then((res) => {

            this.setState({ povijest: res.data });
            console.log("Recived: ");
            console.log(res.data);

        });


    }


    render() {
        return (
            <div >
                <h2 className="text-center">History</h2>
                <br></br>
                <div className="card">
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Input</th>
                                <th scope="col">Output</th>
                                <th scope="col">Input Type</th>
                            </tr>
                        </thead>
                        <tbody>

                            {
                                this.state.povijest.map(
                                    izraz =>
                                        <tr key={izraz.id}>
                                            <th scope="row">{izraz.id}</th>
                                            <td>{izraz.input}</td>
                                            <td>{izraz.output}</td>
                                            <td>{izraz.inputType}</td>
                                        </tr>
                                )
                            }

                        </tbody>
                    </table>
                </div>


            </div>

        )
    }
}

export default HistoryComponent