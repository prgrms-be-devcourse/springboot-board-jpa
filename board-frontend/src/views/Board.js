import { Link, withRouter } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';


function Board() {

    return (
        <div>

            <Menu />

            <h2>게시판</h2>
            <hr /><br />

        </div>
    );
}

export default Board;
