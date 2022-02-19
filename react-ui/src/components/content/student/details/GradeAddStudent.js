import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import StudentsTable from "../StudentsTable";
import instance from "../../../../axios/axios";
import CardComponent from "../../../CardComponent";
import {Button, Grid, MenuItem, TextField} from "@material-ui/core";
import classes from "../../academicgroup/AcademicGroupsForm.module.css";

const GradesAddStudent = () => {
    // dzięki temu że otrzymujemy studentId, możliwe jest późniejsze dodanie
    //  studenta do danej grupy.
    const {studentId} = useParams();
    const [editedGrades, setEditedGrades] = useState([])
    const [gradesRecordsOnServer, setGradesRecordsOnServer] = useState([]);


    const handleChangeForm = name => event => {
        setEditedGrades({...editedGrades, [name]: event.target.value});
    };

    const pullGrades = () => {
        instance.get(`/api/grades/${studentId}`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                setGradesRecordsOnServer(data.data);
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    const addStudentToGrades = (idStudent) => {
        console.log("Removing: " + idStudent)
        instance.post(`/api/grades/student/${studentId}/${idStudent}`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    const removeStudentFromAcademicGroup = (idStudent) => {
        console.log("Adding: " + idStudent)
        instance.delete(`/api/grades/student/${studentId}/${studentId}`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                pullGrades();
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    const isStudentAdded = (studentId) => {
        for (let academicGradeStudentId in editedGrades.students){
            if(editedGrades.students[gradeStudentId].id === studentId){
                return true;
            }
        }
        return false
    }

    useEffect(() => {
        pullGrades();
    }, [])

    const handleSubmit = () => {
        // wysyłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(editedGrades))

        instance.post('/api/grades/addstudent/'+ editedGrades.grades + `/` + studentId)
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

            <CardComponent title={'Grades Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={studentId.grades}
                                   onChange={handleChangeForm("grades")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Grades' size={'small'} variant="filled">
                            {gradesRecordsOnServer.map((grades) => {
                                return (<MenuItem key={grades.id} value={grades.id}>{grades.grades}</MenuItem>)
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
export default GradesAddStudent;