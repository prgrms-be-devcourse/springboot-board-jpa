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

            <div className="div_right">
                <Link to="/posting">게시글 작성하기</Link>
            </div>


        </div>
    );
}

export default Board;
