import React, { Component } from 'react'
//import EmployeeService from '../services/EmployeeService'

class MainComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            employees: []


        }
        this.addEmployee = this.addEmployee.bind(this);
        this.input_aritm = this.input_aritm.bind(this);
        this.input_matrix = this.input_matrix.bind(this);
        this.input_log = this.input_log.bind(this);
        this.editEmployee = this.editEmployee.bind(this);
        this.deleteEmployee = this.deleteEmployee.bind(this);
    }

    deleteEmployee(id) {

        /*EmployeeService.deleteEmployee(id).then(res => {
            this.setState({ employees: this.state.employees.filter(employee => employee.id !== id) });
        });*/
    }
    viewEmployee(id) {
        this.props.history.push(`/view-employee/${id}`);
    }
    editEmployee(id) {
        this.props.history.push(`/add-employee/${id}`);
    }

    lexString() {
        this.props.history.push(`/lexer`);
    }

    componentDidMount() {
        /*EmployeeService.getEmployees().then((res) => {
            this.setState({ employees: res.data });
        });*/

    }

    addEmployee() {
        this.props.history.push('/add-employee/_add');
    }

    input_aritm() {
        this.props.history.push('input-aritm');
    }
    input_matrix() {
        this.props.history.push('input-matrix');
    }
    input_log() {
        this.props.history.push('input-log');
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Main page</h2>

                <button className="btn btn-primary" onClick={this.input_aritm}> Aritm input</button>
                <br /> <br />
                <button className="btn btn-primary" onClick={this.input_matrix}>Matrix input</button>
                <br /> <br />
                <button className="btn btn-primary" onClick={this.input_log}>Logic input</button>
                <br /> <br />

                <br></br>
                <div className="row">

                    <label>{this.state.test}</label>
                </div>

            </div>
        )
    }
}

export default MainComponent