import classes from './AcademicSubjectsForm.module.css';
import CardComponent from "../../CardComponent";
import DeleteIcon from '@material-ui/icons/Delete';
import {Button, Grid, MenuItem, TextField} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import instance from "../../../axios/axios";

// Model / encja pustej oferty/nowego obiektu
const EMPTY_NEW_ACADEMIC_SUBJECT = {
    'id': null,
    'academicSubject': null,
    'universityLecturerId': null,
}

const AcademicSubjectsForm = () => {
    // Tworząc formularz nadajemy mu stan pustego obiektu
    //  Wartości domyślne formularza kopiowane są z obiektu EMPTY_NEW_TRAINING
    const [editedAcademicSubject, setEditedAcademicSubject] = useState({...EMPTY_NEW_ACADEMIC_SUBJECT});
    const [academicLecturersRecordsOnServer, setAcademicLecturersRecordsOnServer] = useState([]);


const handleChangeForm = name => event => {
    setEditedAcademicSubject({...editedAcademicSubject, [name]: event.target.value});
};

const handleClearForm = () => {
    setEditedAcademicSubject({...EMPTY_NEW_ACADEMIC_SUBJECT})
}
const pullAcademicLecturersRecordsFromDatabaseServer = () => {
    instance.get("/universitylecturers")
        .then((data) => {
            // data ma pole data
            setAcademicLecturersRecordsOnServer(data.data);
        })
        .catch((error) => {
            console.log("Otrzymaliśmy odpowiedź o błędzie!")
        });
}
useEffect(() => {
    pullAcademicLecturersRecordsFromDatabaseServer();
}, [])

const handleSubmit = () => {
    // wysyłanie obiektu na serwer
    console.log("Wysyłamy:" + JSON.stringify(editedAcademicSubject))

    instance.post('/academicsubjects' ,editedAcademicSubject)
        .then((data) => {
            console.log("Odpowiedz sukces: " + JSON.stringify(data));
        })
        .catch((err) => {
            console.log("Odpowiedz porazka: " + JSON.stringify(err));
        })
}

return (
    <div>
        <CardComponent title={'Academic Subject Form'}>
            <Grid container className={classes.FormContainer}>
                <Grid item xs={12}>
                    <TextField value={editedAcademicSubject.academicSubject}
                               onChange={handleChangeForm("academicSubject")}
                               className={classes.FormStretchField}
                               label={'Academic Subject name'} size={'small'} variant="filled"/>
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

export default AcademicSubjectsForm;