import * as UserConstant from '../constants/UserConstant';

const initState = {
  info: {
    userName: '',
    userMoney: '',
  },
  isLogin: false, // 是否已经登录
  isLogging: false, // 是否 登陆中
  server: {
    msg: '',
    rs: true,
  },
  registerSuccess: {
    msg: '',
  },
};

export default function (state = initState, action) {
  let res = action.res;
  switch (action.type) {
    case UserConstant.USER_LOGOUT:
      return Object.assign({}, state, {
        info: initState.info,
        isLogin: false,
        isLogging: false,
        server: {
          msg: '',
          rs: true,
        },
      });

    case UserConstant.USER_LOGGING:
      return Object.assign({}, state, {
        isLogging: true,
        server: {
          msg: action.res.msg,
          rs: action.res.rs
        }
      });

    case UserConstant.USER_REGISTER:
      if (res.rs && res.datas.status) {
        return Object.assign({}, state, {
          server: {
            msg: res.msg,
            rs: res.rs
          },
          registerSuccess: {
            msg: res.datas.content
          }
        });
      }
      else {
        return Object.assign({}, state, {
          server: {
            msg: res.msg,
            rs: res.rs
          },
        });
      }

    case UserConstant.USER_LOGIN:
      if (!!!res) break;
      if (res.rs == true) {
        return Object.assign({}, state, {
          isLogging: false,
          isLogin: true,
          info: res['datas'],
          server: {
            msg: res.msg,
            rs: res.rs
          },
        });
      } 
      else if (res.rs == false) {
        return Object.assign({}, state, {
          isLogging: false,
          isLogin: false,
          server: {
            msg: res.msg,
            rs: res.rs
          }
        });
      }
    case UserConstant.USER_NAME_FAILED:
      console.log(action);
      return Object.assign({}, state, {
        server: {
          msg: action.res.msg,
          rs: action.res.rs
        }
      });
  }
  return state;
};