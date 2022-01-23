import CardComponent from "../../CardComponent";
import classes from './UniversityLecturersList.module.css';
import {Link} from "react-router-dom";
import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import UniversityLecturersTable from "./UniversityLecturersTable";
import instance from "../../../axios/axios";

const UniversityLecturersList = () => {
    const [rows, setRows] = useState([]);

    const pullRecordsFromDatabaseServer = () => {
        instance.get("/universitylecturers")
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
                <Link to={"/lecturer/add"} className={classes.UniversityLecturersAddButton}>
                    <Button variant="outlined">Add New</Button>
                </Link>
            </div>
            <UniversityLecturersTable rows={rows} refreshData={pullRecordsFromDatabaseServer}/>
        </div>
    )
}
export default UniversityLecturersList;