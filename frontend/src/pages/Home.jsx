
import React from "react";
import {Link} from "react-router-dom";
import './Home.css';
//Home page code
const Home = () => {
  return (
    <>
      <div>
        <h1>Welcome</h1>
        <Link to="/login">
          <button clasName="HomeButton">Next</button>
        </Link>
      </div>
    </>
  );
};

export default Home;
