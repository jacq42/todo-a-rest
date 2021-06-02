import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListTodoComponent from './components/ListTodoComponent';

function App() {
  return (
    <div>
        <Router>
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListTodoComponent}></Route>
                    </Switch>
                </div>
        </Router>
    </div>
    
  );
}

export default App;


