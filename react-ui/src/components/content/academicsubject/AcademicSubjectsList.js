import CardComponent from "../../CardComponent";
import classes from './AcademicSubjectsList.module.css';
import {Link} from "react-router-dom";
import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import AcademicSubjectsTable from "./AcademicSubjectsTable";
import UniversityLecturersTable from "../universitylecturer/UniversityLecturersTable";
import instance from "../../../axios/axios";

const AcademicSubjectsList = () => {
    const [rows, setRows] = useState([]);

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

    const handleRemoveRecord = (row) => {
        instance.delete("/academicsubjects/" + row.id)
            .then((data) => {
                console.log("Otrzymaliśmy sukces odpowiedź!");
                pullRecordsFromDatabaseServer();
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!");
            });
    }

    useEffect(() => {
        pullRecordsFromDatabaseServer();
    }, [])

    return (
        <div>
            <div className={classes.AddButtonContainer}>
                <Link to={"/subject/add"} className={classes.AcademicSubjectsAddButton}>
                    <Button variant="outlined">Add New</Button>
                </Link>
            </div>
            <AcademicSubjectsTable rows={rows} refreshData={pullRecordsFromDatabaseServer}/>
        </div>
    )
}
export default AcademicSubjectsList;