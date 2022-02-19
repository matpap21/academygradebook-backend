import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Redirect} from 'react-router-dom';

import Spinner from './Spinner';
import {updateObject} from '../../shared/utility';

import * as actions from '../../store/actions/index';
import classes from './Auth.module.css'

class Auth extends Component {
    state = {
        controls: {
            username: {
                value: ''
            },
            password: {
                value: ''
            }
        }
    };

    inputChangedHandler = (event, controlName) => {
        const updatedControls = updateObject(this.state.controls, {
            [controlName]: updateObject(this.state.controls[controlName], {
                value: event.target.value
            })
        });
        this.setState({controls: updatedControls});
    };

    submitHandler = (event) => {
        event.preventDefault();
        this.props.onAuth(this.state.controls.username.value, this.state.controls.password.value);
    };

    render() {
        let form = (
            <React.Fragment>
                <h3>Sign In</h3>
                <div className="form-group">
                    <label>Username</label>
                    <input type="text"
                           className="form-control"
                           placeholder="Enter username"
                           value={this.state.controls.username.value}
                           onChange={(event) => this.inputChangedHandler(event, "username")}/>
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input type="password"
                           className="form-control"
                           placeholder="Enter password"
                           value={this.state.controls.password.value}
                           onChange={(event) => this.inputChangedHandler(event, "password")}/>
                </div>

                <button type="submit" className="btn btn-info btn-block">Submit</button>
            </React.Fragment>
        );

        if (this.props.loading) {
            form = <Spinner/>
        }

        let errorMessage = null;
        if (this.props.error) {
            errorMessage = <p>{this.props.error.message}</p>
        }

        let authRedirect = null;
        if (this.props.isAuthenticated) {
            authRedirect = <Redirect to={this.props.authRedirectPath}/>
        }

        return (
            <div className={classes.authWrapper}>
                <div className={classes.authInner}>
                    {authRedirect}
                    {errorMessage}
                    <form onSubmit={this.submitHandler}>
                        {form}
                    </form>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        loading: state.auth.loading,
        error: state.auth.error,
        isAuthenticated: state.auth.token !== null,
        authRedirectPath: state.auth.authRedirectPath
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAuth: (username, password) => dispatch(actions.auth(username, password)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Auth);
