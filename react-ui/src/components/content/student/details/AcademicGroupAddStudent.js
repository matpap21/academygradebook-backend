import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import StudentsTable from "../StudentsTable";
import instance from "../../../../axios/axios";
import CardComponent from "../../../CardComponent";
import {Button, Grid, MenuItem, TextField} from "@material-ui/core";
import classes from "../../academicgroup/AcademicGroupsForm.module.css";
import DatePicker from "react-datepicker";
import DeleteIcon from "@material-ui/icons/Delete";
import AcademicGroupsForm from "../../academicgroup/AcademicGroupsForm";

const AcademicGroupAddStudent = () => {
    // dzięki temu że otrzymujemy studentId, możliwe jest późniejsze dodanie
    //  studenta do danej grupy.
    const {studentId} = useParams();
    const [editedAcademicGroup, setEditedAcademicGroup] = useState([])
    const [academicGroupsRecordsOnServer, setAcademicGroupsRecordsOnServer] = useState([]);



    const handleChangeForm = name => event => {
        setEditedAcademicGroup({...editedAcademicGroup, [name]: event.target.value});
    };

    const pullAcademicGroups = () => {
        instance.get(`/academicgroups`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                setAcademicGroupsRecordsOnServer(data.data); // kolekcja listy ktora ma by wyswietlona
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    const addStudentToAcademicGroup = (idStudent) => {
        console.log("Removing: " + idStudent)
        instance.post(`/academicgroups/student/${studentId}/${idStudent}`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                pullAcademicGroups();
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    const isStudentAdded = (studentId) => {
        for (let academicGroupStudentId in editedAcademicGroup.students){
            if(editedAcademicGroup.students[academicGroupStudentId].id === studentId){
                return true;
            }
        }
        return false
    }

    useEffect(() => {
        pullAcademicGroups();
    }, [])

    const handleSubmit = () => {
        // wysyłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(editedAcademicGroup))

        instance.post('/academicgroups/addstudent/'+ editedAcademicGroup.academicGroupsId + `/` + studentId)
            .then((data) => {
                console.log("Odpowiedz sukces: " + JSON.stringify(data));
            })
            .catch((err) => {
                console.log("Odpowiedz porazka: " + JSON.stringify(err));
            })
    }

    return (
        <div>
        {/*    TODO: formmularz, ma mieć jedną liste rozwijaną i z listy wybieramy academic group*/}

            <CardComponent title={'Academic Group Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={studentId.academicGroupsId}
                                   onChange={handleChangeForm("academicGroupsId")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Academy Groups' size={'small'} variant="filled">
                            {academicGroupsRecordsOnServer.map((academicGroups) => {
                                return (<MenuItem key={academicGroups.id} value={academicGroups.id}>{academicGroups.academicGroup}</MenuItem>)
                            })}
                        </TextField>
                    </Grid>
                    <Grid item xs={1}/>
                    <Grid container item xs={10}>

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
export default AcademicGroupAddStudent;