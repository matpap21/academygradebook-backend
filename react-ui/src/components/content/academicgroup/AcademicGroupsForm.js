import classes from './AcademicGroupsForm.module.css';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import CardComponent from "../../CardComponent";
import DeleteIcon from '@material-ui/icons/Delete';
import {Button, Grid, MenuItem, TableCell, TableRow, TextField} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import data from "bootstrap/js/src/dom/data";
import instance from "../../../axios/axios";

const getDateStringFromDateObject = (dateObject) => {
    let ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(dateObject);
    let mo = new Intl.DateTimeFormat('en', {month: '2-digit'}).format(dateObject);
    let da = new Intl.DateTimeFormat('en', {day: '2-digit'}).format(dateObject);

    return `${ye}-${mo}-${da}`
}

// Model / encja pustej oferty/nowego obiektu
const EMPTY_NEW_ACADEMIC_GROUP = {
    'id': null,
    'academicGroup': null,
    'startDate': getDateStringFromDateObject(new Date),
    'groupLength': 1,
    'fieldOfStudyId': null,
}

const AcademicGroupsForm = () => {
    // Tworząc formularz nadajemy mu stan pustego obiektu
    //  Wartości domyślne formularza kopiowane są z obiektu EMPTY_NEW_TRAINING
    const [editedAcademicGroup, setEditedAcademicGroup] = useState({...EMPTY_NEW_ACADEMIC_GROUP});
    const [timeStart, setTimeStart] = useState(new Date());
    const [fieldsOfStudyRecordsOnServer, setFieldsOfStudyRecordsOnServer] = useState([]);

    const handleChangeForm = name => event => {
        setEditedAcademicGroup({...editedAcademicGroup, [name]: event.target.value});
    };

    const handleDateChangeForm = name => date => {
        const finalDate = getDateStringFromDateObject(date)
        setTimeStart(date)
        setEditedAcademicGroup({...editedAcademicGroup, [name]: finalDate});
    };

    const handleClearForm = () => {
        setEditedAcademicGroup({...EMPTY_NEW_ACADEMIC_GROUP})
    }

    const pullFieldsOfStudyRecordsFromDatabaseServer = () => {
        instance.get("/api/fieldsofstudy")
            .then((data) => {
                // data ma pole data
                setFieldsOfStudyRecordsOnServer(data.data);
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    useEffect(() => {
        pullFieldsOfStudyRecordsFromDatabaseServer();
    }, [])

    const handleSubmit = () => {
        // wysyłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(editedAcademicGroup))

        instance.post('/api/fieldsofstudy/addgroup/'+ editedAcademicGroup.fieldOfStudyId, editedAcademicGroup)
            .then((data) => {
                console.log("Odpowiedz sukces: " + JSON.stringify(data));
            })
            .catch((err) => {
                console.log("Odpowiedz porazka: " + JSON.stringify(err));
            })
    }

    return (
        <div>
            <CardComponent title={'Academic Group Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={editedAcademicGroup.academicGroup}
                                   onChange={handleChangeForm("academicGroup")}
                                   className={classes.FormStretchField}
                                   label={'Academic Group name'} size={'small'} variant="filled"/>
                    </Grid>

                    <Grid item xs={12}>
                        <DatePicker selected={timeStart}
                                    onChange={handleDateChangeForm("startDate")}>
                        </DatePicker>
                    </Grid>

                    <Grid item xs={12}>
                        <TextField value={editedAcademicGroup.groupLength}
                                   onChange={handleChangeForm("groupLength")}
                                   className={classes.FormStretchField}
                                   type="number"
                                   inputProps={{
                                       'min': 1,
                                       'max': 100,
                                       'step': 1,
                                   }}
                                   label={'Length (days)'} size={'small'} variant="filled"/>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField value={editedAcademicGroup.fieldOfStudyId}
                                   onChange={handleChangeForm("fieldOfStudyId")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Fields of Study' size={'small'} variant="filled">
                            {fieldsOfStudyRecordsOnServer.map((fieldOfStudy) => {
                                return (<MenuItem key={fieldOfStudy.id} value={fieldOfStudy.id}>{fieldOfStudy.fieldOfStudyEnum}</MenuItem>)
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

export default AcademicGroupsForm;