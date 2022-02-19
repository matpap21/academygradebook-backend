import classes from "./AppHeader.module.css";
import {Link} from 'react-router-dom';

import Storage from '@material-ui/icons/Storage'
import AddComment from '@material-ui/icons/AddComment'
import logo from "../../logo.svg";
import PeopleAlt from "@material-ui/icons/PeopleAlt";
import GradeIcon from "@material-ui/icons/Grade";
import {connect} from "react-redux";

const HEADER_BUTTONS = [
    {
        name: 'Logout', /* Link do formularza */
        href: '/logout',
        icon: (<></>),  /* Brak ikony */
        admin: true,
        lecturer: true,
        student: true,
    },
    {
        name: 'Register',
        href: '/register',
        icon: (<></>),  /* Brak ikony */
        admin: true,
        lecturer: true,
        student: true,
    },
    {
        name: 'Home',
        href: '/',
        icon: (<></>),  /* Brak ikony */
        admin: true,
        lecturer: true,
        student: true,
    },
    {
        name: 'Field Of Study', /* Link do tablicy z listą rekordów/danych */
        href: '/fieldofstudy',
        icon: (<Storage fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
        name: 'Academic Group', /* Link do tablicy z listą rekordów/danych */
        href: '/groups',
        icon: (<Storage fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
        name: 'Subjects', /* Link do tablicy z listą rekordów/danych */
        href: '/subjects',
        icon: (<Storage fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
        name: 'University Lecturers',
        href: '/lecturers',
        icon: (<PeopleAlt fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
        name: 'Students',
        href: '/students',
        icon: (<PeopleAlt fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
        name: 'All Grades of All Students', /* Link do tablicy z listą rekordów/danych */
        href: '/grades',
        icon: (<GradeIcon fontSize={"small"}/>),
        admin: true,
        lecturer: true,
        student: false,
    },
    {
    name: 'My grades', /* Link do tablicy z listą rekordów/danych */
    href: '/grades',
    icon: (<GradeIcon fontSize={"small"}/>),
    admin: true,
    lecturer: true,
    student: true,
},
    {
        name: 'My profile', /* Link do tablicy z listą rekordów/danych */
        href: '/students/details/:userId',
        icon: (<GradeIcon fontSize={"small"}/>),
        admin: false,
        lecturer: false,
        student: true,
    },

]

const AppHeaderLoggedIn = (props) => {

    const mapToHeaderButton = (buttonInfo, admin, lecturer, student,userID) => {
        let hrefLink = buttonInfo.href.replace(':userId','' + userID)
        let content = <Link key={buttonInfo.name} to={hrefLink} className={classes.HeaderMenuButton}>
            {buttonInfo.icon}
            <div>{buttonInfo.name}</div>
        </Link>;

        console.log(admin)
        console.log(lecturer)
        console.log(student)
        console.log(buttonInfo)

        if((!buttonInfo.admin && admin) || (!buttonInfo.lecturer && lecturer)|| (!buttonInfo.student && student)){
            content = <></>;
        }

        return (
            /* Link zostanie zastąpiony/zaprezentowany w postaci <a> */
            content
        )
    }

    return (
        <header className={classes.AppHeader}>
            <div className={classes.HeaderLeft}>
                <img src={logo} className={classes.AppLogo} alt="logo"/>
            </div>
            <div className={classes.HeaderRight}>
                {
                    HEADER_BUTTONS.map((button) => {
                        return mapToHeaderButton(button, props.authenticatedUserAdmin, props.authenticatedUserLecturer, props.authenticatedUserStudent, props.authenticatedUserId);
                    })
                }
                <div className={classes.UsernameHeaderDiv}>
                    Logged in as: {props.authenticatedUsername} [{props.authenticatedUserId}]
                    [{props.authenticatedUserAdmin ? 'A' : 'U'}]
                </div>
            </div>
        </header>
    );
}
const mapStateToProps = state => {
        return {
            authenticatedUsername: state.auth.username,
            authenticatedUserAdmin: state.auth.admin,
            authenticatedUserLecturer: state.auth.lecturer,
            authenticatedUserStudent: state.auth.student,
            authenticatedUserId: state.auth.id
        };
    }
;

export default connect(mapStateToProps, null)(AppHeaderLoggedIn);