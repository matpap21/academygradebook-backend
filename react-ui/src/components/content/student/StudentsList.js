import CardComponent from "../../CardComponent";
import classes from './StudentsList.module.css';
import {Link} from "react-router-dom";
import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import StudentsTable from "./StudentsTable";
import AcademicGroupsTable from "../academicgroup/AcademicGroupsTable";
import instance from "../../../axios/axios";


const StudentsList = () => {
    const [rows, setRows] = useState([]);

    const pullRecordsFromDatabaseServer = () => {
        instance.get("/students")
            .then((data) => {
                // data ma pole data
                console.log("Return success !")
                console.log("Records: " + JSON.stringify(data.data));

                setRows(data.data);
            })
            .catch((error) => {
                console.log("Return fail!")
            });
    }



    useEffect(() => {
        pullRecordsFromDatabaseServer();
    }, [])

    return (
        <div>
            <div className={classes.AddButtonContainer}>
                <Link to={"/student/add"} className={classes.StudentsAddButton}>
                    <Button variant="outlined">Add New</Button>
                </Link>
            </div>
            <StudentsTable rows={rows} refreshData={pullRecordsFromDatabaseServer}/>
        </div>
    )
}
export default StudentsList;