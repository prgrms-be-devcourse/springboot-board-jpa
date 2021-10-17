import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';
import { Link } from 'react-router-dom';

function Post(props) {

    // Select Origin
    const local = 'http://localhost:8080';
    const deploy = 'http://15.165.69.116:8080';
    const origin = deploy;

    // State
    const postId = props.match.params.postId;
    const [userInfo, setUserInfo] = useState({});
    const [postInfo, setPostInfo] = useState({});

    // Effect
    useEffect(async () => {
        // post 정보 받아오기
        const response = await axios.get(origin + `/api/post/${postId}`)
            .catch(err => {
                if (err.response.status == 400 || err.response.status == 404) {
                    alert(err.response.data.message);
                    props.history.goBack();
                }
            });
        const res_postInfo = response.data
        // view 업데이트
        axios.patch(origin + `/api/post/${postId}/view`, { newView: res_postInfo.view + 1 })
            .then(res => {
                // console.log(res);
                res_postInfo.view = res.data;
                setPostInfo(res_postInfo)
                setUserInfo(res_postInfo.userDto)
            })
    }, []);

    // Button
    const deletePost = () => {
        if (window.confirm("정말 삭제하시겠습니까?")) {
            axios.delete(origin + `/api/post/${postId}`)
                .then(res => {
                    alert("게시물 삭제 완료");
                    props.history.goBack();
                    return
                })
        }
    }
    const moveUpdatePost = () => {
        props.history.push(`/update-post/${postId}`);
    }

    return (
        <div >
            <Menu />

            <h2>게시물 상세보기</h2>
            <hr /><br />

            <table className="table_post">
                <td style={{ backgroundColor: 'lightgrey' }}><b>작성시간</b>: {postInfo.createdAt}</td>
                <td style={{ backgroundColor: 'lightgrey' }}><b>작성자</b>: {userInfo.name} (<span style={{ fontSize: "13px" }}>{userInfo.email}</span>)</td>
                <td style={{ backgroundColor: 'lightgrey' }}><b>조회수</b>: {postInfo.view}</td>
            </table>
            <br />
            <table className="table_post">
                <tr><td><b>제목</b>: {postInfo.title}</td></tr>
                <tr><td className="td_content">{postInfo.content}</td></tr>
            </table>
            <br />
            {(
                () => {
                    if (localStorage.getItem("userId") == userInfo.id) {
                        return (<div className="div_wrapper">
                            내가 작성한 게시물입니다
                            <br /><br />
                            <button onClick={deletePost}>삭제하기</button>
                            &nbsp;&nbsp;&nbsp;
                            <button onClick={moveUpdatePost}>수정하기</button>
                        </div>)
                    }
                }
            )()}

        </div>
    );
}

export default Post;
