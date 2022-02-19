import React from 'react';

import {makeStyles} from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import CircularProgress from '@material-ui/core/CircularProgress';

const useStyles = makeStyles({
    spinner: {
        color: '#1af7ff'
    }
});

const Spinner = () => {
    const classes = useStyles();
    return (
        <Grid container justifyContent="center" alignItems="center">
            <CircularProgress classes={{
                root: classes.spinner
            }}/>
        </Grid>
    );
}

export default Spinner;
