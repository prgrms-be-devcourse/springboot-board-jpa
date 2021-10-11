import { Link } from 'react-router-dom';

function Menu() {

  return (
    <div >
        <div className= "div_menu">
            <Link to="/board">게시판</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/mypage">마이페이지</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/">로그아웃</Link>
            <br /><br /><br />
        </div>
    </div>
  );
}

export default Menu;
