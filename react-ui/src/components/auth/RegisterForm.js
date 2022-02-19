import classes from '../content/fieldofstudy/FieldOfStudyForm.module.css'
import DeleteIcon from '@material-ui/icons/Delete';
import {Button, Grid, MenuItem, Select, TextField} from "@material-ui/core";
import {useState} from "react";
import CardComponent from "../CardComponent";
import instance from "../../axios/axios";
import {useHistory} from "react-router-dom";

const EMPTY_NEW_USER = {
    'username': '',
    'password': '',
    'role': '',

}

const RegisterForm = () => {
    let history = useHistory();
    // Tworząc formularz nadajemy mu stan pustego obiektu
    const [registeredUser, setRegisteredUser] = useState({...EMPTY_NEW_USER});

    const handleChangeForm = name => event => {
        setRegisteredUser({ ...registeredUser, [name]: event.target.value });
    };

    const handleClearForm = () => {
        setRegisteredUser({...EMPTY_NEW_USER})
    }

    const handleSubmit = () => {
        // wysłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(registeredUser))

        instance.post('/user/register', registeredUser)
            .then((data)=>{
                console.log("Odpowiedź sukces: "+ JSON.stringify(data));

                history.push("/auth");
            })
            .catch((err) => {
                console.log("Odpowiedź failed: "+ JSON.stringify(err));
            })
    }

    return (
        <div>
            <CardComponent title={'Register Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={registeredUser.username}
                                   onChange={handleChangeForm("username")}
                                   className={classes.FormStretchField}
                                   label={'Username'} size={'small'} variant="filled"/>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField value={registeredUser.password}
                                   onChange={handleChangeForm("password")}
                                   className={classes.FormStretchField}
                                   type={"password"}
                                   label={'Password'} size={'small'} variant="filled"/>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField value={registeredUser.role}
                                   onChange={handleChangeForm("role")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Type of user' size={'small'} variant="filled">
                            <MenuItem value={"ROLE_ADMIN"}>Administrator</MenuItem>
                            <MenuItem value={"ROLE_LECTURER"}>Lecturer</MenuItem>
                            <MenuItem value={"ROLE_USER"}>Basic User</MenuItem>

                        </TextField>
                    </Grid>
                    <Grid item xs={1}/>
                    <Grid container item xs={10}>
                        <Grid item xs={6}>
                            <Button className={classes.FormStretchField}
                                    size={'small'} variant="contained"
                                    startIcon={<DeleteIcon/>}
                                    onClick={handleClearForm}>
                                Reset
                            </Button>
                        </Grid>
                        <Grid item xs={6}>
                            <Button className={classes.FormStretchField}
                                    size={'small'} variant="contained"
                                    onClick={handleSubmit}>
                                Submit
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </CardComponent>
        </div>
    )
}

export default RegisterForm;