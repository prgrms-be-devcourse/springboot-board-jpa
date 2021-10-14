import { Link, withRouter } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';


function Board(props) {
    // Select Origin
    const local = 'http://localhost:8080';
    const deploy = 'http://15.165.69.116:8080';
    const origin = local;

    // State
    const page = props.match.params.page;
    const pageSize = 10;
    const [totalPages, setTotalPages] = useState([]);
    const [posts, setPosts] = useState([]);

    // Effect
    const UE = useEffect(() => {
        // 페이징 조회 요청
        axios.get(origin + "/api/post", { params: { page: page - 1, size: pageSize, sort: "id,DESC" } })
            .then(res => {
                console.log(res);
                setPosts(res.data.content)
            })
        // 총 게시글 수 받고 페이지 수 계산
        axios.get(origin + "/api/post/total")
            .then(res => {
                console.log(res);
                let pagesNumList = []
                for (let i = 1; i <= Math.ceil(res.data / pageSize); i++) {
                    pagesNumList.push(i);
                }
                setTotalPages(pagesNumList)
            })
    }, [props]);
 

    // button
    const movePage = (page) => {
        props.history.push(`/board/${page}`);
    }

    return (
        <div>

            <Menu />

            <h2>게시판 ({page}page)</h2>
            <hr /><br />
            <Link to="/posting" style={{fontSize : "20px"}}>📝 게시글 작성하기</Link>

            <br /><br /><br />

            <div style={{ textAlign: "center" }}>
                <table>
                    <tr>
                        <th>id(post)</th>
                        <th style={{ width: "50%" }}>제목(title)</th>
                        <th>조회수(view)</th>
                        <th>작성자(createdBy)</th>
                    </tr>
                    {
                        posts.map(v =>
                            <tr key={v.id}>
                                <td>{v.id}</td>
                                <td><Link to={`/post/${v.id}`} >{v.title}</Link></td>
                                <td>{v.view}</td>
                                <td>{v.createdBy}</td>
                            </tr>
                        )
                    }
                </table>
            </div>

            <br />

            {
                totalPages.map(v =>
                    <button key={v} onClick={() => movePage(v)}>{v}</button>
                )
            }


        </div>
    );
}

export default Board;
