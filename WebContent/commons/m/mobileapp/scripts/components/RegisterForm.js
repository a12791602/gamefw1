import React, {Component, PropTypes} from 'react';
import {absUrl, redirect} from '../utils/url';
import {validateUsername, register, loadUserInfo} from '../actions/UserAction';
import * as validate from '../utils/validate';
import {userIsLogin} from '../utils/user';

class RegisterForm extends Component {

  constructor(props) {
    super(props);
    this.registerAccount = this.registerAccount.bind(this);
    this.state = {disabled:false};
  }

  componentDidMount(){
    this.refs.agent.value = window.defaultAgent != '' ? window.defaultAgent: '888';
  }

  componentWillReceiveProps(newProps) {
    const {user} = newProps;
    const {router} = this.context;
    // 用户已经登录
    if (userIsLogin(user) != ''){
      router.push('/');
    }
    // 验证账户名是否冲突
    else if (user.server.rs == false ) {
      // alert(user.server.msg);
    }
    // 注册成功
    else if (user.server.rs == true 
      && userIsLogin(user)) {
      this.setState({disabled:true});
      redirect('/');
    }
  }

  registerAccount() {
    const {name, password, confirmPassword, agent, realname, mobileNum, qq, withdrawalPwd} = this.refs;
    this.setState({disabled:true});
    const promise = new Promise((resolve, reject) => {
      setTimeout(resolve, 5000)
    });
    promise.then(()=>{
      this.setState({disabled:false});
    });
    // 简单的前端验证
    if (validate.name(name.value) == false) {
      alert('请正确输入帐号');
    }
    else if (validate.password(password.value) == false) {
      alert('请正确输入密码');
    }
    else if (password.value != confirmPassword.value) {
      alert('两次输入的密码不一致');
    }
    else if (realname.value.length <= 0) {
      alert('请正确输入真实姓名');
    }
    else if (validate.mobileNum(mobileNum.value) == false) {
      alert('请正确输入手机号码');
    }
    else if (validate.withdrawalPwd(withdrawalPwd.value) == false) {
      alert('请正确输入取款密码');
    }
    else {
      const {dispatch} = this.props;
      dispatch(register(name.value, 
        password.value, 
        confirmPassword.value,
        agent.value,
        realname.value,
        mobileNum.value,
        qq.value,
        withdrawalPwd.value));
    }
  }

  render() {
    return (
      <div className="login-form register-form">
        <div className="login-icon register-icon">
          <div className="inner">
            <div className="icon icon-reg"></div>
            <div className="li-desc">
              <span>用户注册 <br/> register </span>
            </div>
          </div>
        </div>

        <div className="form">
          <form method="POST" action="#">
            <div className="input-field">
              <label><span className="red">*</span>帐号</label>
              <input type="text" maxLength={10} minLength={4} ref="name" placeholder="请输入4-10个字符,仅可输入英文字母以及数字的组合!"/>
            </div> 
            <div className="input-field">
              <label><span className="red">*</span>密码</label>
              <input type="password" ref="password" maxLength={12} minLength={6} placeholder="须为6~12码英文或数字且符合0~9或a~z字元"/>
            </div>
            <div className="input-field">
              <label><span className="red">*</span>确认密码</label>
              <input type="password" ref="confirmPassword" />
            </div>
            <div className="input-field">
              <label><span className="red">*</span>介绍人</label>
              <input type="text" ref="agent" placeholder="默认888" />
            </div>
            <div className="input-field">
              <label><span className="red">*</span>真实姓名</label>
              <input type="text" ref="realname" placeholder="真实姓名要和银行开户姓名一致"/>
            </div>
            <div className="input-field">
              <label><span className="red">*</span>手机号码</label>
              <input type="text" ref="mobileNum" maxLength={13} placeholder="以手机形式确认提款，必需填写真实号码" />
            </div>
            <div className="input-field">
              <label><span className="red"></span>QQ号码</label>
              <input type="text" ref="qq" maxLength={15} />
            </div>
            <div className="input-field">
              <label><span className="red">*</span>提款密码</label>
              <input type="text" ref="withdrawalPwd" maxLength={4} minLength={4} placeholder="4位数字密码"/>
            </div>
            <div className="btn-group">
              <button type="button" ref="register" onClick={this.registerAccount} className={this.state.disabled?"btn btn-full btn-gray":"btn btn-full btn-red"} disabled={this.state.disabled}>确认注册</button>
            </div>
          </form>

        </div>
      </div>
    );
  }
};

RegisterForm.propTypes = {
  user: PropTypes.object.isRequired
};

RegisterForm.contextTypes = {
  router: PropTypes.object
};

export default RegisterForm;