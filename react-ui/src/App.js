import classes from './App.css';
import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import * as actions from './store/actions/index'
import ContentLoggedIn from "./components/auth/ContentLoggedIn";
import ContentLoggedOut from "./components/auth/ContentLoggedOut";

class App extends Component {
    componentDidMount() {
        this.props.checkAuthState();
    }

    render() {
        return (
            <div className={classes.App}>
                {this.props.isAuthenticated ? (<ContentLoggedIn/>) : (<ContentLoggedOut/>)}
            </div>
        );
    }
}

const mapStateToProps = state => {
        return {
            isAuthenticated: state.auth.token !== null
        };
    }
;

const mapDispatchToProps = dispatch => {
        return {
            checkAuthState: () => dispatch(actions.checkAuthState())
        }
    }
;

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
