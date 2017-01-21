import * as UserConstant from '../constants/UserConstant';
import {logoutUrl, loginUrl, userInfoUrl, options, registerUrl, validateUsernameUrl} from '../utils/url';

export function userLogout() {
  return (dispatch, getState) => {
    let state = getState();
    const {user} = state;
    fetch(logoutUrl(), options({method: 'post'}))
    .then(res => res.json())
    .then(json => {
      dispatch({
        type: UserConstant.USER_LOGOUT,
        res: json
      });
    })
    .catch(e => {
      console.log('登录出错');
    });
  };
};

export function login(name, pwd, code) {
  return dispatch => {
    let form = new FormData();
    form.append("loginName", name);
    form.append("password", pwd);
    form.append("verifycode", code);

    fetch(loginUrl(), options({
      body: form,
      method: 'post',
    }))
    .then(res => res.json())
    .then(json => {
      dispatch({
        type: UserConstant.USER_LOGGING,
        res: json
      });
      if (json.rs) {
        dispatch(loadUserInfo());
      }
    });
  };
};

export function loadUserInfo() {
  return dispatch => {
    fetch(userInfoUrl(), options())
    .then(res => res.json())
    .then(json => {
      dispatch({
        type: UserConstant.USER_LOGIN,
        res: json
      });
    })
    .catch(e => {
      dispatch({
        type: UserConstant.USER_LOGIN,
        res: null,
      });
    });
  };
};

function validateUsername(name, cb = (json) => {}) {
  let form = new FormData()
  form.append('userName', name);
  fetch(validateUsernameUrl(), options({
    method: 'POST',
    body: form
  }))
  .then(res => res.json())
  .then(json => {
    cb(json);
  });
}

export function register(name, password, confirmPassword, agent, realname, mobile, qq, withdrawalPwd) {
  return dispatch => {

    validateUsername(name, (json) => {
      if (json.rs) {
        let form = new FormData();
        form.append('userName', name);
        form.append('userPassword', password);
        form.append('ruserPassword', confirmPassword);
        form.append('userAgent', agent);
        form.append('userRealName', realname);
        form.append('swmobile_1', 1);
        form.append('userMobile', mobile);
        form.append('userQq', qq);
        form.append('userWithdrawPassword', withdrawalPwd);
        fetch(registerUrl(), options({
          method: 'POST',
          body: form
        }))
        .then(res => {
          
          dispatch({
            type: UserConstant.USER_REGISTER,
            res: json
          });

          if (json.rs) {
            dispatch(loadUserInfo());
          }
        });
      }
      else {
        dispatch({
          type: UserConstant.USER_NAME_FAILED,
          res: json
        });
      }
    });
  };
}