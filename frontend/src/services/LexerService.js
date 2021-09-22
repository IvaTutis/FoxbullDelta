import axios from 'axios';

const LEXER_API_BASE_URL = "http://localhost:8001/expressionControl";


class LexerService {


    sendMatrixString(string) {
        return axios.post(LEXER_API_BASE_URL + '/matrix', { value: string });
    }
    sendAlgebraString(string) {
        return axios.post(LEXER_API_BASE_URL + '/arithmetic', { value: string });
    }
    sendLogicString(string, numberOf) {
        return axios.post(LEXER_API_BASE_URL + '/logic', { input: string, number_of_variables: numberOf });
    }
    sendHistory() {
        return axios.get(LEXER_API_BASE_URL + '/get');
    }
}

export default new LexerService()