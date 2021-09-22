import React, { Component } from 'react'
import LexerService from '../services/LexerService';

class InputLogicComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            input: '',
            result: '',
            subsctiptChars: "₀₁₂₃₄₅₆₇₈₉",
            cols: 0,
            disabled: [false, true, true, true],
            numberOf: [0, 0, 0, 0],
            brojRezRedaka: 0,
            rezTablica: null
        }
        this.changeExprHandler = this.changeExprHandler.bind(this);
        this.insertP = this.insertP.bind(this);
        this.insertOp = this.insertOp.bind(this);
        this.insertCols = this.insertCols.bind(this);
        this.kreirajTablicu = this.kreirajTablicu.bind(this);
        this.myResultRef = React.createRef();
    }

    componentDidMount() {


    }

    updateNumberOf(index, item) {
        this.setState({
            numberOf: [
                ...this.state.numberOf.slice(0, index),
                item,
                ...this.state.numberOf.slice(index + 1)
            ]
        });
    }


    //ON CLICK  -- SOLVE BUTTON
    solve = (e) => {
        e.preventDefault();


        if (!this.state.input) {
            alert("You didn't input anything.");
            return
        }

        if (this.state.cols !== 0) {
            alert("You misplaced colon somewhere.");
            return
        }

        //DO SADA UPISAN TEXT
        console.log('expression => ' + JSON.stringify(this.state.input));

        var stari = this.state.input;
        var novi = '';
        for (var i = 0; i < stari.length; i++) {
            if (stari[i] >= '₀' && stari[i] <= '₉') {
                novi = novi + (stari[i].charCodeAt(0) - '₀'.charCodeAt(0));
            }
            else if (stari[i] === '⇒') {
                novi = novi + '->';

            }
            else if (stari[i] === '⇔') {
                novi = novi + '<->';
            }
            else {
                novi = novi + stari[i];
            }
        }
        var koliko = 0;
        koliko = koliko + (this.state.numberOf[0] > 0 ? 1 : 0);
        koliko = koliko + (this.state.numberOf[1] > 0 ? 1 : 0);
        koliko = koliko + (this.state.numberOf[2] > 0 ? 1 : 0);
        koliko = koliko + (this.state.numberOf[3] > 0 ? 1 : 0);
        //console.log(this.state.numberOf[2]);
        //console.log(koliko);


        LexerService.sendLogicString(novi, koliko).then((res) => {


            //var test = ["test 123", "test 123", "test 123", "test 123"];
            console.log("DOBIO SA SERVERA::::::::->")
            console.log(res.data)

            this.kreirajTablicu(res.data);

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



    insertP = (broj) => {

        var tren = this.state.input;

        this.setState({ input: tren + "P" + this.state.subsctiptChars[broj] });
        ///
        this.updateNumberOf(broj, this.state.numberOf[broj] + 1);

        /*this.state.numberOf.forEach((element, index) => {
            if (element > 0) this.updateDisabled(index, true);
            else this.updateDisabled(index, false);
        });*/

        //console.log(this.state.disabled)
        //console.log(this.state.numberOf)


    }



    insertCols = (left) => {
        var tren = this.state.input;
        var cols = this.state.cols;
        console.log(cols)
        if (left) {
            this.setState({ input: tren + "(" });
            this.setState({ cols: cols + 1 });
        }
        else {
            this.setState({ input: tren + ")" });
            this.setState({ cols: cols - 1 });
        }


    }
    insertOp = (op) => {
        var tren = this.state.input;

        this.setState({ input: tren + op });

    }

    del = (event) => {

        this.setState({ input: '' });

        this.setState({ numberOf: [0, 0, 0, 0] });

        this.setState({ cols: 0 });

    }

    back = (event) => {

        var tren = this.state.input;

        var zadnjiChar = tren.substring(tren.length - 1, tren.length);

        if (zadnjiChar >= "₀" && zadnjiChar <= "₉") {
            this.setState({ input: tren.substring(0, tren.length - 2) });

            var index = (zadnjiChar.charCodeAt(0) - '₀'.charCodeAt(0));
            this.updateNumberOf(index, this.state.numberOf[index] - 1);

        }
        else
            this.setState({ input: tren.substring(0, tren.length - 1) });
    }

    getTitle() {

        return <h3 className="text-center">Logic solver</h3>
    }


    kreirajTablicu(data) { //JEDOM KAD DOBIJE INFO S SERVERA ---> POPUNJAVA brojRezRedaka i rezTablica

        this.setState({
            brojRezRedaka: data.length
        });
        this.setState({
            rezTablica: data
        });

    }



    nacrtajTablicu = () => {



        //event.preventDefault();
        console.log("divs changed")


        if (this.state.brojRezRedaka <= 0 || !this.state.rezTablica) return;

        return (
            <div>
                <h3>Result</h3>
                <table className="table">
                    <thead>
                        <th scope="col">#</th>
                        <th scope="col">Variables</th>
                        <th scope="col">Output</th>
                    </thead>
                    <tbody>



                        {this.state.rezTablica.map((value, ind) => {
                            return <tr key={ind + " st"}>
                                <th scope="row">{ind}</th>
                                <td>{value.substr(0, value.indexOf('izlaz') - 1)}</td>
                                <td>{value.substr(value.indexOf('izlaz'))}</td>

                            </tr>

                        })}

                    </tbody>


                </table>
            </div>
        );



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
                                    <div className="btn-group mr-2" role="group" aria-label="First group">
                                        <button type="button" onClick={(param) => this.insertOp("&")} className="btn btn-secondary">&</button>
                                        <button type="button" onClick={(param) => this.insertOp("!")} className="btn btn-secondary">!</button>
                                        <button type="button" onClick={(param) => this.insertOp("|")} className="btn btn-secondary">|</button>
                                        <button type="button" onClick={(param) => this.insertOp("⇒")} className="btn btn-secondary">⇒</button>
                                        <button type="button" onClick={(param) => this.insertOp("⇔")} className="btn btn-secondary">⇔</button>
                                    </div>&nbsp;
                                    <div className="btn-group mr-2" role="group" aria-label="First group">
                                        <button type="button" onClick={(param) => this.insertCols(1)} className="btn btn-secondary">(</button>
                                        <button type="button" onClick={(param) => this.insertCols(0)} className="btn btn-secondary">)</button>
                                    </div>
                                    <br />
                                    <br />
                                    <div className="btn-group mr-2" role="group" aria-label="First group">
                                        <button type="button" onClick={(param) => this.insertP(0)} className="btn btn-outline-info">P<sub>0</sub></button>
                                        <button type="button" onClick={(param) => this.insertP(1)} className="btn btn-outline-info">P<sub>1</sub></button>
                                        <button type="button" onClick={(param) => this.insertP(2)} className="btn btn-outline-info">P<sub>2</sub></button>
                                        <button type="button" onClick={(param) => this.insertP(3)} className="btn btn-outline-info">P<sub>3</sub></button>
                                    </div>&nbsp;
                                    <div className="btn-group mr-2" role="group" aria-label="First group">
                                        <button type="button" onClick={this.back} className="btn btn-warning">←</button>
                                        <button type="button" onClick={this.del} className="btn btn-danger">del</button>
                                    </div>
                                    <br />


                                    <br />
                                    <div className="form-group">

                                        <input disabled placeholder="input some expression" name="firstName" className="form-control"
                                            value={this.state.input} onChange={this.changeExprHandler} />



                                    </div>

                                    <br></br>
                                    <button style={buttonStyle} className="btn btn-success" onClick={this.solve}>Solve</button>
                                    <button style={buttonStyle} className="btn btn-outline-warning" onClick={this.hist.bind(this)} >See history</button>
                                    <button className="btn btn-outline-danger" onClick={this.cancel.bind(this)} >Go back</button>
                                    <br /><br />
                                    <div >

                                        {this.nacrtajTablicu()}

                                    </div>
                                </form>
                            </div>

                        </div>


                    </div>

                </div>
            </div>
        )
    }
}

export default InputLogicComponent