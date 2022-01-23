import * as actionTypes from '../actions/actionTypes';

const updateObject = (oldObject, updatedProperties) => {
    return {
        ...oldObject,
        ...updatedProperties
    }
}

const initialState = {
    token: null,
    username: null,
    id: null,
    admin:null,
    lecturer:null,
    student:null,
    error: null,
    loading: false,
    authRedirectPath: '/auth'
}

const authStart = (state) => {
    return updateObject(state, {error: null, loading: true})
}

const parseJwt = (token) => {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};

const authSuccess = (state, action) => {
    console.log(action);

    return updateObject(state, {
        token: action.token,
        username: action.username,
        id: action.id,
        admin:action.admin,
        lecturer:action.lecturer,
        student:action.student,
        error: null,
        loading: false
    });
}

const authFail = (state, action) => {
    return updateObject(state, {
        error: action.error,
        loading: false
    });
}

const authLogout = (state) => {
    return updateObject(state, {token: null, username: null});
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.AUTH_START:
            return authStart(state, action);
        case actionTypes.AUTH_SUCCESS:
            return authSuccess(state, action);
        case actionTypes.AUTH_FAIL:
            return authFail(state, action);
        case actionTypes.AUTH_LOGOUT:
            return authLogout(state, action);
        default:
            return state;
    }
}

export default reducer;
