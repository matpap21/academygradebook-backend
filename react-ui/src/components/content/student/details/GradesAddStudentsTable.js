import classes from './StudentDetails.module.css';
import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import CardComponent from "../../../CardComponent";
import axios from "axios";
import instance from "../../../../axios/axios";
import {Link} from "react-router-dom";
import {useEffect, useState} from "react";

const GradesAddStudentsTable = (props) => {
    const [gradesRecordsOnServer, setGradesRecordsOnServer] = useState([]);


    const handleRemoveRecord = (row) => {
        instance.delete("/api/grades" + row.id)
            .then((data) => {
                console.log("Otrzymaliśmy sukces odpowiedź!");
                props.refreshData();
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!");
            });
    }

    const pullGrades = () => {
        instance.get(`/api/grades/bystudent/${props.studentId}`)
            .then((data) => {
                // data ma pole data
                console.log("Otrzymaliśmy sukces odpowiedź!")
                console.log("Rekordy: " + JSON.stringify(data.data));

                setGradesRecordsOnServer(data.data); // kolekcja listy ktora ma by wyswietlona
            })
            .catch((error) => {
                console.log("Otrzymaliśmy odpowiedź o błędzie!")
            });
    }

    useEffect(() => {
        pullGrades();
    }, [])

    return <CardComponent title={'Grades with Student'}>
        <div className={classes.TableContainer}>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Id</TableCell>
                            <TableCell align="right">Grades</TableCell>
                            <TableCell align="right">Subject Name</TableCell>
                            <TableCell align="right"/>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {gradesRecordsOnServer.map((row) => {
                            let addButton = (<></>);
                            let removeButton = (<></>);
                            let detailsButton = (
                                <Link to={`/students/details/${row.id}`}>
                                    <Button variant="outlined">Details</Button>
                                </Link>);

                            if (props.onAdd) {
                                addButton = (<TableCell align="right">
                                    <Button onClick={() => {
                                        props.onAdd(row.id)
                                    }}>Add</Button>
                                </TableCell>)
                            }
                            if (props.onRemove) {
                                removeButton = (<TableCell align="right">
                                    <Button onClick={() => {
                                        props.onRemove(row.id)
                                    }}>Remove</Button>
                                </TableCell>)
                            }

                            return (<TableRow
                                key={row.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                <TableCell component="th" scope="row">{row.id}</TableCell>
                                <TableCell align="right">{row.grades}</TableCell>
                                <TableCell align="right">{row.academicSubject}</TableCell>
                                <TableCell align="right">
                                    {
                                        props.hideDelete ? (<></>) : <Button onClick={() => {
                                            handleRemoveRecord(row)
                                        }}>Delete</Button>

                                    }

                                </TableCell>
                                {
                                    (props.isAdded !== undefined && props.isAdded(row.id)) ? removeButton : addButton
                                }

                                {
                                    detailsButton
                                }

                            </TableRow>)
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    </CardComponent>
}

export default GradesAddStudentsTable;