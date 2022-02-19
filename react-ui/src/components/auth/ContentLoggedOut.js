import classes from "../../App.css";
import {Route, Switch} from "react-router-dom";
import AppContentHome from "./../content/home/AppContentHome";
import React from "react";
import Auth from "./Auth";
import AppHeaderLoggedOut from "../header/AppHeaderLoggedOut";
import RegisterForm from "./RegisterForm";

const ContentLoggedOut = () => {
    return (
        <>
            <AppHeaderLoggedOut/>
            <div className={classes.AppContent}>
                <Switch>
                    <Route path={'/auth'}>
                        <Auth/>
                    </Route>
                    <Route path={'/register'}>
                        <RegisterForm/>
                    </Route>
                    <Route path={'/'}>
                        <AppContentHome/>
                    </Route>
                </Switch>
            </div>
        </>
    )
}

export default ContentLoggedOut;