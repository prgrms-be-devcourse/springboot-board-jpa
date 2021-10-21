import { Link, withRouter } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import qs from 'qs';
import Menu from '../components/Menu';


function Board(props) {
    // Select Origin
    const local = 'http://localhost:8080/api';
    const deploy = 'https://boardapi.hanjo.xyz/api';
    const origin = local;

    // State
    const query = qs.parse(props.location.search, { ignoreQueryPrefix: true });
    const page = query.page;
    const sort = query.sort;
    const size = 10;
    const search = query.search;
    const keyword = query.keyword;
    const [pageList, setPageList] = useState([]);
    const [posts, setPosts] = useState([]);
    const [total, setTotal] = useState();
    const [prev, setPrev] = useState();
    const [next, setNext] = useState();
    const [inputKeyword, setInputKeyword] = useState();
    const [searchType, setSearchType] = useState("");


    // Effect
    useEffect(() => {
        // 페이징 조회 요청
        axios.get(origin + "/post", { params: { page: page - 1, size: size, sort: sort } })
            .then(res => {
                setPosts(res.data.content)
            })
        // 총 게시글 수 받고 페이지 수 계산
        axios.get(origin + "/post/total")
            .then(res => {
                // 전체 페이지 숫자
                let totalPageNum = Math.ceil(res.data / size);
                setTotal(totalPageNum);
                // 현재 페이지가 속한 범위의 리스트
                let startPage = page % 10 == 0 ? (Math.floor(page / 10) - 1) * 10 + 1 : Math.floor(page / 10) * 10 + 1;
                let endPage = startPage + 9 < totalPageNum ? startPage + 9 : totalPageNum;
                let pagesNumList = []
                for (let i = startPage; i <= endPage; i++) {
                    pagesNumList.push(i);
                }
                setPageList(pagesNumList);
                // 이전-다음 페이지 지정 -> *1 페이지로 이동
                setPrev(startPage - 10);
                setNext(endPage + 1);
            })
    }, [props]);

    // Handler
    const searchTypeHandler = (e) => setSearchType(e.currentTarget.value)
    const inputKeywordHandler = (e) => setInputKeyword(e.currentTarget.value)

    // Button
    const movePage = (page) => {
        props.history.push(`/board?page=${page}&sort=${sort}`);
    }
    const changeSort = (sort) => {
        props.history.push(`/board?page=${page}&sort=${sort}`);
    }
    const changeSearch = () => {
        // `/board?page=${page}&sort=${sort}&search=${}&keyword=${}`
        console.log(inputKeyword)
        axios.get(origin + "/post/search", { params: { searchType: searchType, keyword: inputKeyword, page: 0, size: size, sort: sort} })
            .then(res => {
                console.log(res.data.content);
            })
            .catch(err => {
                console.log(err.response)
                if (err.response.status == 400 || err.response.status == 404)
                    alert(err.response.data.message);
            });
    }

    return (
        <div>

            <Menu />

            <h2>게시판 (page{page})</h2>
            <Link to="/posting" style={{ fontSize: "20px" }}>📝 게시글 작성하기</Link>

            <hr /><br />

            <div className="div_wrapper_search">
                <input type="radio" name="search" value="TITLE" onChange={searchTypeHandler} /> 제목
                <input type="radio" name="search" value="CONTENT" onChange={searchTypeHandler} /> 내용
                <input type="radio" name="search" value="AUTHOR" onChange={searchTypeHandler} /> 작성자
                <input style={{ marginTop: "15px" }} type="text" placeholder="검색어를 입력하세요" onChange={inputKeywordHandler} />
                <button onClick={changeSearch}>검색</button>
            </div>

            <br /><br />

            <div style={{ textAlign: "center" }}>
                <table>
                    <tr>
                        <th>post_id&nbsp;
                            {(
                                () => {
                                    if (sort === "id,DESC")
                                        return (<button className="button_sort" onClick={() => changeSort("id,ASC")}>∨</button>)
                                    else
                                        return (<button className="button_sort" onClick={() => changeSort("id,DESC")}>∧</button>)
                                }
                            )()}
                        </th>
                        <th style={{ width: "50%" }}>제목</th>
                        <th>조회수&nbsp;
                            {(
                                () => {
                                    if (sort === "view,DESC")
                                        return (<button className="button_sort" onClick={() => changeSort("view,ASC")}>∨</button>)
                                    else
                                        return (<button className="button_sort" onClick={() => changeSort("view,DESC")}>∧</button>)
                                }
                            )()}
                        </th>
                        <th>작성자</th>
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

            {(
                () => {
                    if (prev > 0) {
                        return (<button className="button_page_move" onClick={() => movePage(prev)}>&lt; 이전</button>)
                    }
                }
            )()}

            {
                pageList.map(v => {
                    if (v == page) {
                        return <button className="button_recent_page" key={v}>{v}</button>
                    }
                    else {
                        return <button className="button_page" key={v} onClick={() => movePage(v)}>{v}</button>
                    }
                })
            }

            {(
                () => {
                    if (next < total) {
                        return (<button className="button_page_move" onClick={() => movePage(next)}>다음 &gt;</button>)
                    }
                }
            )()}

        </div>
    );
}

export default Board;
