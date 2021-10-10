import { Link, withRouter } from 'react-router-dom';

function Login() {

  return (
    <div >

      <h2>로그인</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="text" placeholder="email"  />
        <input type="password" placeholder="password"  />
        <br /><br />
        <button >로그인하기</button>
      </div>

      <br/><br/><br/>

      <h2>회원가입</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="text" placeholder="email"  />
        <input type="password" placeholder="password"  />
        <input type="text" placeholder="name"  />
        <input type="text" placeholder="age"  />
        <input type="text" placeholder="hobby"  />
        <br /><br />
        <button >가입하기</button>
      </div>

    </div>
  );
}

export default withRouter(Login);
