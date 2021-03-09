import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import { CgProfile } from "react-icons/cg";
import { ImSwitch } from "react-icons/im";
import { RiBankLine } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

export default function Header() {
  //Pulling up the data from Redux
  let logged = false;

  //Verify whether customer logged in or not.
  let loggedUser = JSON.parse(localStorage.getItem("loggedUserData"));
  if (loggedUser !== null && loggedUser.authenticatedSuccessfully) {
    logged = true;
  }

  const navigate = useNavigate()

  function handleLogOut(){
    localStorage.clear()
    navigate("/")
  }

  return (
    <div>
      {logged ? (
        <Navbar collapseOnSelect expand="lg" className="responsive-header">
          <Navbar.Brand href="/transfer">
            <RiBankLine size="1.5em" className="mr-2" />
            Internet Banking
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto"></Nav>
            <Nav>
              <Nav.Link href="/profile">
                <CgProfile /> Edit Profile
              </Nav.Link>
              <Nav.Link eventKey={2} href="#" onClick={handleLogOut}>
                <ImSwitch /> Logout
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      ) : (
        <p className="common-header">Internet Banking</p>
      )}
    </div>
  );
}
