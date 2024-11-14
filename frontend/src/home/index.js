import React from 'react';
import '../App.css';
import '../static/css/home/home.css';
import logo from '../static/images/logoPNG_3.png';

export default function Home(){
    return(
        <div className="home-page-container">
            <div className="hero-div">
                <h1>Your game</h1>
                <h3>---</h3>
                <img src={logo}/>
                <h3>Do you want to play?</h3>                
            </div>
        </div>
    );
}