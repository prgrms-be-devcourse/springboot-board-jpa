import { Link } from 'react-router-dom';

function Menu() {

    const logout = () => {
        alert("로그아웃 완료. 초기화면으로 이동")
    }

  return (
    <div >
        <div className= "div_menu">
            <Link to="/board">게시판</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/mypage">마이페이지</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/" onClick={logout}>로그아웃</Link>
            <br />
        </div>
    </div>
  );
}

export default Menu;
