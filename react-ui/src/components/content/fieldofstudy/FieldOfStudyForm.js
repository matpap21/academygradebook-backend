import classes from './FieldOfStudyForm.module.css';
import CardComponent from "../../CardComponent";
import DeleteIcon from '@material-ui/icons/Delete';
import {Button, Grid, MenuItem, TextField} from "@material-ui/core";
import {useState} from "react";
import instance from "../../../axios/axios";

// Model / encja pustej oferty/nowego obiektu
const EMPTY_NEW_FIELD_OF_STUDY = {
    'id': null,
    'fieldOfStudyEnum': null,
}

const FieldOfStudyForm = () => {
    // Tworząc formularz nadajemy mu stan pustego obiektu
    //  Wartości domyślne formularza kopiowane są z obiektu EMPTY_NEW_TRAINING
    const [editedFieldOfStudy, setEditedFieldOfStudy] = useState({...EMPTY_NEW_FIELD_OF_STUDY});


    const handleChangeForm = name => event => {
        setEditedFieldOfStudy({...editedFieldOfStudy, [name]: event.target.value});
    };

    const handleClearForm = () => {
        setEditedFieldOfStudy({...EMPTY_NEW_FIELD_OF_STUDY})
    }

    const handleSubmit = () => {
        // wysyłanie obiektu na serwer
        console.log("Wysyłamy:" + JSON.stringify(editedFieldOfStudy))

        instance.post('/fieldsofstudy', editedFieldOfStudy)
            .then((data) => {
                console.log("Odpowiedz sukces: " + JSON.stringify(data));
            })
            .catch((err) => {
                console.log("Odpowiedz porazka: " + JSON.stringify(err));
            })
    }

    return (
        <div>
            <CardComponent title={'Field Of Study Form'}>
                <Grid container className={classes.FormContainer}>
                    <Grid item xs={12}>
                        <TextField value={editedFieldOfStudy.fieldOfStudyEnum}
                                   onChange={handleChangeForm("fieldOfStudyEnum")}
                                   className={classes.FormStretchField}
                                   select
                                   label='Fields of Study' size={'small'} variant="filled">
                            <MenuItem value={'ArchitectureandUrbanPlanning'}>Architecture and Urban Planning</MenuItem>
                            <MenuItem value={'CivilEngineering'}>Civil Engineering</MenuItem>
                            <MenuItem value={'EnvironmentalEngineering'}>Environmental Engineering</MenuItem>
                            <MenuItem value={'BiomedicalEngineering'}>Biomedical Engineering</MenuItem>
                            <MenuItem value={'MaterialsEngineering'}>Materials Engineering</MenuItem>
                            <MenuItem value={'MechanicalEngineering'}>Mechanical Engineering</MenuItem>
                            <MenuItem value={'ManagementandProductionEngineering'}>Management and Production Engineering</MenuItem>
                            <MenuItem value={'Mechatronics'}>Mechatronics</MenuItem>
                            <MenuItem value={'TechnicalApplicationsoftheInternet'}>Technical Applications of the Internet</MenuItem>
                            <MenuItem value={'ElectronicsandTelecommunications'}>Electronics and Telecommunications</MenuItem>
                            <MenuItem value={'AutomaticControlandRobotics'}>Automatic Control and Robotics</MenuItem>
                            <MenuItem value={'ElectricalEngineering'}>Electrical Engineering</MenuItem>
                            <MenuItem value={'InformationEngineering'}>Information Engineering</MenuItem>
                            <MenuItem value={'PowerEngineering'}>Power Engineering</MenuItem>
                            <MenuItem value={'Mathematics'}>Mathematics</MenuItem>
                            <MenuItem value={'AutomaticControlandManagement'}>Automatic Control and Management</MenuItem>
                            <MenuItem value={'Bioinformatics'}>Bioinformatics</MenuItem>
                            <MenuItem value={'SafetyEngineering'}>Safety Engineering</MenuItem>
                            <MenuItem value={'Logistics'}>Logistics</MenuItem>
                            <MenuItem value={'Transport'}>Transport</MenuItem>
                            <MenuItem value={'ChemicalTechnology'}>Chemical Technology</MenuItem>

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

export default FieldOfStudyForm;