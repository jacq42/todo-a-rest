import React, { Component } from 'react'
import TodoListService from '../services/TodoListService'

class ListTodoComponent extends Component {
	constructor(props) {
		super(props)

		this.state = {
			todos: []
		}
		this.deleteTodo = this.deleteTodo.bind(this);
	}

	deleteTodo(id) {
		TodoListService.deleteTodo(id).then(res => {
			this.setState({ todos: this.state.todos.filter(todo => todo.id !== id) });
		});
	}

	componentDidMount() {
		TodoListService.getTodos().then((res) => {
			this.setState({ todos: res.data });
		});
	}

	render() {
		return (
			<div>
				<h2 className="text-center">TODO List</h2>
				<div className="row">
					<table className="table table-striped table-bordered">

						<thead>
							<tr>
								<th> ID</th>
								<th> Created at</th>
								<th> Description</th>
							</tr>
						</thead>
						<tbody>
							{
								this.state.todos.map(
									todo =>
										<tr key={todo.id}>
											<td> {todo.id} </td>
											<td> {todo.createdAt} </td>
											<td> {todo.description} </td>
											<td>
												<button style={{ marginLeft: "10px" }} onClick={() => this.deleteTodo(todo.id)} className="btn btn-danger">Delete </button>
											</td>
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

export default ListTodoComponent

