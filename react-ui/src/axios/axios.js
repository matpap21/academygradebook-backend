import axios from 'axios';

const instance = axios.create({
    baseURL: "http://localhost:8080"
});

instance.interceptors.request.use(req => {
    console.log('req: ' + req)
    const token = localStorage.getItem('token');
    if (token) {
        req.headers = {'Authorization': token};
    }
    return req;
});

export default instance;
