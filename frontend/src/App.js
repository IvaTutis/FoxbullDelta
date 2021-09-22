
import { useState } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

//import UserComponent from './components/UserComponent';
import LoginForm from './components/LoginForm';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import HistoryComponent from './components/HistoryComponent';
import MainComponent from './components/MainComponent';
import InputAritmComponent from './components/InputAritmComponent';
import InputLogicComponent from './components/InputLogicComponent';
import InputMatrixComponent from './components/InputMatrixComponent';
import UserService from './services/UserService';

function App() {


  async function Login(details) {



    UserService.checkUser(details.email, details.password).then((res) => {
      let isLoginSuccess = res.data;
      console.log(isLoginSuccess ? "Login uspjesan" : "Login neuspjesan");


      if (isLoginSuccess) {
        localStorage.setItem("user-info", JSON.stringify(details));
        window.location.reload();
      }


    });
  }
  const Logout = () => {
    console.log(JSON.parse(localStorage.getItem("user-info")));
    //setUser({ name: "", email: "" });
    localStorage.removeItem('user-info');
    window.location.reload();

  }

  return (

    <div className="App" >
      {(JSON.parse(localStorage.getItem("user-info")) != null) ? (
        <div>
          <Router>
            <HeaderComponent Logout={Logout} />
            <div className="container">
              <Switch>
                <Route path="/" exact component={MainComponent}></Route>
                <Route path="/input-aritm/" exact component={InputAritmComponent}></Route>
                <Route path="/input-matrix/" exact component={InputMatrixComponent}></Route>
                <Route path="/input-log/" exact component={InputLogicComponent}></Route>
                <Route path="/history" exact component={HistoryComponent}></Route>
                {/* <Route path = "/update-employee/:id" component = {UpdateEmployeeComponent}></Route> */}
              </Switch>
            </div>
            <FooterComponent />
          </Router>
        </div>
      ) : (
        <LoginForm Login={Login} />
      )}
    </div>



  );
}

export default App;


/**
 * <Route path="/" exact component={ListEmployeeComponent}></Route>
 *             <Route path="/employees" component={ListEmployeeComponent}></Route>
 *             <Route path="/add-employee/:id" component={CreateEmployeeComponent}></Route>
 *             <Route path="/view-employee/:id" component={ViewEmployeeComponent}></Route>
 *
 *
 *
*
*
*/