import axios from "axios";


const USERS_REST_API_URL = 'http://localhost:8001/userControl';

class UserService {


    //ADDS NEW USER TO DATABASE ,returns success
    addNewUser(name, email, password) {
        return axios.post(USERS_REST_API_URL + '/addUser', { name: name, email: email, password: password });
    }



    //CHECKS IF USER EXISTS AND IF PASSWORD HE GAVE IS CORRECT,returns success
    checkUser(email, password) {
        return axios.post(USERS_REST_API_URL + '/login', { name: 'undefined', email: email, password: password });
    }


}


export default new UserService();