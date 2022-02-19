import classes from './GradesForm.module.css';
import CardComponent from "../../CardComponent";
import DeleteIcon from '@material-ui/icons/Delete';
import {Button, Grid, MenuItem, TextField} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import data from "bootstrap/js/src/dom/data";
import instance from "../../../axios/axios";
import {useParams} from "react-router-dom";

// Model / encja pustej oferty/nowego obiektu
const EMPTY_NEW_GRADE = {
    'id': null,
    'grades': null,
    'subjectId': null,
    'studentId':null,
}

const GradesForm = () => {
    const {studentId} = useParams();
    const [rows, setRows] = useState([]);
    // Tworząc formularz nadajemy mu stan pustego obiektu
    //  Wartości domyślne formularza kopiowane są z obiektu EMPTY_NEW_TRAINING
    const [editedGrades, setEditedGrades] = useState({...EMPTY_NEW_GRADE});

    const handleChangeForm = name => event => {
        setEditedGrades({...editedGrades, [name]: event.target.value});
    };

    const handleClearForm = () => {
        setEditedGrades({...EMPTY_NEW_GRADE})
    }


    const handleSubmit = () => {
        // wysyłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(editedGrades))

        editedGrades.studentId = studentId;
        instance.post('/grades', editedGrades)
            .then((data) => {
                console.log("Odpowiedz sukces: " + JSON.stringify(data));
            })
            .catch((err) => {
                console.log("Odpowiedz porazka: " + JSON.stringify(err));
            })
    }
    const pullRecordsFromDatabaseServer = () => {
        instance.get("/academicsubjects")
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                setRows(data.data);
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }
    useEffect(() => {
        pullRecordsFromDatabaseServer();
    }, [])


    return (
        <div>
            <CardComponent title={'Grades Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={editedGrades.grades}
                                   onChange={handleChangeForm("grades")}
                                   className={classes.FormStretchField}
                                   type="number"
                                   inputProps={{
                                       'min': 2,
                                       'max': 5,
                                       'step': 0.5,
                                   }}
                                   label={'Grades'} size={'small'} variant="filled"/>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField value={editedGrades.subjectId}
                                   onChange={handleChangeForm("subjectId")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Grades' size={'small'} variant="filled">
                            {rows.map((universitySubject) => {
                                // na koncu trzeba wystitlic z listy nazwe grupy (wziete z back endu academicSubject)
                                return (<MenuItem key={universitySubject.id} value={universitySubject.id}>{universitySubject.academicSubject}</MenuItem>)
                            })}
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

export default GradesForm;