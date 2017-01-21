import * as LiveConstant from '../constants/LiveConstant';

const initState = {
  ag: {
    status: false,
    res: {
      rs: true,
      msg: '',
    },
  },
  ds: {
    status: false,
    res: {
      rs: true,
      msg: '',
    },
  },
  sa: {
    status: false,
    res: {
      rs: true,
      msg: '',
    },
  },
  hg: {
    status: false,
    res: {
      rs: true,
      msg: '',
    },
  },
  og: {
    status: false,
    res: {
      rs: true,
      msg: '',
    },
  },
};

export default function (state = initState, action) {
  switch (action.type) {
    case LiveConstant.LIVE_CHECK_FLAT:
      let type = action.flat;
      let res = action.res;
      return Object.assign({}, initState, {
       [type]:  {
        status: !res.datas.status,
        res: {
          rs: res.rs,
          msg: res.datas.msg
        },
       }
      });
  }
  return state;
}