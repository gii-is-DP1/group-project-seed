import React, { useState } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarText, NavbarToggler, Collapse } from 'reactstrap';
import { Link } from 'react-router-dom';
import useAuth from './hooks/useAuth';

function AppNavbar() {
    const { jwt, roles, username } = useAuth();
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    let adminLinks = <></>;
    let playerLinks = <></>;
    let userLinks = <></>;
    let userLogout = <></>;
    let publicLinks = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>                    
                    <NavItem>
                        <NavLink className="nav-link-white" tag={Link} to="/users">Users</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink className="nav-link-white" tag={Link} to="/developers">Developers</NavLink>
                    </NavItem>
                </>
            )
        } 
        if (role === "PLAYER") {
            playerLinks = (
            <>
                <NavItem>
                <NavLink className="nav-link-white" tag={Link} to="/achievements">Achievements</NavLink>
                </NavItem>
            </>
            )
        }
       
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink className="nav-link-white" id="docs" tag={Link} to="/docs">Docs</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="nav-link-white" id="plans" tag={Link} to="/plans">Pricing Plans</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="nav-link-white" id="register" tag={Link} to="/register">Register</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="nav-link-white" id="login" tag={Link} to="/login">Login</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLinks = (
            <>
                <NavItem>
                    <NavLink className="nav-link-white" tag={Link} to="/dashboard">Dashboard</NavLink>
                </NavItem>
            </>
        )
        userLogout = (
            <>
                <NavItem>
                    <NavLink className="nav-link-white" id="docs" tag={Link} to="/docs">Docs</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className="nav-link-white" id="plans" tag={Link} to="/plans">Pricing Plans</NavLink>
                </NavItem>
                <NavbarText className="nav-link-white justify-content-end">{username}</NavbarText>
                <NavItem className="d-flex">
                    <NavLink className="nav-link-white" id="logout" tag={Link} to="/logout">Logout</NavLink>
                </NavItem>
            </>
        )

    }

    return (
        <div>
            <Navbar expand="md" dark color="dark">
                <NavbarBrand href="/">
                    <img alt="logo" src="/logo1-recortado.png" style={{ height: 40, width: 40 }} />
                     My Game - DP1
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="me-auto mb-2 mb-lg-0" navbar>
                        {userLinks}
                        {adminLinks}
                        {playerLinks}
                    </Nav>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;
