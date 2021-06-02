import axios from 'axios';

const TODOLIST_API_BASE_URL = "http://localhost:8080/api/v1/todos";

class TodoListService {

    getTodos(){
        return axios.get(TODOLIST_API_BASE_URL);
    }

    deleteTodo(todoId){
        return axios.delete(TODOLIST_API_BASE_URL + '/' + todoId);
    }
}

export default new TodoListService()