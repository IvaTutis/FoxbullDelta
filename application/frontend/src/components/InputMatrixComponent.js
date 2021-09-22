import React, { Component } from 'react'
import LexerService from '../services/LexerService';

class InputMatrixComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            firstName: '',
            result: '',
            selection: null,
            operator: ' + ',

            matrixSizeAx: 0,
            matrixSizeAy: 0,
            matrixSizeBx: 0,
            matrixSizeBy: 0,


        }

        this.changeMatrix1 = this.changeMatrix1.bind(this);
        this.changeMatrix2 = this.changeMatrix2.bind(this);

        this.matrixSizeChangeAx = this.matrixSizeChangeAx.bind(this);
        this.matrixSizeChangeAy = this.matrixSizeChangeAy.bind(this);
        this.matrixSizeChangeBx = this.matrixSizeChangeBx.bind(this);
        this.matrixSizeChangeBy = this.matrixSizeChangeBy.bind(this);

        this.operator = this.operator.bind(this);



    }
    operator = (e) => {
        this.setState({ operator: e.target.value });
    }

    drawResultMatrix() {
        if (this.state.result === '') return;



        let re = new RegExp('\d x \d matrix');

        var a = parseInt(this.state.result.toString().replace(/(^\d) x (\d) matrix(.+$)/i, '$1'));
        var b = parseInt(this.state.result.toString().replace(/(^\d) x (\d) matrix(.+$)/i, '$2'));
        var brojevi = this.state.result.toString().replace(/(^\d) x (\d) matrix(.+$)/i, '$3')
        console.log(brojevi)
        //var v = new Array(novaA);

        var novi = [];


        for (var i = 0; i < a; ++i) {
            novi.push(<tr>
                {this.redak(i, b, brojevi)}
            </tr>);
        }
        return novi


    }

    redak(ind, b, brojevi) {
        var stupci = [];

        var string = brojevi;
        string = string.split(" ");
        var stringArray = new Array();
        for (var i = 0; i < string.length; i++) {
            stringArray.push(string[i]);
            if (i != string.length - 1) {
                //stringArray.push(" ");
            }
        }
        console.log(stringArray)

        var n = ind * b;

        for (var i = 0; i < b; i++) {
            stupci.push(
                <td>{stringArray[i + b * ind]}</td>
            )
        }

        return stupci;


    }

    changeMatrix1(a, b) { //init matrix to new dimensions
        this.setState({ matrix1: null });

        var novix;
        var noviy;
        if (a == null) {
            noviy = b;
            novix = this.state.matrixSizeAx;

        }
        else {
            noviy = this.state.matrixSizeAy;
            novix = a;
        }


        var temp = null;

        console.log("init matrix 1 s novim dim: " + novix + "," + noviy)

        temp = new Array(novix);
        for (var i = 0; i < novix; i++) {
            temp[i] = new Array(noviy);
            for (let j = 0; j < noviy; ++j) temp[i][j] = 0;
        }
        this.setState({ matrix1: temp });

    }
    changeMatrix2(a, b) {//init matrix to new dimensions

        var novix;
        var noviy;
        if (a == null) {
            noviy = b;
            novix = this.state.matrixSizeBx;
        }
        else {
            noviy = this.state.matrixSizeBy;
            novix = a;
        }

        this.setState({ matrix2: null });
        var temp = null;
        temp = new Array(novix);
        for (var i = 0; i < novix; i++) {
            temp[i] = new Array(noviy);
            for (let j = 0; j < noviy; ++j) temp[i][j] = 0;
        }
        this.setState({ matrix2: temp });

    }

    matrixSizeChangeAx = (event) => {//triggers on select-element 

        this.setState({ matrixSizeAx: event.target.value });
        console.log("mijenjam Ax u " + event.target.value)

        //change matrix
        this.changeMatrix1(event.target.value, null);


    }
    matrixSizeChangeAy = (event) => {//triggers on select-element 

        this.setState({ matrixSizeAy: event.target.value });
        console.log("mijenjam Ay u " + event.target.value)
        //change matrix
        this.changeMatrix1(null, event.target.value);
    }
    matrixSizeChangeBx = (event) => {//triggers on select-element 

        console.log("change Bx")
        this.setState({ matrixSizeBx: event.target.value });

        this.changeMatrix2(event.target.value, null);
    }
    matrixSizeChangeBy = (event) => {//triggers on select-element 

        this.setState({ matrixSizeBy: event.target.value });
        //change matrix
        this.changeMatrix2(null, event.target.value);
    }

    matrix1change = (e) => {//on change of one element

        //console.log(e.target.value);
        var x = parseInt(e.target.getAttribute("stupac"))
        var y = parseInt(e.target.getAttribute("redak"))

        var newArray = this.state.matrix1.map(function (arr) {
            return arr.slice();
        });

        newArray[y][x] = parseInt(e.target.value);
        this.setState({ matrix1: newArray });

    }
    matrix2change = (e) => { //on change of one element

        //console.log(e.target.value);
        var x = parseInt(e.target.getAttribute("stupac"))
        var y = parseInt(e.target.getAttribute("redak"))

        var newArray = this.state.matrix2.map(function (arr) {
            return arr.slice();
        });

        newArray[y][x] = parseInt(e.target.value);
        this.setState({ matrix2: newArray });

    }




    componentDidMount() {


    }



    save = (e) => {
        e.preventDefault();

        //DO SADA UPISAN TEXT
        console.log('expression => ' + JSON.stringify(this.state.firstName));



        LexerService.lexString(this.state.firstName).then((res) => {

            console.log(res.data);
            this.setState({ result: res.data });
        });

    }

    cancel() {
        this.props.history.push('/');
    }
    hist() {
        this.props.history.push('/history');
    }

    getTitle() {

        return <h3 className="text-center">Matrix solver</h3>

    }

    stringify = (e) => {
        console.log(this.state.matrixSizeAx + "," + this.state.matrixSizeAy + ", " +
            this.state.matrixSizeBx + "," +
            this.state.matrixSizeBy)

        console.log("stringify");
        //console.log(this.state.matrix1)
        var str = JSON.stringify(this.state.matrix1)
        var str2 = JSON.stringify(this.state.matrix2)

        str = '{' + str.substring(1, str.length - 1) + '}';
        str2 = '{' + str2.substring(1, str2.length - 1) + '}';
        console.log(str)

        console.log("Stringified - sending to server : " + str + this.state.operator + str2)
        LexerService.sendMatrixString(str + this.state.operator + str2).then((res) => {


            this.setState({ result: str + this.state.operator + str2 });
            console.log("Recived: " + res.data);
            this.setState({ result: res.data });
        });






    }


    nacrtajMatricu = (indMatrice) => { // crta x*y inputa 


        if (indMatrice === 0) {
            //console.log("ind matr ==0")
            //console.log(this.state.matrix1)
            if (this.state.matrix1 == null) return;

        }
        else {
            //console.log("ind matr ==1")
            if (this.state.matrix2 == null) return;
        }



        var dim_red = (indMatrice === 0) ? this.state.matrixSizeAx : this.state.matrixSizeBx;
        var dim_st = (indMatrice === 0) ? this.state.matrixSizeAy : this.state.matrixSizeBy;


        console.log("crtam " + (indMatrice ? "drugu" : "prvu") + " matricu");
        //console.log(this.state.matrixSizeAx + "," + this.state.matrixSizeAy + ", " +
        //    this.state.matrixSizeBx + "," +
        //    this.state.matrixSizeBy);


        let a = new Array(dim_red);
        for (let i = 0; i < dim_red; ++i) a[i] = 0;

        let b = new Array(dim_st);
        for (let i = 0; i < dim_st; ++i) b[i] = 0;
        if (!this.state.matrix1 && indMatrice === 0) return
        if (!this.state.matrix2 && indMatrice === 1) return
        return indMatrice === 0 ? (<div className="matrix" style={{ width: "100px" }}>

            {a.map((value, x) => {

                return (
                    b.map((value2, y) => {
                        //console.log("crtam jedan input")

                        return <input
                            style={{ width: (100 / this.state.matrixSizeAy) + "px" }}
                            key={y + "," + x}
                            stupac={y}
                            redak={x}
                            onChange={this.matrix1change}
                            value={this.state.matrix1[x][y]} />

                    })

                )
            })}
        </div>) : (

            <div className="matrix" style={{ width: "100px" }}>

                {a.map((value, x) => {
                    return (
                        b.map((value2, y) => {
                            return <input
                                style={{ width: (100 / this.state.matrixSizeBy) + "px" }}
                                key={y}
                                stupac={y}
                                redak={x}
                                onChange={this.matrix2change}
                                value={this.state.matrix2[x][y]} />

                        })

                    )
                })}
            </div>)
    }


    render() {




        return (
            <div>
                <br></br>
                <div className="card">
                    <br />

                    <div className="d-flex justify-content-around">
                        <div>

                            <select onChange={this.matrixSizeChangeAx} value={this.state.matrixSizeAx} className="form-control" id="exampleFormControlSelect1">
                                <option value={0}>0</option>
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                            </select>

                        </div>
                        <div>
                            Matrix A dimension
                        </div>
                        <div>

                            <select onChange={this.matrixSizeChangeAy} value={this.state.matrixSizeAy} className="form-control" id="exampleFormControlSelect1">
                                <option value={0}>0</option>
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                            </select>

                        </div>
                        <div>
                        </div>

                        <div>
                        </div>
                        <div>
                            <select onChange={this.matrixSizeChangeBx} value={this.state.matrixSizeBx} className="form-control" id="exampleFormControlSelect1">
                                <option value={0}>0</option>
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                            </select>
                        </div>
                        <div>
                            Matrix B dimension
                        </div>
                        <div>

                            <select onChange={this.matrixSizeChangeBy} value={this.state.matrixSizeBy} className="form-control" id="exampleFormControlSelect1">
                                <option value={0}>0</option>
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                            </select>

                        </div>

                    </div>
                    <br />

                </div>
                <br></br>

                <div className="card">
                    <br />
                    <div className="d-flex justify-content-around">
                        <div>A</div>
                        <div></div>
                        <div>B</div>
                    </div>
                    <div className="d-flex justify-content-around">
                        <div  >

                            {this.nacrtajMatricu(0)}
                        </div>
                        <div>
                            <div className="form-check">
                                <input value=" * " onChange={this.operator} className="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" />
                                <label className="form-check-label" htmlFor="flexRadioDefault1">
                                    AxB
                                </label>
                            </div>
                            <div className="form-check">
                                <input value=" - " onChange={this.operator} className="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" />
                                <label className="form-check-label" htmlFor="flexRadioDefault2">
                                    A-B
                                </label>
                            </div>
                            <div className="form-check">
                                <input value=" + " onChange={this.operator} className="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault3" />
                                <label className="form-check-label" htmlFor="flexRadioDefault3">
                                    A+B
                                </label>
                            </div>
                            <button className="btn btn-success" onClick={this.stringify}>Solve</button>

                        </div>

                        <div>
                            {this.nacrtajMatricu(1)}
                        </div>


                    </div>
                    <br />

                </div>
                <br />
                <button className="btn btn-outline-warning" onClick={this.hist.bind(this)} >See history</button>&nbsp;
                <button className="btn btn-outline-danger" onClick={this.cancel.bind(this)} >Go back</button>

                <br /><br />
                <h2>Result</h2>
                <input readOnly placeholder="result" name="firstName" className="form-control"
                    value={this.state.result} />
                <br />
                <div className="card" style={{ width: "100%" }}>

                    <table className="table" >

                        <thead>

                        </thead>
                        <tbody>
                            {this.drawResultMatrix()}
                        </tbody>
                    </table>

                </div>
                <br />

            </div>

        )
    }
}

export default InputMatrixComponent

