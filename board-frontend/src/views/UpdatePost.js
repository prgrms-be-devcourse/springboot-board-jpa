import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';

function UpdatePost(props) {

    // Select Origin
    const local = 'http://localhost:8080/api';
    const deploy = 'https://boardapi.hanjo.xyz/api';
    const origin = local;

    // State
    const userId = localStorage.getItem("userId");
    const postId = props.match.params.postId;
    const [userInfo, setUserInfo] = useState({});
    const [newPostInfo, setNewPostInfo] = useState({});

    // Effect
    useEffect(async () => {
        // post 정보 받아오기
        const response = await axios.get(origin + `/post/${postId}`);
        // console.log(response)
        const res_postInfo = response.data
        const res_userInfo = res_postInfo.userDto
        // 검증
        if (!userId == res_userInfo.id) {
            alert("내가 작성한 게시물이 아닙니다!");
            props.history.goBack();
            return;
        }
        setUserInfo(res_userInfo);
        setNewPostInfo({ title: res_postInfo.title, content: res_postInfo.content });
    }, []);

    // Handler
    const titleHandler = (e) => setNewPostInfo({ ...newPostInfo, title: e.currentTarget.value })
    const contentHandler = (e) => setNewPostInfo({ ...newPostInfo, content: e.currentTarget.value })

    // Button
    const updatePost = () => {
        // console.log(newPostInfo);
        axios.patch(origin + `/post/${postId}`, newPostInfo)
            .then(res => {
                alert("게시물이 성공적으로 수정되었습니다.")
                props.history.goBack();
            })
            .catch(err => {
                if (err.response.status == 400 || err.response.status == 404) {
                    alert(err.response.data.message);
                }
            });
    };

    return (
        <div>
            <Menu />

            <h2>게시물 수정</h2>
            <hr /><br />

            <div className="div_wrapper_posting">
                <ul>
                    <li><b>작성자</b>: {userInfo.email}</li>
                    <li><b>이름</b>: {userInfo.name}</li>
                </ul>

                <input style={{ width: "100%" }} type="text" placeholder="제목" onChange={titleHandler} value={newPostInfo.title} />
                <br /><br />
                <textarea style={{ width: "100%", height: "300px" }} placeholder="내용을 입력하세요" onChange={contentHandler} value={newPostInfo.content} />
                <div style={{ textAlign: "center" }}>
                    <button onClick={updatePost}>수정완료</button>
                </div>

            </div>

        </div>
    );
}

export default UpdatePost;
