import React, {PropTypes, Component} from 'react';
import {absUrl} from '../utils/url';
import {timestamp} from '../utils/time';
import {login} from '../actions/UserAction';
import {userIsLogin} from '../utils/user';

import BaseComponent from './BaseComponent';

class LoginForm extends BaseComponent {

  constructor(props) {
    super(props);
    this.refreshCodeImg = this.refreshCodeImg.bind(this);
    this.startToLogin = this.startToLogin.bind(this);
  }

  componentWillReceiveProps(newProps) {
    const {user} = newProps;
    if (user.server.rs == false && user.server.msg) {
      alert(user.server.msg);
    }
    else if (userIsLogin(user)) {
      const {router} = this.context;
      router.push('/');
    }
  }

  refreshCodeImg() {
    let img = this.refs.code_img;
    let src = img.src;
    img.src = src + "?t=" + timestamp();
  }

  startToLogin() {
    const {password, code, name} = this.refs;
    const {dispatch} = this.props;
    if (password.value == '' || code.value == '' || name.value == '') {
      return 
    }
    else {
      dispatch(login(name.value, password.value, code.value));
    }
  }

  render() {
    return (
      <div className="login-form">
        <div className="login-icon">
          <div className="inner">
            <div className="icon icon-people"></div>
            <div className="li-desc">
              <span>用户登录 <br/> user login </span>
            </div>
          </div>
        </div>

        <div className="form">
          <form method="POST" action="#">
            <div className="input-field">
              <input type="text" placeholder="用户名" ref="name" />
            </div>
            <div className="input-field">
              <input type="password" placeholder="密码" ref="password" />
            </div>
            <div className="input-field clearfix">
              <input type="text" placeholder="验证码" ref="code" />
              <img ref="code_img" src={ absUrl('resources-code.jpg') + "?t=" + timestamp() } onClick={this.refreshCodeImg}/>
            </div>
            <div className="btn-group">
              <button type="button" onClick={this.startToLogin} className="btn btn-full btn-red">确认登录</button>
            </div>
          </form>
        </div>
      </div>
    );
  }
};

LoginForm.propTypes = {
  dispatch: PropTypes.func.isRequired,
  user: PropTypes.object
};

LoginForm.contextTypes = {
  router: PropTypes.object
};

export default LoginForm;