import classes from "./AppHeader.module.css";
import {Link} from 'react-router-dom';
import logo from "../../logo.svg";

const HEADER_BUTTONS = [
    {
        name: 'Home',
        href: '/',
        icon: (<></>),  /* Brak ikony */
    },
    {
        name: 'Register',
        href: '/register',
        icon: (<></>),  /* Brak ikony */
    },
    {
        name: 'Auth',
        href: '/auth',
        icon: (<></>),  /* Brak ikony */
    },
]

const AppHeaderLoggedOut = () => {

    const mapToHeaderButton = (buttonInfo) => {
        return (
            /* Link zostanie zastąpiony/zaprezentowany w postaci <a> */
            <Link key={buttonInfo.name} to={buttonInfo.href} className={classes.HeaderMenuButton}>
                {buttonInfo.icon}
                <div>{buttonInfo.name}</div>
            </Link>
        )
    }

    return (
        <header className={classes.AppHeader}>
            <div className={classes.HeaderLeft}>
                <img src={logo} className={classes.AppLogo} alt="logo"/>
            </div>
            <div className={classes.HeaderRight}>
                {
                    HEADER_BUTTONS.map(mapToHeaderButton)
                }
            </div>
        </header>
    );
}

export default AppHeaderLoggedOut;