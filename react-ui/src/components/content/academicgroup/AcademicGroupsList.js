import CardComponent from "../../CardComponent";
import classes from './AcademicGroupsList.module.css';
import {Link} from "react-router-dom";
import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import AcademicGroupsTable from "./AcademicGroupsTable";
import FieldOfStudyTable from "../fieldofstudy/FieldOfStudyTable";
import instance from "../../../axios/axios";

const AcademicGroupsList = () => {
    const [rows, setRows] = useState([]);

    const pullRecordsFromDatabaseServer = () => {
        instance.get("/academicgroups")
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
        instance.delete("/academicgroups/" + row.id)
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
                <Link to={"/group/add"} className={classes.AcademicGroupsAddButton}>
                    <Button variant="outlined">Add New</Button>
                </Link>
            </div>
            <AcademicGroupsTable rows={rows} refreshData={pullRecordsFromDatabaseServer}/>
        </div>
    )
}
export default AcademicGroupsList;